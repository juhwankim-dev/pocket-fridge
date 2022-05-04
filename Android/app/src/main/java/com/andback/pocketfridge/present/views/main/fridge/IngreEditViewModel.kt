package com.andback.pocketfridge.present.views.main.fridge

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
import com.andback.pocketfridge.domain.usecase.ingredient.UpdateIngreUseCase
import com.andback.pocketfridge.present.utils.*
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class IngreEditViewModel @Inject constructor(
    private val updateIngreUseCase: UpdateIngreUseCase,
    private val getCategoryUseCase: GetCategoryUseCase,
    private val getFridgesUseCase: GetFridgesUseCase
): ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val _isInitDone = MutableLiveData(0)
    val isInitDone: LiveData<Int> = _isInitDone

    private val _isUpdateSuccess = MutableLiveData(false)
    val isUpdateSuccess: LiveData<Boolean> = _isUpdateSuccess

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    // region view 상태
    // 데이터를 바탕으로 수정할 식재료 객체 생성
    private val _selectedFridge = MutableLiveData<FridgeEntity>()
    val selectedFridge: LiveData<FridgeEntity> get() = _selectedFridge
    private val _selectedStorage = MutableLiveData<Storage>()
    val selectedStorage: LiveData<Storage> get() = _selectedStorage
    private val _selectedMainCategory = MutableLiveData<MainCategoryEntity>()
    val selectedMainCategory: LiveData<MainCategoryEntity> get() = _selectedMainCategory
    private val _selectedSubCategory = MutableLiveData<SubCategoryEntity>()
    val selectedSubCategory: LiveData<SubCategoryEntity> get() = _selectedSubCategory
    private val _selectedSubCategories = MutableLiveData<List<SubCategoryEntity>>()
    val selectedSubCategories: LiveData<List<SubCategoryEntity>> get() = _selectedSubCategories
    val name = MutableLiveData<String>()
    val datePurchased = MutableLiveData<String>()
    val dateExpiry = MutableLiveData<String>()
    private var ingreId: Int = -1
    // endregion

    // region 카테고리 리스트 라이브 데이터
    private val _mainCategories = MutableLiveData<List<MainCategoryEntity>>()
    val mainCategories: LiveData<List<MainCategoryEntity>> get() = _mainCategories

    private val _subCategories = MutableLiveData<List<SubCategoryEntity>>()
    val subCategories: LiveData<List<SubCategoryEntity>> get() = _subCategories
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

    var updatedIngredient: Ingredient? = null

    init {
        getCategories()
        getFridges()
    }

    fun init(ingredient: Ingredient) {
        name.value = ingredient.name
        _selectedStorage.value = ingredient.storage
        datePurchased.value = ingredient.purchasedDate
        dateExpiry.value = ingredient.expiryDate
        ingreId = ingredient.id
        subCategories.value?.let {
            val result = it.find { element -> ingredient.category == element.subCategoryId }
            _selectedSubCategory.value = result!!
        }
        fridges.value?.let { list ->
            val fridge = list.find { ingredient.fridgeId == it.refrigeratorId }
            fridge?.let { _selectedFridge.value = it }
        }
    }

    fun onUpdateBtnClick() {
        val ingredient = getIngredientFromInput()
        compositeDisposable.add(
            updateIngreUseCase(ingredient)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        updatedIngredient = ingredient
                        _isUpdateSuccess.value = true
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
        val name = name.value?:""
        val quantity = 1
        val category = selectedSubCategory.value?.subCategoryId?: -1
        val purchasedDate = datePurchased.value.toString()
        val expiryDate = dateExpiry.value.toString()
        val fridgeId = selectedFridge.value?.refrigeratorId?: -1
        val storage = selectedStorage.value?:Storage.Fridge
        val id = ingreId
        return Ingredient(
            id = id,
            category = category,
            quantity = quantity,
            purchasedDate = purchasedDate,
            expiryDate = expiryDate,
            name = name,
            fridgeId = fridgeId,
            storage = storage
        )
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
        datePurchased.value = ""
        dateExpiry.value = ""
        _selectedStorage.value = Storage.Fridge
        _selectedFridge.value = getDefaultFridge()
        _selectedMainCategory.value = getDefaultMainCategory()
        _selectedSubCategory.value = getDefaultSubCategory()
        _isUpdateSuccess.value = false
        updatedIngredient = null
    }

    /**
     * 메인 카테고리의 첫 번째 값이 디폴트
     */
    private fun getDefaultMainCategory(): MainCategoryEntity? {
        return if(!mainCategories.value.isNullOrEmpty()) {
            mainCategories.value!![0]
        } else {
            null
        }
    }

    /**
     * 디폴트 메인 카테고리의 서브 카테고리의 첫 번째 값이 디폴트
     */
    private fun getDefaultSubCategory(): SubCategoryEntity? {
        return if(!subCategories.value.isNullOrEmpty() && selectedMainCategory.value != null) {
            _selectedSubCategories.value = subCategories.value!!.filter { it.mainCategoryId == selectedMainCategory.value!!.mainCategoryId }
            _selectedSubCategories.value!![0]
        } else {
            null
        }
    }

    fun updateSelectedSubCategories() {
        _selectedSubCategories.value = subCategories.value?.filter {
            it.mainCategoryId == selectedMainCategory.value?.mainCategoryId
        }
    }

    fun selectSubCategory(value: SubCategoryEntity) {
        _selectedSubCategory.value = value
    }

    fun selectMainCategory(value: MainCategoryEntity) {
        _selectedMainCategory.value = value
    }

    /**
     * 디폴트 냉장고는 냉장고 리스트의 첫 번째 값
     */
    private fun getDefaultFridge(): FridgeEntity? {
        return if(!fridges.value.isNullOrEmpty()) {
            fridges.value!![0]
        } else {
            null
        }
    }

    private fun getCategories() {
        compositeDisposable.add(
            getCategoryUseCase.getAllCategories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        if(!it.data.isNullOrEmpty()) {
                            when {
                                it.data[0] is MainCategoryEntity -> {
                                    _mainCategories.value = it.data as List<MainCategoryEntity>
                                    setDefaultData()
                                }
                                it.data[0] is SubCategoryEntity -> {
                                    _subCategories.value = it.data as List<SubCategoryEntity>
                                    setDefaultData()
                                }
                                else -> {
                                    // TODO: error 처리
                                }
                            }
                        }
                    },
                    {
                        // TODO: 카테고리 에러 처리
                        Log.d(TAG, "error: ${it.javaClass.canonicalName}")
                    },
                    {
                        _isInitDone.value = _isInitDone.value?.plus(1)
                    },
                    {
                        // TODO: onSubscribe 처리
                    }
                )
        )
    }

    fun getFridges() {
        if(!isLoading.value!!) {
            compositeDisposable.add(
                getFridgesUseCase.excute(getEmail())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            it.data?.let { list ->
                                _fridges.value = list
                                _isInitDone.value = _isInitDone.value?.plus(1)
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

    // TODO: 회원정보 가져오는 usecase 생성 후 처리
    fun getEmail(): String = "ms001118@gmail.com"

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }

    fun clearData() {
        clearError()
        setDefaultData()
    }

    companion object {
        private const val TAG = "IngreEditViewModel_debuk"
    }
}