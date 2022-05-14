package com.andback.pocketfridge.present.views.main.mypage.fridgemanage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andback.pocketfridge.R
import com.andback.pocketfridge.data.model.FridgeEntity
import com.andback.pocketfridge.data.model.UserEntity
import com.andback.pocketfridge.domain.usecase.fridge.CreateFridgeUseCase
import com.andback.pocketfridge.domain.usecase.fridge.DeleteFridgeUseCase
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
    private val createFridgeUseCase: CreateFridgeUseCase,
    private val deleteFridgeUseCase: DeleteFridgeUseCase
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val _user = MutableLiveData<UserEntity>()
    val user: LiveData<UserEntity> get() = _user
    private val _fridges = MutableLiveData<List<FridgeEntity>>()
    val fridges: LiveData<List<FridgeEntity>> get() = _fridges
    private val _tstMsg = MutableLiveData<String>()
    val tstMsg: LiveData<String> get() = _tstMsg
    private val _tstErrorMsg = MutableLiveData<String>()
    val tstErrorMsg: LiveData<String> get() = _tstErrorMsg


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
                        _tstErrorMsg.value = R.string.common_error.toString()
                        Log.e(TAG, "deleteFridge: ${it.message}")
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
                        _tstErrorMsg.value = R.string.common_error.toString()
                        Log.e(TAG, "deleteFridge: ${it.message}")
                    }
                )
        )
    }

    fun deleteFridge(id: Int) {
        compositeDisposable.add(
            deleteFridgeUseCase(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        _tstMsg.value = it.message
                        getFridges()
                    },
                    {
                        _tstErrorMsg.value = R.string.common_error.toString()
                        Log.e(TAG, "deleteFridge: ${it.message}")
                    }
                )
        )
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    companion object {
        const val TAG = "FridgeManageViewModel"
    }
}