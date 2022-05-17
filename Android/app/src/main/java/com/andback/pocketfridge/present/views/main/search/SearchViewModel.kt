package com.andback.pocketfridge.present.views.main.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andback.pocketfridge.data.model.FridgeEntity
import com.andback.pocketfridge.domain.model.Ingredient
import com.andback.pocketfridge.domain.usecase.category.GetCategoryUseCase
import com.andback.pocketfridge.domain.usecase.fridge.GetFridgesUseCase
import com.andback.pocketfridge.domain.usecase.ingredient.GetAllIngreListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getFridgesUseCase: GetFridgesUseCase,
    private val getAllIngreListUseCase: GetAllIngreListUseCase,
    private val getCategoryUseCase: GetCategoryUseCase
) : ViewModel() {
    private val compositeDisposable = CompositeDisposable()

    private val _fridges = MutableLiveData<List<FridgeEntity>>()
    val fridges: LiveData<List<FridgeEntity>> get() = _fridges
    private val _ingreList = MutableLiveData<List<Ingredient>>()
    val ingreList: LiveData<List<Ingredient>> get() = _ingreList
    private val _toastMsg = MutableLiveData<String>()
    val toastMsg: LiveData<String> get() = _toastMsg


    init {
        getFridges()
    }

    fun getFridges() {
        compositeDisposable.add(
            getFridgesUseCase()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        it.data?.let { list ->
                            _fridges.value = list
                        }
                        getAllIngreList()
                    },
                    {
                        showError(it)
                    }
                )
        )
    }

    private fun getAllIngreList() {
        compositeDisposable.add(
            getAllIngreListUseCase()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        it.data?.let { list ->
                            _ingreList.value = list
                        }
                    },
                    {
                        showError(it)
                    }
                )
        )
    }

    fun getSubCategory() {
        compositeDisposable.add(
            getCategoryUseCase.getSubCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {
                        // TODO : 카테고리 이미지 가져오기
                    },
                    {
                        showError(it)
                    }
                )
        )
    }

    private fun showError(t : Throwable) {
        if (t is HttpException && (t.code() in 400 until 500)){
            val responseBody = t.response()?.errorBody()?.string()
            val jsonObject = JSONObject(responseBody!!.trim())
            val message = jsonObject.getString("message")
            _toastMsg.value = message
            Log.d("LoginViewModel", "${t.code()}")
        } else {
            _toastMsg.value = t.message
        }
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}