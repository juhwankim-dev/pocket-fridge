package com.andback.pocketfridge.present.views.user

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andback.pocketfridge.R
import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.domain.model.CheckResult
import com.andback.pocketfridge.domain.usecase.GetCheckEmailUseCase
import com.andback.pocketfridge.domain.usecase.GetCheckNicknameUseCase
import com.andback.pocketfridge.domain.usecase.GetSignUpUseCase
import com.andback.pocketfridge.domain.usecase.GetSendEmailUseCase
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

    // 로딩을 보여줄지 결정하기 위함
    private val _isShowLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isShowLoading: LiveData<Boolean> get() = _isShowLoading

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
    var sentEmailAuthNumber = "THISISPRIVATEKEY"

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
                            else -> {
                                _toastMsg.value = it.message
                            }
                        }
                    }, { showError(it) }
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
                            }
                            else -> {
                                _toastMsg.value = it.message
                            }
                        }
                    },
                    {
                        showError(it)
                        _isShowLoading.value = false
                    },
                    {
                        _isShowLoading.value = false
                    },
                    {
                        _isShowLoading.value = true
                    }
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
                            }
                            401 -> {
                                _nicknameErrorMsg.value = R.string.nickname_overlap_error
                            }
                        }
                    }, { showError(it) },
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
                        _toastMsg.value = it.message
                    },
                    {
                        _isShowLoading.value = false
                        showError(it)
                    },
                    {
                        _isShowLoading.value = false
                        //pageNumber.value = PageSet.LOGIN
                    },
                    {
                        _isShowLoading.value = true
                    }
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

    private fun getEnteredUserInfo(): MutableMap<String, String>{
        val req = mutableMapOf<String, String>()
        req["userEmail"] = email.value.toString()
        req["userName"] = name.value.toString()
        req["userNickname"] = nickname.value.toString()
        req["userPassword"] = pw.value.toString()

        return req
    }

    private fun showError(t : Throwable) {
        if (t is HttpException && (t.code() in 400 until 500)){
            var responseBody = t.response()?.errorBody()?.string()
            val jsonObject = JSONObject(responseBody!!.trim())
            var message = jsonObject.getString("message")
            _toastMsg.value = message
            Log.d("UserViewModel", "${message}")
            Log.d("UserViewModel", "${t.code()}")
        } else {
            _toastMsg.value = t.message
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}