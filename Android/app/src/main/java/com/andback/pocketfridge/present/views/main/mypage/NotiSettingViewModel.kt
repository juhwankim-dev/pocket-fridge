package com.andback.pocketfridge.present.views.main.mypage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andback.pocketfridge.domain.usecase.datastore.ReadDataStoreUseCase
import com.andback.pocketfridge.domain.usecase.datastore.WriteDataStoreUseCase
import com.andback.pocketfridge.present.config.EXPIRY_DATE_PREFERENCE
import com.andback.pocketfridge.present.config.EXPIRY_NOTI_ACCEPTED
import com.andback.pocketfridge.present.config.EXPIRY_NOTI_HOUR
import com.andback.pocketfridge.present.config.EXPIRY_NOTI_MINUTE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class NotiSettingViewModel @Inject constructor(
    private val readDataStoreUseCase: ReadDataStoreUseCase,
    private val writeDataStoreUseCase: WriteDataStoreUseCase
): ViewModel() {
    private val _savedHour = MutableLiveData<Int>()
    val savedHour: LiveData<Int> = _savedHour
    private val _savedMinute = MutableLiveData<Int>()
    val savedMinute: LiveData<Int> = _savedMinute
    private val _savedAccepted = MutableLiveData<Boolean>()
    val savedAccepted: LiveData<Boolean> = _savedAccepted
    private val _savedOffset = MutableLiveData<Int>()
    val savedOffset: LiveData<Int> = _savedOffset

    init {
        viewModelScope.launch {
            getPref()
        }
    }

    private suspend fun getPref() {
        val hour = readDataStoreUseCase.execute(EXPIRY_NOTI_HOUR)
        val minute = readDataStoreUseCase.execute(EXPIRY_NOTI_MINUTE)
        val accepted = readDataStoreUseCase.execute(EXPIRY_NOTI_ACCEPTED)
        val offset = readDataStoreUseCase.execute(EXPIRY_DATE_PREFERENCE)

        val now = Calendar.getInstance()

        if(hour != null) {
            _savedHour.postValue(hour.toInt())
        } else {
            _savedHour.postValue(now.get(Calendar.HOUR_OF_DAY))
        }

        if(minute != null) {
            _savedMinute.postValue(minute.toInt())
        } else {
            _savedMinute.postValue(now.get(Calendar.MINUTE))
        }

        if(accepted != null) _savedAccepted.postValue(accepted.toBoolean())
        if(offset != null) _savedOffset.postValue(offset.toInt())
    }

    fun updateHour(hour: Int) {
        _savedHour.value = hour
    }

    fun updateMin(min: Int) {
        _savedMinute.value = min
    }

    fun updateAccepted(accepted: Boolean) {
        _savedAccepted.value = accepted
    }

    fun updateOffset(offset: Int) {
        if(offset == _savedOffset.value) return
        _savedOffset.value = offset
    }

    suspend fun save() {
        val hour = savedHour.value
        val min = savedMinute.value
        val accepted = savedAccepted.value
        val offset = savedOffset.value

        if(hour != null) writeDataStoreUseCase.execute(EXPIRY_NOTI_HOUR, hour.toString())
        if(min != null) writeDataStoreUseCase.execute(EXPIRY_NOTI_MINUTE, min.toString())
        if(accepted != null) writeDataStoreUseCase.execute(EXPIRY_NOTI_ACCEPTED, accepted.toString())
        if(offset != null) writeDataStoreUseCase.execute(EXPIRY_DATE_PREFERENCE, offset.toString())
    }
}