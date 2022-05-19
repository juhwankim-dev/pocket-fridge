package com.andback.pocketfridge.present.views.main.ingreupload

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andback.pocketfridge.data.model.FridgeEntity
import com.andback.pocketfridge.data.model.MainCategoryEntity
import com.andback.pocketfridge.data.model.SubCategoryEntity
import com.andback.pocketfridge.domain.model.Ingredient
import com.andback.pocketfridge.domain.usecase.category.GetCategoryUseCase
import com.andback.pocketfridge.domain.usecase.fridge.GetFridgesUseCase
import com.andback.pocketfridge.domain.usecase.ingredient.UploadIngreUseCase
import com.andback.pocketfridge.present.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

private const val TAG = "IngreUploadViewModel_debuk"
@HiltViewModel
class IngreUploadViewModel @Inject constructor(
    private val uploadIngreUseCase: UploadIngreUseCase,
    private val getFridgesUseCase: GetFridgesUseCase
): ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    // region view 상태
    // 데이터를 바탕으로 업로드할 식재료 객체 생성
    private val _selectedFridge = MutableLiveData<FridgeEntity>()
    val selectedFridge: LiveData<FridgeEntity> get() = _selectedFridge
    private val _selectedStorage = MutableLiveData<Storage>()
    val selectedStorage: LiveData<Storage> get() = _selectedStorage
    private val _selectedSubCategory = MutableLiveData<SubCategoryEntity>()
    val selectedSubCategory: LiveData<SubCategoryEntity> get() = _selectedSubCategory
    val name = MutableLiveData<String>()
    val datePurchased = MutableLiveData<String>()
    val dateExpiry = MutableLiveData<String>()
    // endregion

    // region 냉장고 리스트 라이브 데이터
    private val _fridges = MutableLiveData<List<FridgeEntity>>()
    val fridges: LiveData<List<FridgeEntity>> get() = _fridges
    // endregion

    // region 에러 라이브 데이터
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

    // endregion

    private val _isUploadSuccess = MutableLiveData(false)
    val isUploadSuccess: LiveData<Boolean> get() = _isUploadSuccess

    init {
        getFridges()
        setPurchaseDateToday()
    }

    private fun setPurchaseDateToday() {
        datePurchased.value = SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().time)
    }

    fun onUploadBtnClick() {
        compositeDisposable.add(
            uploadIngreUseCase.uploadIngre(getIngredientFromInput())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        _isUploadSuccess.value = true
                    },
                    { throwable ->
                        handleException(throwable)
                    },
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

    fun clearError() {
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
        return Ingredient(quantity = 1, subCategory = selectedSubCategory.value?.subCategoryId?: -1, mainCategory = -1, name = name.value?: "", purchasedDate = datePurchased.value.toString(), expiryDate = dateExpiry.value.toString(), fridgeId = selectedFridge.value?.id?: -1, storage = selectedStorage.value?:Storage.Fridge)
    }

    fun setFridge() {
        _selectedStorage.value = Storage.Fridge
    }

    fun setFreeze() {
        _selectedStorage.value = Storage.Freeze
    }

    fun setRoom() {
        _selectedStorage.value = Storage.Room
    }

    fun setFridge(fridge: FridgeEntity) {
        _selectedFridge.value = fridge
    }

    private fun setDefaultData() {
        name.value = ""
        dateExpiry.value = ""
        _selectedStorage.value = Storage.Fridge
        _selectedFridge.value = getDefaultFridge()
        _isUploadSuccess.value = false
    }

    fun selectSubCategory(value: SubCategoryEntity) {
        _selectedSubCategory.value = value
    }

    /**
     * 디폴트 냉장고는 냉장고 리스트의 첫 번째 값
     */
    private fun getDefaultFridge(): FridgeEntity? {
        return if(!fridges.value.isNullOrEmpty()) {
            Log.d(TAG, "getDefaultFridge: ${fridges.value!![0]}")
            fridges.value!![0]
        } else {
            null
        }
    }

    fun getFridges() {
        if(!isLoading.value!!) {
            compositeDisposable.add(
                getFridgesUseCase()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            it.data?.let { list ->
                                _fridges.value = list.filter { fridge -> fridge.isOwner }
                                _selectedFridge.value = getDefaultFridge()
                            }
                            _isLoading.value = false
                        },
                        {
                            _isLoading.value = false
                            // TODO: 냉장고 리스트 fail ui 처리
                        },
                    )
            )
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
        Log.d(TAG, "onCleared: ")
    }

    fun clearData() {
        clearError()
        setDefaultData()
    }
}