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
import java.lang.Exception
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
        when(e) {
            is HttpException -> {
                // TODO: 서버 통신 에러
            }
            is IOException -> {
                // TODO: 네트워크 에러
            }
            is IngreNameException -> {
                // TODO: 재료 이름 에러
            }
            is IngreQuantityException -> {
                // TODO: 재료 수량 에러
            }
            is IngreFridgeIdException -> {
                // TODO: 냉장고 id 에러
            }
            is IngreCategoryException -> {
                // TODO: 재료 카테고리 에러
            }
            is IngreDatePurchasedException -> {
                // TODO: 재료 구매일 에러
            }
            is IngreDateExpiryException -> {
                // TODO: 재료 유통기한 에러
            }
        }
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