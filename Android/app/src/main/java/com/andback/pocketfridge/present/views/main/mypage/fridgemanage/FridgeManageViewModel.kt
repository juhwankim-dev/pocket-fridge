package com.andback.pocketfridge.present.views.main.mypage.fridgemanage

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andback.pocketfridge.R
import com.andback.pocketfridge.data.model.FridgeEntity
import com.andback.pocketfridge.data.model.ShareUserEntity
import com.andback.pocketfridge.data.model.UserEntity
import com.andback.pocketfridge.domain.usecase.fridge.*
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
    private val updateFridgeNameUseCase: UpdateFridgeNameUseCase,
    private val deleteFridgeUseCase: DeleteFridgeUseCase,
    private val shareFridgeUseCase: ShareFridgeUseCase,
    private val getFridgeMembersUseCase: GetFridgeMembersUseCase
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val _user = MutableLiveData<UserEntity>()
    val user: LiveData<UserEntity> get() = _user
    private val _fridges = MutableLiveData<List<FridgeEntity>>()
    val fridges: LiveData<List<FridgeEntity>> get() = _fridges
    private val _members = MutableLiveData<List<ShareUserEntity>>()
    val members: LiveData<List<ShareUserEntity>> get() = _members
    private val _tstMsg = MutableLiveData<String>()
    val tstMsg: LiveData<String> get() = _tstMsg
    private val _isShared = MutableLiveData<Boolean>()
    val isShared: LiveData<Boolean> get() = _isShared
    private val _tstErrorMsg = MutableLiveData<Int>()
    val tstErrorMsg: LiveData<Int> get() = _tstErrorMsg

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
                        _tstErrorMsg.value = R.string.common_error
                        Log.e(TAG, "getFridges: ${it.message}")
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
                        _tstErrorMsg.value = R.string.common_error
                        Log.e(TAG, "createFridge: ${it.message}")
                    }
                )
        )
    }

    fun updateFridgeName(id: Int, name: String) {
        compositeDisposable.add(
            updateFridgeNameUseCase(id, name)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        _tstMsg.value = it.message
                        getFridges()
                    },
                    {
                        _tstErrorMsg.value = R.string.common_error
                        Log.e(TAG, "updateFridgeName: ${it.message}")
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
                        _tstErrorMsg.value = R.string.common_error
                        Log.e(TAG, "deleteFridge: ${it.message}")
                    }
                )
        )
    }

    fun addMember(fridgeId: Int, email: String) {
        val disposable = shareFridgeUseCase(email, fridgeId).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _isShared.value = true
//                    _isShared.value = false
                },
                {
                    // TODO: R.string.share_error
                }
            )

        compositeDisposable.add(disposable)
    }

    fun resetIsShared() {
        _isShared.value = false
    }

    fun getFridgeMembers(id: Int) {
        compositeDisposable.add(
            getFridgeMembersUseCase(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        it.data?.let { list ->
                            _members.value = list.sortedByDescending { item -> item.isOwner }
                        }
                    },
                    {
                        _tstErrorMsg.value = R.string.common_error
                        _members.value = emptyList()
                        Log.e(TAG, "getFridgeMembers: ${it.message}")
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