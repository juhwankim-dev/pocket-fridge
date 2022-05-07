package com.andback.pocketfridge.present.views.main.recipe.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andback.pocketfridge.data.model.CookingIngreEntity
import com.andback.pocketfridge.data.model.RecipeEntity
import com.andback.pocketfridge.data.model.RecipeStepEntity
import com.andback.pocketfridge.domain.usecase.recipe.GetCookingIngresUseCase
import com.andback.pocketfridge.domain.usecase.recipe.GetRecipeStepsUseCase
import com.andback.pocketfridge.present.config.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class CookSharedViewModel @Inject constructor(
    private val getCookingIngresUseCase: GetCookingIngresUseCase,
    private val getRecipeStepsUseCase: GetRecipeStepsUseCase
): ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    lateinit var selectedRecipe: RecipeEntity
    private val _cookingIngres = MutableLiveData<List<CookingIngreEntity>>()
    val cookingIngres: LiveData<List<CookingIngreEntity>> get() = _cookingIngres

    private val _recipeSteps = MutableLiveData<List<RecipeStepEntity>>()
    val recipeSteps: LiveData<List<RecipeStepEntity>> get() = _recipeSteps

    private val _isIngresLoaded = MutableLiveData<Boolean>()
    val isIngresLoaded: LiveData<Boolean> get() = _isIngresLoaded

    private val _isStepsLoaded = MutableLiveData<Boolean>()
    val isStepsLoaded: LiveData<Boolean> get() = _isStepsLoaded

    private val _toastMsg = SingleLiveEvent<String>()
    val toastMsg: LiveData<String> get() = _toastMsg

    fun getCookingIngres(id: Int) {
        _isIngresLoaded.value = false
        compositeDisposable.add(
            getCookingIngresUseCase(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        _isIngresLoaded.value = true
                        _cookingIngres.value = it.data!!
                    },
                    {
                        _isIngresLoaded.value = false
                        showError(it)
                    },
                )
        )
    }

    fun getRecipeSteps(id: Int) {
        _isStepsLoaded.value = false
        compositeDisposable.add(
            getRecipeStepsUseCase(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        _isStepsLoaded.value = true
                        _recipeSteps.value = it.data!!
                    },
                    {
                        _isStepsLoaded.value = false
                        showError(it)
                    },
                )
        )
    }

    private fun showError(t : Throwable) {
        if (t is HttpException && (t.code() in 400 until 500)){
            var responseBody = t.response()?.errorBody()?.string()
            val jsonObject = JSONObject(responseBody!!.trim())
            var message = jsonObject.getString("message")
            _toastMsg.value = message
            Log.d("CookSharedViewModel", "${t.code()}")
        } else {
            _toastMsg.value = t.message
        }
    }
}