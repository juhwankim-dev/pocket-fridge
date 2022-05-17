package com.andback.pocketfridge.present.views.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andback.pocketfridge.domain.usecase.user.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase
): ViewModel() {

    val isLogin = MutableLiveData<Boolean>()

    init {
        checkLogin()
    }

    fun checkLogin() {
        getUserUseCase().subscribeOn(Schedulers.io())
            .subscribe(
                {
                    isLogin.postValue(true)
                },
                {
                    isLogin.postValue(false)
                }
            )
    }
}