package com.andback.pocketfridge.present.views.user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andback.pocketfridge.domain.model.CheckResult
import com.andback.pocketfridge.domain.usecase.GetLoginUseCase
import com.andback.pocketfridge.present.config.SingleLiveEvent
import com.andback.pocketfridge.present.utils.NetworkManager
import com.andback.pocketfridge.present.utils.PageSet
import com.andback.pocketfridge.present.utils.SignUpChecker
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject


@HiltViewModel
class UserViewModel @Inject constructor (
    private val getLoginUseCase: GetLoginUseCase,
    private val networkManager: NetworkManager
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    // viewmodel이 editText에 입력된 값을 observe 하기 위함
    val emailForSignUp: MutableLiveData<String> = MutableLiveData("")
    val pwForSignUp: MutableLiveData<String> = MutableLiveData("")
    val pwConfirmForSignUp: MutableLiveData<String> = MutableLiveData("")
    val nameForSignUp: MutableLiveData<String> = MutableLiveData("")
    val nicknameForSignUp: MutableLiveData<String> = MutableLiveData("")

    // view가 다음으로 넘어갈 페이지를 observe 하기 위함
//    private val _pageNumber = MutableLiveData<PageSet>()
//    val pageNumber: LiveData<PageSet> get() = _pageNumber

    val pageNumber = SingleLiveEvent<PageSet>()

    fun signUp(req: MutableMap<String, String>) {
        compositeDisposable.add(
            getLoginUseCase.execute(req)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        // hideLoading()
                    },
                    {
                        // hideLoading()
                        // showError()
                        if (it is HttpException && (it!!.code() in 400 until 500)){
                            var responseBody = it!!.response()?.errorBody()?.string()
                            val jsonObject = JSONObject(responseBody!!.trim())
                            var message = jsonObject.getString("message")
                            Log.d("UserViewModel", "signUp: ${message}")
                            Log.d("UserViewModel", "signUp: ${it.code()}")
                        }
                    },
                    {
                        // hideLoading()
                        pageNumber.value = PageSet.LOGIN
                    },
                    {
                        // showLoading()
                    }
                )
        )
        pageNumber.value = PageSet.LOGIN
    }

    fun onNextClick() {
        if (!checkNetworkState()) return

        val id = emailForSignUp.value.toString()
        val pw = pwForSignUp.value.toString()
        val pwConfirm = pwConfirmForSignUp.value.toString()

        if(SignUpChecker.validateEmail(id).isValid
            && SignUpChecker.validatePw(pw).isValid
            && SignUpChecker.validateConfirmPw(pw, pwConfirm).isValid) {
            pageNumber.value = PageSet.STEP_TWO
        }
    }

    fun onSignUpClick() {
        if (!checkNetworkState()) return

        val name = nameForSignUp.value.toString()
        val nickname = nicknameForSignUp.value.toString()

        if(SignUpChecker.validateName(name).isValid
            && SignUpChecker.validateNickname(nickname).isValid) {
            signUp(getEnteredUserInfo())
        }
    }

    private fun getEnteredUserInfo(): MutableMap<String, String>{
        val req = mutableMapOf<String, String>()
        req["userEmail"] = emailForSignUp.value.toString()
        req["userName"] = nameForSignUp.value.toString()
        req["userNickname"] = nicknameForSignUp.value.toString()
        req["userPassword"] = pwForSignUp.value.toString()

        return req
    }

    private fun checkNetworkState(): Boolean {
        return networkManager.checkNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}