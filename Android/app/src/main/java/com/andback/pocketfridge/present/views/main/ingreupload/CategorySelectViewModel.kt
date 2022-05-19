package com.andback.pocketfridge.present.views.main.ingreupload

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andback.pocketfridge.data.model.MainCategoryEntity
import com.andback.pocketfridge.data.model.SubCategoryEntity
import com.andback.pocketfridge.domain.usecase.category.GetCategoryUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class CategorySelectViewModel @Inject constructor(
    private val getCategoryUseCase: GetCategoryUseCase
): ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    // region 카테고리 리스트 라이브 데이터
    private val _mainCategories = MutableLiveData<List<MainCategoryEntity>>()
    val mainCategories: LiveData<List<MainCategoryEntity>> get() = _mainCategories

    private val _subCategories = MutableLiveData<List<SubCategoryEntity>>()
    val subCategories: LiveData<List<SubCategoryEntity>> get() = _subCategories
    // endregion

    private val _selectedMainCategory = MutableLiveData<MainCategoryEntity>()
    val selectedMainCategory: LiveData<MainCategoryEntity> get() = _selectedMainCategory
    private val _selectedSubCategory = MutableLiveData<SubCategoryEntity>()
    val selectedSubCategory: LiveData<SubCategoryEntity> get() = _selectedSubCategory
    private val _selectedSubCategories = MutableLiveData<List<SubCategoryEntity>>()
    val selectedSubCategories: LiveData<List<SubCategoryEntity>> get() = _selectedSubCategories

    init {
        getCategories()
    }

    private fun getCategories() {
        val observable = getCategoryUseCase.getAllCategories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    if(!it.data.isNullOrEmpty()) {
                        when {
                            it.data[0] is MainCategoryEntity -> {
                                _mainCategories.value = it.data as List<MainCategoryEntity>
                            }
                            it.data[0] is SubCategoryEntity -> {
                                _subCategories.value = it.data as List<SubCategoryEntity>
                            }
                            else -> {
                                // TODO: error 처리
                            }
                        }
                        getDefaultMainCategory()
                        getDefaultSubCategory()
                    }
                },
                {
                    // TODO: 카테고리 에러 처리
                    Log.d(TAG, "error: ${it.javaClass.canonicalName}")
                },
                {
                    // TODO: complete 처리
                },
                {
                    // TODO: onSubscribe 처리
                }
            )
        compositeDisposable.add(observable)
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
        val result = subCategories.value?.filter {
            it.mainCategoryId == selectedMainCategory.value?.mainCategoryId
        }
        result?.let {
            _selectedSubCategories.value = it
        }
    }

    fun selectMainCategory(value: MainCategoryEntity) {
        _selectedMainCategory.value = value
    }

    fun selectAllSubCategory() {
        subCategories.value?.let { _selectedSubCategories.value = it }
    }

    companion object {
        private const val TAG = "CategorySelectViewModel_debuk"
    }
}