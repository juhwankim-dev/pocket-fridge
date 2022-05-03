package com.andback.pocketfridge.present.views.main.fridge

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andback.pocketfridge.data.model.SubCategoryEntity
import com.andback.pocketfridge.domain.model.Ingredient
import com.andback.pocketfridge.domain.usecase.category.GetCategoryUseCase
import com.andback.pocketfridge.domain.usecase.ingredient.DeleteIngreUseCase
import com.andback.pocketfridge.domain.usecase.ingredient.UpdateIngreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class IngreDetailViewModel @Inject constructor(
    private val deleteIngreUseCase: DeleteIngreUseCase,
    private val getCategoryUseCase: GetCategoryUseCase
): ViewModel() {
    private val _selectedIngre = MutableLiveData<Ingredient>()
    val selectedIngre: LiveData<Ingredient> = _selectedIngre

    private val _subCategory = MutableLiveData<SubCategoryEntity>()
    val subCategory: LiveData<SubCategoryEntity> = _subCategory

    private lateinit var subCategories: List<SubCategoryEntity>

    init {
        getSubCategories()
    }

    /**
     * 식재료와 서브 카테고리 세팅
     */
    fun selectIngre(ingre: Ingredient) {
        _selectedIngre.value = ingre
        setSubCategory(ingre)?.let {
            Log.d(TAG, "selectIngre: $it")
            _subCategory.value = it
        }
    }
    
    fun deleteIngre() {
        selectedIngre.value?.let { 
            deleteIngreUseCase(it.id).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        // TODO: 삭제 성공
                    },
                    {
                        // TODO: 예외 처리 
                    }
                )
        }
    }

    /**
     * 서브 카테고리를 받아오면 식재료의 값에 따라 서브카테고리 세팅
     */
    private fun getSubCategories() {
        getCategoryUseCase.getSubCategory().subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    subCategories = it.data!!
                    Log.d(TAG, "getSubCategories: ${_selectedIngre.value}")
                    _selectedIngre.value?.let { ingre -> _subCategory.value = setSubCategory(ingre) }
                },
                {

                }
            )
    }

    private fun setSubCategory(ingre: Ingredient): SubCategoryEntity? {
        return if(this::subCategories.isInitialized) {
            subCategories.find { it.subCategoryId == ingre.category }
        } else null
    }
    
    companion object {
        private const val TAG = "IngreDetailViewModel_debuk"
    }
}