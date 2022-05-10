package com.andback.pocketfridge.present.workmanager

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.*
import com.andback.pocketfridge.domain.usecase.datastore.ReadDataStoreUseCase
import com.andback.pocketfridge.domain.usecase.datastore.WriteDataStoreUseCase
import com.andback.pocketfridge.domain.usecase.notification.SendIngreExpiryNotiUseCase
import com.andback.pocketfridge.present.config.EXPIRY_DATE_NOTIFIED
import com.andback.pocketfridge.present.config.EXPIRY_NOTI_HOUR
import com.andback.pocketfridge.present.config.EXPIRY_NOTI_MINUTE
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.*
import java.util.*
import java.util.concurrent.TimeUnit

@HiltWorker
class DailyNotiWorker @AssistedInject constructor(
    @Assisted applicationContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val readDataStoreUseCase: ReadDataStoreUseCase,
    private val writeDataStoreUseCase: WriteDataStoreUseCase,
    private val sendIngreExpiryNoti: SendIngreExpiryNotiUseCase
) : CoroutineWorker(applicationContext, workerParams) {

    companion object {
        private const val TAG = "NotificationWorker_debuk"
        const val WORKER_NAME = "Noti worker"

        /**
         * work가 언제 실행되야 할 지 세팅하는 함수
         * 이 함수를 호출해야하는 경우
         * 1. 로그인 성공 했을 때
         */

        suspend fun runAt(context: Context, readDataStoreUseCase: ReadDataStoreUseCase) {
            // 알림 시각 가져오기
            val triggerHour = triggerHour(readDataStoreUseCase)
            val triggerMinute = triggerMinute(readDataStoreUseCase)

            // 세팅된 알림이 없으면 리턴
            if(triggerHour == -1 || triggerMinute == -1) return

            val now = Calendar.getInstance()
            Log.d(TAG, "run: now = $now")

            // 오늘 날짜를 기반으로 세팅된 시간 값
            val notiTime = GregorianCalendar.getInstance().apply {
                set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH), triggerHour, triggerMinute)
            }

            // 현재 시간이 예약된 시간과 같거나 지난 뒤
            // noti가 보여졌으면 다음 날로 미룸
            val plusDay = if (now == notiTime
                || now.after(notiTime)
                || isNotified(now, readDataStoreUseCase)
            ) 1 else 0

            now.add(Calendar.DAY_OF_MONTH, plusDay)
            Log.d(TAG, "run: added now = $now")

            val duration = now.get(Calendar.SECOND) - Calendar.getInstance().get(Calendar.SECOND)
            Log.d(TAG, "run: duration = $duration")

            val workRequest = OneTimeWorkRequestBuilder<DailyNotiWorker>()
                .setInitialDelay(duration.toLong(), TimeUnit.SECONDS)
                .build()

            WorkManager.getInstance(context)
                .enqueueUniqueWork(
                    WORKER_NAME,
                    ExistingWorkPolicy.REPLACE,
                    workRequest
                )
        }

        private suspend fun isNotified(now: Calendar, readDataStoreUseCase: ReadDataStoreUseCase): Boolean {
            val notifiedDate = readDataStoreUseCase.execute(EXPIRY_DATE_NOTIFIED)?: -1
            val today = now.get(Calendar.DAY_OF_MONTH)
            return notifiedDate == today
        }

        private suspend fun triggerHour(readDataStoreUseCase: ReadDataStoreUseCase): Int {
            val hour = readDataStoreUseCase.execute(EXPIRY_NOTI_HOUR)?.toInt()
            return hour?: -1
        }

        private suspend fun triggerMinute(readDataStoreUseCase: ReadDataStoreUseCase): Int {
            val minute = readDataStoreUseCase.execute(EXPIRY_NOTI_MINUTE)?.toInt()
            return minute?: -1
        }

        /**
         * 로그아웃, 회원 탈퇴 시 호출
         */
        fun cancel(context: Context) {
            WorkManager.getInstance(context)
                .cancelUniqueWork(WORKER_NAME)
        }
    }

    override suspend fun doWork(): Result = coroutineScope {
        try {
            // 알림 보내기
            sendIngreExpiryNoti()

            // 오늘 day로 NOTIFIED 값 설정
            val day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            writeDataStoreUseCase.execute(EXPIRY_DATE_NOTIFIED, day.toString())

            Result.success()
        } catch (e: Exception) {
            if (runAttemptCount > 3) {
                Result.success()
            } else {
                Result.retry()
            }
        } finally {
            // 다시 워커 설정
            runAt(applicationContext, readDataStoreUseCase)
        }
    }

}