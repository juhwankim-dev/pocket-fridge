package com.andback.pocketfridge.present.views.user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andback.pocketfridge.R
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
class UserViewModel @Inject constructor (
    private val getSignUpUseCase: GetSignUpUseCase,
    private val getSendEmailUseCase: GetSendEmailUseCase,
    private val getCheckEmailUseCase: GetCheckEmailUseCase,
    private val getCheckNicknameUseCase: GetCheckNicknameUseCase
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    // viewmodel이 editText에 입력된 값을 observe 하기 위함
    val email: MutableLiveData<String> = MutableLiveData("")
    val name: MutableLiveData<String> = MutableLiveData("")
    val nickname: MutableLiveData<String> = MutableLiveData("")
    val pw: MutableLiveData<String> = MutableLiveData("")
    val pwConfirm: MutableLiveData<String> = MutableLiveData("")

    // view가 다음으로 넘어갈 페이지를 observe 하기 위함
    val pageNumber = SingleLiveEvent<PageSet>()

    // 이메일을 성공적으로 보냈는지 xml에서 확인하기 위함
    val isSentEmail = SingleLiveEvent<Boolean>()

    // 토스트 메시지 & 에러 메시지
    private val _toastMsg = MutableLiveData<String>()
    private val _toastMsgIntType = MutableLiveData<Int>()
    private val _emailErrorMsg: MutableLiveData<CheckResult> = MutableLiveData()
    private val _emailAuthNumberErrorMsg: MutableLiveData<Int> = MutableLiveData()
    private val _nicknameErrorMsg: MutableLiveData<Int> = MutableLiveData()

    val toastMsg: LiveData<String> get() = _toastMsg
    val toastMsgIntType: LiveData<Int> get() = _toastMsgIntType
    val emailErrorMsg: LiveData<CheckResult> get() = _emailErrorMsg
    val emailAuthNumberErrorMsg: LiveData<Int> get() = _emailAuthNumberErrorMsg
    val nicknameErrorMsg: LiveData<Int> get() = _nicknameErrorMsg

    // 이메일 인증번호
    val EmailAuthNumber: MutableLiveData<String> = MutableLiveData("")
    var sentEmailAuthNumber = ""

    private fun checkEmail(email: String) {
        compositeDisposable.add(
            getCheckEmailUseCase.execute(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        when(it.status) {
                            200 -> {
                                _emailErrorMsg.value = CheckResult(R.string.no_error, true)
                                sendEmail(email)
                            }
                            401 -> {
                                _emailErrorMsg.value = CheckResult(R.string.email_overlap_error, false)
                            }
                        }
                    }, {}, {}, {}
                )
        )
    }

    private fun sendEmail(email: String) {
        compositeDisposable.add(
            getSendEmailUseCase.execute(email)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        when(it.status) {
                            200 -> {
                                isSentEmail.value = true
                                sentEmailAuthNumber = it.data!!
                                _toastMsg.value = it.message
                            }
                            401 -> {
                                _toastMsg.value = it.message
                            }
                        }
                    }, {}, {}, {}
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
                        when(it.status) {
                            200 -> {
                                _nicknameErrorMsg.value = R.string.no_error
                                signUp(getEnteredUserInfo())
                                _toastMsg.value = it.message
                            }
                            401 -> {
                                _nicknameErrorMsg.value = R.string.nickname_overlap_error
                                _toastMsg.value = it.message
                            }
                        }
                    }, {}, {}, {}
                )
        )
    }

    private fun signUp(req: MutableMap<String, String>) {
        compositeDisposable.add(
            getSignUpUseCase.execute(req)
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

    private fun getEnteredUserInfo(): MutableMap<String, String>{
        val req = mutableMapOf<String, String>()
        req["userEmail"] = email.value.toString()
        req["userName"] = name.value.toString()
        req["userNickname"] = nickname.value.toString()
        req["userPassword"] = pw.value.toString()

        return req
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}