package com.andback.pocketfridge.present.views.main.mypage.fridgemanage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andback.pocketfridge.data.model.FridgeEntity
import com.andback.pocketfridge.data.model.UserEntity
import com.andback.pocketfridge.domain.usecase.fridge.CreateFridgeUseCase
import com.andback.pocketfridge.domain.usecase.fridge.GetFridgesUseCase
import com.andback.pocketfridge.domain.usecase.user.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class FridgeManageViewModel @Inject constructor(
    private val getFridgesUseCase: GetFridgesUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val createFridgeUseCase: CreateFridgeUseCase
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val _user = MutableLiveData<UserEntity>()
    val user: LiveData<UserEntity> get() = _user
    private val _fridges = MutableLiveData<List<FridgeEntity>>()
    val fridges: LiveData<List<FridgeEntity>> get() = _fridges
    private val _tstMsg = MutableLiveData<String>()
    val tstMsg: LiveData<String> get() = _tstMsg

    init {
        getUser()
        getFridges()
    }

    private fun getUser() {
        compositeDisposable.add(
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
        )
    }

    private fun getFridges() {
        compositeDisposable.add(
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
        )
    }

    fun createFridge(name: String) {
        compositeDisposable.add(
            createFridgeUseCase(name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        _tstMsg.value = it.message
                        getFridges()
                    },
                    {
                        // TODO : 에러 처리
                    }
                )
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}