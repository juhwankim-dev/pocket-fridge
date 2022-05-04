package com.andback.pocketfridge.present.views.main.barcode

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andback.pocketfridge.domain.usecase.barcode.GetProductFromBarcodeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class BarcodeScanViewModel @Inject constructor(
    private val getProductFromBarcodeUseCase: GetProductFromBarcodeUseCase
) : ViewModel() {
    private val SUCCESS = "INFO-000"

    private val _productName = MutableLiveData<String>()
    val productName: LiveData<String> get() = _productName
    private val _networkError = MutableLiveData<Boolean>()
    val networkError: LiveData<Boolean> get() = _networkError


    fun getProductNameFromBarcode(barcode: String) {
        getProductFromBarcodeUseCase(barcode).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    val code = it.serviceName.result.code
                    val totalCnt = it.serviceName.totalCount.toInt()

                    if (code == SUCCESS && totalCnt > 0) {
                        _productName.value = it.serviceName.data[0].name
                    } else {
                        _productName.value = ""
                    }
                    _networkError.value = false
                },
                {
                    _networkError.value = true
                }
            )
    }

}