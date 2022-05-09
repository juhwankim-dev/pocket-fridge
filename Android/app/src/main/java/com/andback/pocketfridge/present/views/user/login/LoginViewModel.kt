package com.andback.pocketfridge.present.views.user.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andback.pocketfridge.data.model.LoginEntity
import com.andback.pocketfridge.domain.usecase.user.GetLoginUseCase
import com.andback.pocketfridge.domain.usecase.datastore.WriteDataStoreUseCase
import com.andback.pocketfridge.present.config.SingleLiveEvent
import com.andback.pocketfridge.present.utils.PageSet
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val getLoginUseCase: GetLoginUseCase,
    private val writeDataStoreUseCase: WriteDataStoreUseCase
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    val email = MutableLiveData<String>()
    val pw = MutableLiveData<String>()

    // view가 다음으로 넘어갈 페이지를 observe 하기 위함
    val pageNumber = SingleLiveEvent<PageSet>()

    // 로딩을 보여줄지 결정하기 위함
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _toastMsg = SingleLiveEvent<String>()
    val toastMsg: LiveData<String> get() = _toastMsg

    private fun login(loginEntity: LoginEntity) {
        _isLoading.value = true

        compositeDisposable.add(
            getLoginUseCase(loginEntity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        if(it.data != null) {
                            saveJWT(it.data)
                        }
                        _toastMsg.value = it.message
                        _isLoading.value = false
                        pageNumber.value = PageSet.MAIN
                    },
                    {
                        _isLoading.value = false
                        showError(it)
                    },
                )
        )
    }

    fun saveJWT(jwt: String) {
        viewModelScope.launch {
            writeDataStoreUseCase.execute("JWT", jwt)
        }
    }

    fun onLoginClick() {
        login(LoginEntity(email.value.toString(), pw.value.toString()))
    }

    fun onSignUpClick() {
        pageNumber.value = PageSet.EMAIL_AUTH
    }

    fun onFindPwClick() {
        pageNumber.value = PageSet.FIND_PW
    }

    private fun showError(t : Throwable) {
        if (t is HttpException && (t.code() in 400 until 500)){
            var responseBody = t.response()?.errorBody()?.string()
            val jsonObject = JSONObject(responseBody!!.trim())
            var message = jsonObject.getString("message")
            _toastMsg.value = message
            Log.d("LoginViewModel", "${t.code()}")
        } else {
            _toastMsg.value = t.message
        }
    }
}