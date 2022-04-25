package com.andback.pocketfridge.present.views.main

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
    private val ingreName = MutableLiveData<String>("")
    private val ingreDatePurchased = MutableLiveData<String>("")
    private val ingreDateExpiry = MutableLiveData<String>("")

    // TODO: 카테고리 테이블 완성 후 추가
//    private val ingreCategory = MutableLiveData<String>("")
    private val ingreStorage = MutableLiveData<Storage>(Storage.Fridge)
    private val ingreFridgeId = MutableLiveData<Int>(-1)

    // 에러 라이브 데이터
    private val isNameError = MutableLiveData(false)
    private val isQuantityError = MutableLiveData(false)
    private val isDatePurchasedError = MutableLiveData(false)
    private val isDateExpiryError = MutableLiveData(false)
    private val isCategoryError = MutableLiveData(false)
    private val isStorageError = MutableLiveData(false)
    private val isFridgeIdError = MutableLiveData(false)
    private val isNetworkError = MutableLiveData(false)
    private val isServerError = MutableLiveData(false)

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
                isServerError.value = true
            }
            is IOException -> {
                isNetworkError.value = true
            }
            is IngreNameException -> {
                isNameError.value = true
            }
            is IngreQuantityException -> {
                isQuantityError.value = true
            }
            is IngreFridgeIdException -> {
                isFridgeIdError.value = true
            }
            is IngreCategoryException -> {
                isCategoryError.value = true
            }
            is IngreDatePurchasedException -> {
                isDatePurchasedError.value = true
            }
            is IngreDateExpiryException -> {
                isDateExpiryError.value = true
            }
        }
    }

    private fun clearError() {
        isNameError.value = false
        isQuantityError.value = false
        isDatePurchasedError.value = false
        isDateExpiryError.value = false
        isCategoryError.value = false
        isStorageError.value = false
        isFridgeIdError.value = false
        isNetworkError.value = false
        isServerError.value = false
    }

    private fun getIngredientFromInput(): Ingredient {
        // TODO: 카테고리 테이블 완성 후 값 변경
        return Ingredient(category = "temp", name = ingreName.value?: "", purchasedDate = ingreDatePurchased.value.toString(), expiryDate = ingreDateExpiry.value.toString(), fridgeId = ingreFridgeId.value?: -1, storage = ingreStorage.value?:Storage.Fridge)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}