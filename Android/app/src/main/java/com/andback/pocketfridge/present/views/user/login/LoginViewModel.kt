package com.andback.pocketfridge.present.views.user.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andback.pocketfridge.data.model.LoginEntity
import com.andback.pocketfridge.domain.usecase.user.GetLoginUseCase
import com.andback.pocketfridge.domain.usecase.datastore.WriteDataStoreUseCase
import com.andback.pocketfridge.domain.usecase.user.SocialLoginUseCase
import com.andback.pocketfridge.present.config.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getLoginUseCase: GetLoginUseCase,
    private val socialLoginUseCase: SocialLoginUseCase,
    private val writeDataStoreUseCase: WriteDataStoreUseCase
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading
    private val _toastMsg = SingleLiveEvent<String>()
    val toastMsg: LiveData<String> get() = _toastMsg
    private val _isLogin = SingleLiveEvent<Boolean>()
    val isLogin: LiveData<Boolean> = _isLogin

    val email = MutableLiveData<String>()
    val pw = MutableLiveData<String>()

    private fun login(loginEntity: LoginEntity) {
        _isLoading.value = true

        compositeDisposable.add(
            getLoginUseCase(loginEntity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        if(it.data != null) {
                            saveJWT(it.data.token, it.data.loginType)
                        }
                        _isLogin.value = true
                        _toastMsg.value = it.message
                        _isLoading.value = false
                    },
                    {
                        _isLoading.value = false
                        showError(it)
                    },
                )
        )
    }

    fun socialLogin(socialType: String, code: String) {
        _isLoading.value = true

        compositeDisposable.add(
            socialLoginUseCase(socialType, code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        if (it.data != null) {
                            saveJWT(it.data.token, socialType.uppercase())
                        }
                        _isLogin.value = true
                        _toastMsg.value = it.message
                        _isLoading.value = false
                    },
                    {
                        _isLoading.value = false
                        showError(it)
                    }
                )
        )
    }

    fun saveJWT(jwt: String, loginType: String) =
            runBlocking {
                writeDataStoreUseCase.execute("JWT", jwt)
                writeDataStoreUseCase.execute("LOGIN_TYPE", loginType)
            }

    fun onLoginClick() {
        login(LoginEntity(email.value.toString(), pw.value.toString()))
    }

    private fun showError(t : Throwable) {
        if (t is HttpException && (t.code() in 400 until 500)){
            val responseBody = t.response()?.errorBody()?.string()
            val jsonObject = JSONObject(responseBody!!.trim())
            val message = jsonObject.getString("message")
            _toastMsg.value = message
            Log.d("LoginViewModel", "${t.code()}")
        } else {
            _toastMsg.value = t.message
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}