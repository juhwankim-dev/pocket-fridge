package com.andback.pocketfridge.present.views.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.andback.pocketfridge.domain.usecase.GetUserUseCase
import com.andback.pocketfridge.present.config.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor (
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val _canLogIn = SingleLiveEvent<Any>()
    val canLogIn: LiveData<Any>
        get() = _canLogIn

    fun signUp(req: MutableMap<String, String>) {
        compositeDisposable.add(
            getUserUseCase.execute(req)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {

                    },
                    {

                    }
                )
        )
    }

    fun onLoginClick() {

    }
}