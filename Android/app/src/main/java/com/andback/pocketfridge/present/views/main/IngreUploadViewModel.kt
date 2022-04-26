package com.andback.pocketfridge.present.views.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andback.pocketfridge.domain.model.Ingredient
import com.andback.pocketfridge.domain.usecase.UploadIngreUseCase
import com.andback.pocketfridge.present.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class IngreUploadViewModel @Inject constructor(
    private val uploadIngreUseCase: UploadIngreUseCase
): ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    val ingreName = MutableLiveData<String>("")
    val ingreDatePurchased = MutableLiveData<String>("")
    val ingreDateExpiry = MutableLiveData<String>("")

    // TODO: 카테고리 테이블 완성 후 추가
//    private val ingreCategory = MutableLiveData<String>("")
    val ingreStorage = MutableLiveData<Storage>(Storage.Fridge)
    val ingreFridgeId = MutableLiveData<Int>(-1)

    // 에러 라이브 데이터
    private val _isNameError = MutableLiveData(false)
    val isNameError: LiveData<Boolean>
        get() = _isNameError
    private val _isQuantityError = MutableLiveData(false)
    val isQuantityError: LiveData<Boolean>
        get() = _isQuantityError
    private val _isDatePurchasedError = MutableLiveData(false)
    val isDatePurchasedError: LiveData<Boolean>
        get() =_isDatePurchasedError
    private val _isDateExpiryError = MutableLiveData(false)
    val isDateExpiryError: LiveData<Boolean>
        get() = _isDateExpiryError
    private val _isCategoryError = MutableLiveData(false)
    val isCategoryError: LiveData<Boolean>
        get() =_isCategoryError
    private val _isStorageError = MutableLiveData(false)
    val isStorageError: LiveData<Boolean>
        get() = _isStorageError
    private val _isFridgeIdError = MutableLiveData(false)
    val isFridgeIdError: LiveData<Boolean>
        get() = _isFridgeIdError
    private val _isNetworkError = MutableLiveData(false)
    val isNetworkError: LiveData<Boolean>
        get() =_isNetworkError
    private val _isServerError = MutableLiveData(false)
    val isServerError: LiveData<Boolean>
        get() = _isServerError

    // TODO: 보유 냉장고 목록 반환 usecase로 냉장고 정보 얻기 

    fun onUploadBtnClick() {
        compositeDisposable.add(
            uploadIngreUseCase.uploadIngre(getIngredientFromInput())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        // TODO: 식재료 등록 성공      
                    },
                    { throwable ->
                        handleException(throwable)
                    },
                    {
                        // TODO: hideLoading() 
                    },
                    {
                        // TODO: showLoading() 
                    }
                )
        )
    }

    private fun handleException(e: Throwable) {
        clearError()
        when(e) {
            is HttpException -> {
                _isServerError.value = true
            }
            is IOException -> {
                _isNetworkError.value = true
            }
            is IngreNameException -> {
                _isNameError.value = true
            }
            is IngreQuantityException -> {
                _isQuantityError.value = true
            }
            is IngreFridgeIdException -> {
                _isFridgeIdError.value = true
            }
            is IngreCategoryException -> {
                _isCategoryError.value = true
            }
            is IngreDatePurchasedException -> {
                _isDatePurchasedError.value = true
            }
            is IngreDateExpiryException -> {
                _isDateExpiryError.value = true
            }
        }
    }

    private fun clearError() {
        _isNameError.value = false
        _isQuantityError.value = false
        _isDatePurchasedError.value = false
        _isDateExpiryError.value = false
        _isCategoryError.value = false
        _isStorageError.value = false
        _isFridgeIdError.value = false
        _isNetworkError.value = false
        _isServerError.value = false
    }

    private fun getIngredientFromInput(): Ingredient {
        // TODO: 카테고리 테이블 완성 후 값 변경
        // TODO: 수량 data가 필요해지면 추가
        return Ingredient(quantity = 1, category = "temp", name = ingreName.value?: "", purchasedDate = ingreDatePurchased.value.toString(), expiryDate = ingreDateExpiry.value.toString(), fridgeId = ingreFridgeId.value?: -1, storage = ingreStorage.value?:Storage.Fridge)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}