package com.andback.pocketfridge.present.views.main.fridge

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andback.pocketfridge.data.model.FridgeEntity
import com.andback.pocketfridge.data.model.MainCategoryEntity
import com.andback.pocketfridge.domain.model.Ingredient
import com.andback.pocketfridge.domain.usecase.category.GetCategoryUseCase
import com.andback.pocketfridge.domain.usecase.fridge.GetFridgesUseCase
import com.andback.pocketfridge.domain.usecase.ingredient.DeleteIngreUseCase
import com.andback.pocketfridge.domain.usecase.ingredient.GetIngreListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class FridgeViewModel @Inject constructor(
    private val getFridgesUseCase: GetFridgesUseCase,
    private val getIngreListUseCase: GetIngreListUseCase,
    private val deleteIngreUseCase: DeleteIngreUseCase,
    private val getCategoryUseCase: GetCategoryUseCase
): ViewModel() {
    // data
    private val _fridges = MutableLiveData<List<FridgeEntity>>()
    val fridges: LiveData<List<FridgeEntity>> get() = _fridges
    private val _selectedFridge = MutableLiveData<FridgeEntity>()
    val selectedFridge: LiveData<FridgeEntity> get() = _selectedFridge
    private val _ingreList = MutableLiveData<List<Ingredient>>()
    val ingreList: LiveData<List<Ingredient>> get() = _ingreList
    private val _mainCategoryList = MutableLiveData<List<MainCategoryEntity>>()
    val mainCategoryList: LiveData<List<MainCategoryEntity>> get() = _mainCategoryList

    // view state
    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> get() = _isLoading

    /**
     * 뷰모델이 처음 생성될 때, 냉장고 리스트 요청
     * 냉장고 리스트 받기 성공 -> 첫번째 냉장고 id로 식재료 리스트 요청
     */
    init {
        // TODO: user 정보 획득 후 이메일 변환
        getFridgesUseCase.excute("ms001118@gmail.com")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    it.data?.let { list ->
                        _fridges.value = list
                        setFridgeForChangingIngreList(list[0].refrigeratorId)
                    }
                },
                {
                    // TODO: 예외 ui 처리
                }
            )
    }

    private fun getIngreList(fridgeId: Int) {
        _isLoading.value = true
        getIngreListUseCase(fridgeId).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _isLoading.value = false
                    it.data?.let { list ->
                        _ingreList.value = list
                    }
                },
                {
                    // TODO: 예외 ui 처리
                    _isLoading.value = false
                }
            )
    }

    /**
     * 냉장고를 선택하면 그에 맞는 재료 리스트까지 업데이트
     */
    private fun setFridgeForChangingIngreList(id: Int) {
        val selectedFridge = fridges.value?.find { it.refrigeratorId == id }?: return
        _selectedFridge.value = selectedFridge
        getIngreList(id)
    }

    /**
     * 식재료 id로 삭제
     */
    fun deleteIngreById(id: Int) {
        _isLoading.value = true
        deleteIngreUseCase(id).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _isLoading.value = false
                    removeIngreFromLiveData(id)
                },
                {
                    _isLoading.value = false
                    // TODO: 예외 처리
                }
            )
    }

    /**
     * chip에 띄우기 위해 메인 카테고리 목록을 불러옴
     */
    fun getMainCategory() {
        _isLoading.value = true
        getCategoryUseCase.getMainCategory()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    _isLoading.value = false
                    _mainCategoryList.value = it.data!!
                },
                {
                    _isLoading.value = false
                    // TODO: 예외 처리
                }
            )
    }

    private fun removeIngreFromLiveData(id: Int) {
        _ingreList.value?.let { list ->
            val index = list.indexOfFirst { it.id == id }
            val refreshedList = list.toMutableList().also {
                it.removeAt(index)
            }
            _ingreList.value = refreshedList
        }
    }
}