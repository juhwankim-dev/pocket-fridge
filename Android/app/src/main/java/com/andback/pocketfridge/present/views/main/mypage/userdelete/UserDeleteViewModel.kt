package com.andback.pocketfridge.present.views.main.mypage.userdelete

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andback.pocketfridge.domain.usecase.datastore.ResetDataStoreUseCase
import com.andback.pocketfridge.domain.usecase.user.DeleteUserUseCase
import com.andback.pocketfridge.present.config.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserDeleteViewModel @Inject constructor(
    private val deleteUserUseCase: DeleteUserUseCase,
    private val resetDataStoreUseCase: ResetDataStoreUseCase
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val _isDeletedUser = SingleLiveEvent<Boolean>()
    val isDeletedUser: LiveData<Boolean> get() = _isDeletedUser

    val isLoading = MutableLiveData<Boolean>()

    fun onDeleteUserClick() {
        isLoading.value = true
        compositeDisposable.add(
            deleteUserUseCase()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        _isDeletedUser.value = true
                    },
                    {
                        isLoading.value = false
                    },
                )
        )
    }

    fun resetDataStore() {
        viewModelScope.launch { resetDataStoreUseCase.execute() }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}