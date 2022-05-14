package com.andback.pocketfridge.present.views.main.mypage.useredit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andback.pocketfridge.domain.model.Password
import com.andback.pocketfridge.domain.usecase.user.ConfirmPWUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class ConfirmPWViewModel @Inject constructor(
    private val confirmPWUseCase: ConfirmPWUseCase
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    val pw = MutableLiveData<String>()

    private val _isCorrectPW = MutableLiveData<Boolean>()
    val isCorrectPW: LiveData<Boolean> get() = _isCorrectPW

    fun checkPW(pw: Password) {
        compositeDisposable.add(
            confirmPWUseCase(pw)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        _isCorrectPW.value = true
                    },
                    {
                        _isCorrectPW.value = false
                    },
                )
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}