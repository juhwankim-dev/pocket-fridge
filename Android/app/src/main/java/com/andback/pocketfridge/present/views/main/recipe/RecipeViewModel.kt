package com.andback.pocketfridge.present.views.main.recipe

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andback.pocketfridge.data.model.RecipeEntity
import com.andback.pocketfridge.domain.usecase.recipe.GetRecipesUseCase
import com.andback.pocketfridge.present.config.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class RecipeViewModel @Inject constructor(
    private val getRecipesUseCase: GetRecipesUseCase
): ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val _toastMsg = SingleLiveEvent<String>()
    val toastMsg: LiveData<String> get() = _toastMsg

    private val _recipes = MutableLiveData<List<RecipeEntity>>()
    val recipes: LiveData<List<RecipeEntity>> get() = _recipes

    fun getRecipes() {
        _isLoading.value = true

        compositeDisposable.add(
            getRecipesUseCase()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        _isLoading.value = false
                        _recipes.value = it.data!!
                    },
                    {
                        _isLoading.value = false
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
            Log.d("RecipeViewModel", "${t.code()}")
        } else {
            _toastMsg.value = t.message
        }
    }
}