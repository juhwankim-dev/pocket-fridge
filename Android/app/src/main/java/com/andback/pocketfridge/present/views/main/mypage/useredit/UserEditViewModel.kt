package com.andback.pocketfridge.present.views.main.mypage.useredit

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.fragment.findNavController
import com.andback.pocketfridge.data.model.UserEditEntity
import com.andback.pocketfridge.data.model.UserEntity
import com.andback.pocketfridge.domain.usecase.user.GetUserUseCase
import com.andback.pocketfridge.domain.usecase.user.UpdateUserUseCase
import com.andback.pocketfridge.present.config.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class UserEditViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val updateUserUseCase: UpdateUserUseCase
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    val personalInfo = MutableLiveData<UserEntity>()

    val nickname = MutableLiveData<String>()
    val pw = MutableLiveData<String>()

    private val _toastMsg = SingleLiveEvent<String>()
    val toastMsg: LiveData<String> get() = _toastMsg

    val isLoading = MutableLiveData<Boolean>()

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

    fun updateUser(userEditEntity: UserEditEntity) {
        compositeDisposable.add(
            updateUserUseCase(userEditEntity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        isLoading.value = false
                    },
                    {
                        showError(it)
                        isLoading.value = false
                    },
                )
        )
    }

    private fun showError(t : Throwable) {
        if (t is HttpException && (t.code() in 400 until 500)){
            var responseBody = t.response()?.errorBody()?.string()
            val jsonObject = JSONObject(responseBody!!.trim())
            var message = jsonObject.getString("message")
            _toastMsg.value = message
            Log.d("UserEditViewModel", "${t.code()}")
        } else {
            _toastMsg.value = t.message
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}