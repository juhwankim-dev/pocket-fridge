package com.andback.pocketfridge.present.views.main.fridge

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andback.pocketfridge.domain.model.Ingredient
import com.andback.pocketfridge.domain.usecase.ingredient.DeleteIngreUseCase
import com.andback.pocketfridge.domain.usecase.ingredient.UpdateIngreUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class IngreDetailViewModel @Inject constructor(
    private val deleteIngreUseCase: DeleteIngreUseCase,
): ViewModel() {
    private val _selectedIngre = MutableLiveData<Ingredient>()
    val selectedIngre: LiveData<Ingredient> = _selectedIngre
    
    fun selectIngre(ingre: Ingredient) {
        _selectedIngre.value = ingre
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
}