package com.andback.pocketfridge.present.views.main.mypage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andback.pocketfridge.data.model.UserEntity
import com.andback.pocketfridge.domain.usecase.datastore.ReadDataStoreUseCase
import com.andback.pocketfridge.domain.usecase.user.GetUserUseCase
import com.andback.pocketfridge.present.config.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.runBlocking
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class MyPageViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val readDataStoreUseCase: ReadDataStoreUseCase
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    val personalInfo = MutableLiveData<UserEntity>()

    private val _toastMsg = SingleLiveEvent<String>()
    val toastMsg: LiveData<String> get() = _toastMsg

    private val _loginType = SingleLiveEvent<String>()
    val loginType: LiveData<String> get() = _loginType

    fun getUser() {
        compositeDisposable.add(
            getUserUseCase()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        personalInfo.value = it.data!!
                    },
                    {
                        showError(it)
                    },
                )
        )
    }

    fun readLoginType() {
        _loginType.value = runBlocking {
            readDataStoreUseCase.execute("LOGIN_TYPE") ?: ""
        }
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

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}