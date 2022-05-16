package com.andback.pocketfridge.present.views.user.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andback.pocketfridge.R
import com.andback.pocketfridge.data.model.SignUpEntity
import com.andback.pocketfridge.domain.model.CheckResult
import com.andback.pocketfridge.domain.usecase.user.GetCheckEmailUseCase
import com.andback.pocketfridge.domain.usecase.user.GetCheckNicknameUseCase
import com.andback.pocketfridge.domain.usecase.user.GetSignUpUseCase
import com.andback.pocketfridge.domain.usecase.user.GetSendEmailUseCase
import com.andback.pocketfridge.present.config.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject


@HiltViewModel
class SignUpViewModel @Inject constructor (
    private val getSignUpUseCase: GetSignUpUseCase,
    private val getSendEmailUseCase: GetSendEmailUseCase,
    private val getCheckEmailUseCase: GetCheckEmailUseCase,
    private val getCheckNicknameUseCase: GetCheckNicknameUseCase
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading
    private val _toastMsg = SingleLiveEvent<String>()
    val toastMsg: LiveData<String> get() = _toastMsg
    private val _toastMsgIntType = SingleLiveEvent<Int>()
    val toastMsgIntType: LiveData<Int> get() = _toastMsgIntType
    private val _emailErrorMsg = MutableLiveData<CheckResult>()
    val emailErrorMsg: LiveData<CheckResult> get() = _emailErrorMsg
    private val _emailAuthNumberErrorMsg = MutableLiveData<Int>()
    val emailAuthNumberErrorMsg: LiveData<Int> get() = _emailAuthNumberErrorMsg
    private val _isSuccessCheckEmail = MutableLiveData<Boolean>()
    val isSuccessCheckEmail: LiveData<Boolean> get() = _isSuccessCheckEmail
    private val _isSuccessSignUp = MutableLiveData<Boolean>()
    val isSuccessSignUp: LiveData<Boolean> get() = _isSuccessSignUp
    private val _nicknameErrorMsg = MutableLiveData<Int>()
    val nicknameErrorMsg: LiveData<Int> get() = _nicknameErrorMsg

    val email = MutableLiveData<String>()
    val isSentEmail = SingleLiveEvent<Boolean>()
    val typedAuthNumber = MutableLiveData<String>()
    var sentEmailAuthNumber = "THISISPRIVATEKEY"

    val name = MutableLiveData<String>()
    val nickname = MutableLiveData<String>()
    val pw = MutableLiveData<String>()
    val pwConfirm = MutableLiveData<String>()

    private fun checkEmail(email: String) {
        compositeDisposable.add(
            getCheckEmailUseCase.execute(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        _emailErrorMsg.value = CheckResult(R.string.no_error, true)
                        sendEmail(email)
                    }, { showError(it) }
                )
        )
    }

    private fun sendEmail(email: String) {
        _isLoading.value = true

        compositeDisposable.add(
            getSendEmailUseCase.execute(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        isSentEmail.value = true
                        _isLoading.value = false
                        sentEmailAuthNumber = it.data!!
                    },
                    {
                        showError(it)
                        _isLoading.value = false
                    },
                )
        )
    }

    private fun checkNickname(nickname: String) {
        compositeDisposable.add(
            getCheckNicknameUseCase.execute(nickname)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        _nicknameErrorMsg.value = R.string.no_error
                        signUp(
                            SignUpEntity(
                                email.value.toString(),
                                name.value.toString(),
                                nickname,
                                pw.value.toString(),
                                null
                            )
                        )
                    }, { showError(it) },
                )
        )
    }

    private fun signUp(signUpEntity: SignUpEntity) {
        _isLoading.value = true

        compositeDisposable.add(
            getSignUpUseCase(signUpEntity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        _toastMsg.value = it.message
                        _isLoading.value = false
                        _isSuccessSignUp.value = true
                    },
                    {
                        _isLoading.value = false
                        showError(it)
                    },
                )
        )
    }

    fun onSendEmailClick() {
        checkEmail(email.value!!)
    }

    fun onNextClick() {
        if(sentEmailAuthNumber == typedAuthNumber.value) {
            _isSuccessCheckEmail.value = true
        } else {
            _toastMsgIntType.value = R.string.email_auth_match_error
        }
    }

    fun onSignUpClick() {
        checkNickname(nickname.value!!)
    }

    private fun showError(t : Throwable) {
        if (t is HttpException && (t.code() in 400 until 500)){
            var responseBody = t.response()?.errorBody()?.string()
            val jsonObject = JSONObject(responseBody!!.trim())
            var message = jsonObject.getString("message")
            _toastMsg.value = message
            Log.d("SignUpViewModel", "${t.code()}")
        } else {
            _toastMsg.value = t.message
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}