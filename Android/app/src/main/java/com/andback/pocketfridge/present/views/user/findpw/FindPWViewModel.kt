package com.andback.pocketfridge.present.views.user.findpw

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andback.pocketfridge.data.model.UserForFindEntity
import com.andback.pocketfridge.domain.usecase.user.GetFindPWUseCase
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
class FindPWViewModel @Inject constructor(
    private val getFindPWUseCase: GetFindPWUseCase
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    val name = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val pageNumber = SingleLiveEvent<PageSet>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _toastMsg = MutableLiveData<String>()
    val toastMsg: LiveData<String> get() = _toastMsg

    private fun findPW(userForFind: UserForFindEntity) {
        _isLoading.value = true

        compositeDisposable.add(
            getFindPWUseCase(userForFind)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        when(it.status) {
                            200 -> {
                                _isLoading.value = false
                                _toastMsg.value = "이메일을 전송하였습니다."
                                pageNumber.value = PageSet.LOGIN
                            }
                        }
                    },
                    {
                        _isLoading.value = false
                        showError(it)
                    }
                )
        )
    }

    fun onSendEmailClick() {
        findPW(UserForFindEntity(email.value!!, name.value!!))
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
            Log.d("FindPWViewModel", "${t.code()}")
        } else {
            _toastMsg.value = t.message
        }
    }
}