package com.andback.pocketfridge.present.views.main.notification

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.andback.pocketfridge.data.model.NotificationEntity
import com.andback.pocketfridge.domain.repository.NotificationRepository
import com.andback.pocketfridge.domain.usecase.notification.GetNotificationListUseCase
import com.andback.pocketfridge.domain.usecase.notification.ReadNotificationUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(
    private val getNotificationListUseCase: GetNotificationListUseCase,
    private val readNotificationUseCase: ReadNotificationUseCase
): ViewModel() {
    private val compositeDisposable = CompositeDisposable()
    private val _notificationList = MutableLiveData<List<NotificationEntity>>()
    val notificationList: LiveData<List<NotificationEntity>> get() = _notificationList

    init {
        getNotifications()
    }

    fun getNotifications() {
        val single = getNotificationListUseCase().subscribeOn(Schedulers.io())
            .subscribe(
                { response ->
                    response.data?.let {
                        _notificationList.postValue(it)
                    }
                },
                {
                    // TODO: 알림 받기 실패 처리
                }
            )
        compositeDisposable.add(single)
    }

    fun readNotification() {
        val single1 = readNotificationUseCase().subscribeOn(Schedulers.io())
            .subscribe(
                {},
                {}
            )
        compositeDisposable.add(single1)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
    }
}