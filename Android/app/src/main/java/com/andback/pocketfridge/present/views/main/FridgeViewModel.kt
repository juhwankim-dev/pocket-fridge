package com.andback.pocketfridge.present.views.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andback.pocketfridge.data.model.BaseResponse
import com.andback.pocketfridge.data.model.FridgeEntity
import com.andback.pocketfridge.domain.usecase.fridge.GetFridgesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class FridgeViewModel @Inject constructor(
    private val getFridgesUseCase: GetFridgesUseCase
): ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean>
        get() = _isLoading
    
    private val _fridges = MutableLiveData<List<FridgeEntity>>()
    val fridges: LiveData<List<FridgeEntity>>
        get() = _fridges 


    init {
        getFridges()
    }

    fun getFridges() {
        if(isLoading.value!!) {
            compositeDisposable.add(
                getFridgesUseCase.excute(getEmail())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            it.data?.let { list ->
                                _fridges.value = list
                            }
                            _isLoading.value = false
                        },
                        {
                            _isLoading.value = false
                            // TODO: 냉장고 리스트 fail ui 처리 
                        },
                        {
                            _isLoading.value = false
                        },
                        {
                            _isLoading.value = true
                        }
                    )
            )    
        }
    }

    // TODO: 회원정보 가져오는 usecase 생성 후 처리
    fun getEmail(): String = "ms001118@gmail.com"

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}