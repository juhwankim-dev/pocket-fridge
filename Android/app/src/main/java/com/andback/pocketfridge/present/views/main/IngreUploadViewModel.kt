package com.andback.pocketfridge.present.views.main

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andback.pocketfridge.data.model.MainCategoryEntity
import com.andback.pocketfridge.data.model.SubCategoryEntity
import com.andback.pocketfridge.domain.model.Ingredient
import com.andback.pocketfridge.domain.usecase.category.GetCategoryUseCase
import com.andback.pocketfridge.domain.usecase.ingredient.UploadIngreUseCase
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
    private val uploadIngreUseCase: UploadIngreUseCase,
    private val getCategoryUseCase: GetCategoryUseCase
): ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    val ingreName = MutableLiveData<String>("")
    val ingreDatePurchased = MutableLiveData<String>("")
    val ingreDateExpiry = MutableLiveData<String>("")
    val ingreCategory = MutableLiveData<String>("")
    val ingreStorage = MutableLiveData<Storage>(Storage.Fridge)
    val ingreFridgeId = MutableLiveData<Int>(-1)

    //카테고리 livedata
    private val _mainCategory = MutableLiveData<List<MainCategoryEntity>>()
    val mainCategory: LiveData<List<MainCategoryEntity>>
        get() = _mainCategory

    private val _subCategory = MutableLiveData<List<SubCategoryEntity>>()
    val subCategory: LiveData<List<SubCategoryEntity>>
        get() = _subCategory


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
    //
    init {
        getCategoryUseCase.getAllCategories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if(!it.data.isNullOrEmpty()) {
                        when {
                            it.data[0] is MainCategoryEntity -> {
                                _mainCategory.value = it.data as List<MainCategoryEntity>
                            }
                            it.data[0] is SubCategoryEntity -> {
                                _subCategory.value = it.data as List<SubCategoryEntity>
                            }
                            else -> {
                                // TODO: error 처리
                            }
                        }
                    }
                },
                {
                    // TODO: 카테고리 에러 처리
                },
                {
                    // TODO: complete 처리
                },
                {
                    // TODO: onSubscribe 처리
                }
            )
    }

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
        // TODO: 수량 data가 필요해지면 추가
        // TODO: mapper 필요
        return Ingredient(quantity = 1, category = ingreCategory.value?: "", name = ingreName.value?: "", purchasedDate = ingreDatePurchased.value.toString(), expiryDate = ingreDateExpiry.value.toString(), fridgeId = ingreFridgeId.value?: -1, storage = ingreStorage.value?:Storage.Fridge)
    }

    fun setFridge() {
        ingreStorage.value = Storage.Fridge
    }

    fun setFreeze() {
        ingreStorage.value = Storage.Freeze
    }

    fun setRoom() {
        ingreStorage.value = Storage.Room
    }



    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun clearData() {
        clearError()
        ingreName.value = ""
        ingreDatePurchased.value = ""
        ingreDateExpiry.value = ""
        ingreStorage.value = Storage.Fridge
        ingreCategory.value = ""
        ingreFridgeId.value = -1
    }
}