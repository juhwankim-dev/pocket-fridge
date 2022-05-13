package com.andback.pocketfridge.present.views.main.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andback.pocketfridge.data.model.FridgeEntity
import com.andback.pocketfridge.data.model.UserEntity
import com.andback.pocketfridge.domain.usecase.fridge.GetFridgesUseCase
import com.andback.pocketfridge.domain.usecase.user.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class FridgeManageViewModel @Inject constructor(
    private val getFridgesUseCase: GetFridgesUseCase,
    private val getUserUseCase: GetUserUseCase
) : ViewModel() {
    private val _user = MutableLiveData<UserEntity>()
    val user: LiveData<UserEntity> = _user
    private val _fridges = MutableLiveData<List<FridgeEntity>>()
    val fridges: LiveData<List<FridgeEntity>> get() = _fridges

    init {
        getUser()
        getFridges()
    }

    private fun getUser() {
        getUserUseCase().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if(it.data != null) {
                        _user.value = it.data!!
                    }
                },
                {

                }
            )
    }

    fun getFridges() {
        getFridgesUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    it.data?.let { list ->
                        _fridges.value = list
                    }
                },
                {
                    // TODO: 예외 ui 처리
                }
            )
    }
}