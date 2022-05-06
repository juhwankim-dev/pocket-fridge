package com.andback.pocketfridge.present.views.user.signup

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andback.pocketfridge.R
import com.andback.pocketfridge.data.model.UserEntity
import com.andback.pocketfridge.domain.model.CheckResult
import com.andback.pocketfridge.domain.usecase.user.GetCheckEmailUseCase
import com.andback.pocketfridge.domain.usecase.user.GetCheckNicknameUseCase
import com.andback.pocketfridge.domain.usecase.user.GetSignUpUseCase
import com.andback.pocketfridge.domain.usecase.user.GetSendEmailUseCase
import com.andback.pocketfridge.present.config.SingleLiveEvent
import com.andback.pocketfridge.present.utils.PageSet
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

    // viewmodel이 editText에 입력된 값을 observe 하기 위함
    val email = MutableLiveData<String>()
    val name = MutableLiveData<String>()
    val nickname = MutableLiveData<String>()
    val pw = MutableLiveData<String>()
    val pwConfirm = MutableLiveData<String>()

    // view가 다음으로 넘어갈 페이지를 observe 하기 위함
    val pageNumber = SingleLiveEvent<PageSet>()

    // 로딩을 보여줄지 결정하기 위함
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    // 이메일을 성공적으로 보냈는지 xml에서 확인하기 위함
    val isSentEmail = SingleLiveEvent<Boolean>()

    // 토스트 메시지 & 에러 메시지
    private val _toastMsg = SingleLiveEvent<String>()
    private val _toastMsgIntType = SingleLiveEvent<Int>()
    private val _emailErrorMsg = MutableLiveData<CheckResult>()
    private val _emailAuthNumberErrorMsg = MutableLiveData<Int>()
    private val _nicknameErrorMsg = MutableLiveData<Int>()

    val toastMsg: LiveData<String> get() = _toastMsg
    val toastMsgIntType: LiveData<Int> get() = _toastMsgIntType
    val emailErrorMsg: LiveData<CheckResult> get() = _emailErrorMsg
    val emailAuthNumberErrorMsg: LiveData<Int> get() = _emailAuthNumberErrorMsg
    val nicknameErrorMsg: LiveData<Int> get() = _nicknameErrorMsg

    // 이메일 인증번호
    val EmailAuthNumber = MutableLiveData<String>()
    var sentEmailAuthNumber = "THISISPRIVATEKEY"

    fun init() {
        email.value = ""
        name.value = ""
        nickname.value = ""
        pw.value = ""
        pwConfirm.value = ""
        isSentEmail.value = false
        EmailAuthNumber.value = ""
        sentEmailAuthNumber = "THISISPRIVATEKEY"
    }

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
                            UserEntity(
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

    private fun signUp(userEntity: UserEntity) {
        _isLoading.value = true

        compositeDisposable.add(
            getSignUpUseCase(userEntity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        _toastMsg.value = it.message
                        _isLoading.value = false
                        pageNumber.value = PageSet.LOGIN
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
        if(sentEmailAuthNumber == EmailAuthNumber.value) {
            _toastMsgIntType.value = R.string.email_auth_success
            pageNumber.value = PageSet.SIGN_UP
        } else {
            _emailAuthNumberErrorMsg.value = R.string.email_auth_match_error
        }
    }

    fun onSignUpClick() {
        checkNickname(nickname.value!!)
    }

    fun onCloseClick() {
        pageNumber.value = PageSet.LOGIN
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