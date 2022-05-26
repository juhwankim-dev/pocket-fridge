package com.andback.pocketfridge.data.db.dao

import android.util.Log
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.andback.pocketfridge.data.db.AppDatabase
import com.andback.pocketfridge.data.model.IngreEntity
import com.andback.pocketfridge.present.utils.Storage
import com.google.common.truth.Truth
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidTest
class IngreDaoTest {

    // 힐트 의존성 주입을 사용하기위한 룰
    @get:Rule
    var hiltTestRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db")
    lateinit var database: AppDatabase
    private lateinit var dao: IngreDao

    private val fridge0 = 0
    private val fridge1 = 1

    private val today = Calendar.getInstance().apply {
        set(2020, 0, 1)
    }
    private val todayString = SimpleDateFormat("yyyy-MM-dd").format(today.time)
    private val targetDay = Calendar.getInstance().apply {
        set(2020, 0, 4)
    }
    private val targetDayString = SimpleDateFormat("yyyy-MM-dd").format(targetDay.time)

    private val ingre1 = IngreEntity(todayString, "2020-01-03", "가지", Storage.Fridge.value, fridge0, 1, 1, 0)
    private val ingre2 = IngreEntity(todayString, "2020-01-04", "가지", Storage.Fridge.value, fridge0, 1, 1, 1)
    private val ingre3 = IngreEntity(todayString, "2020-01-05", "가지", Storage.Fridge.value, fridge0, 1, 1, 2)

    private val ingre4 = IngreEntity(todayString, "2020-01-03", "가지", Storage.Fridge.value, fridge1, 1, 1, 3)
    private val ingre5 = IngreEntity(todayString, "2020-01-04", "가지", Storage.Fridge.value, fridge1, 1, 1, 4)
    private val ingre6 = IngreEntity(todayString, "2020-01-05", "가지", Storage.Fridge.value, fridge1, 1, 1, 5)

    @Before
    fun setup() {
        // 의존성 주입을 위한 세팅
        hiltTestRule.inject()
        dao = database.ingreDao()
    }

    // 냉장고 Id 0으로 식재료 삽입 및 냉장고 id 0으로 조회
    @Test
    fun insertIngreWithFridgeId0AndSelectWithIt() {
        val completable = dao.insert(ingre1)
        completable.test().assertComplete()

        val single = dao.getIngreListByFridgeId(fridge0)
        single.test().assertValue(listOf(ingre1))
    }

    // 냉장고 Id 0으로 식재료 삽입 및 냉장고 id 1으로 조회
    @Test
    fun insertIngreWithFridgeId0AndSelectWithOtherFridgeId() {
        val completable = dao.insert(ingre1)
        completable.test().assertComplete()

        val single = dao.getIngreListByFridgeId(fridge1)
        single.test().assertValue(listOf())
    }

    // 냉장고 Id 0인 식재료 리스트 삽입 및 냉장고 id 0으로 조회
    @Test
    fun insertIngreListAndSelect() {
        val list = listOf(ingre1, ingre2, ingre3)
        val completable = dao.insertAll(list)

        completable.test().assertComplete()

        val single = dao.getIngreListByFridgeId(fridge0)
        single.test().assertValue(list)
    }

    // 냉장고 Id 0인 식재료 리스트 삽입 및 냉장고 id 1으로 조회
    @Test
    fun insertIngreListAndSelectWithOtherFridgeId() {
        val list = listOf(ingre1, ingre2, ingre3)

        val completable = dao.insertAll(list)

        completable.test().assertComplete()

        val single = dao.getIngreListByFridgeId(fridge1)
        single.test().assertValue(listOf())
    }

    // 오늘 날짜 2020-01-01, 유통기한 D-3인 식재료 4개 리턴
    @Test
    fun insertIngreListAndSelectWithExpiryDateIn3days() {
        val list = listOf(ingre1, ingre2, ingre3, ingre4, ingre5, ingre6)

        val completable = dao.insertAll(list)
        completable.test().assertComplete()

        val single = dao.getIngreListByDateBetween(todayString, targetDayString)
        single.test().assertResult(listOf(ingre1, ingre2, ingre4, ingre5))
    }

    // 식재료 Id로 entity 삭제, 삭제 개수 결과 1 반환
    @Test
    fun deleteIngreWithIdAndGetNumberOfDeletedEntity() {
        val list = listOf(ingre1, ingre2, ingre3)

        val completable = dao.insertAll(list)
        completable.test().assertComplete()

        val result = dao.deleteById(ingre3.foodIngredientId)
        assertThat(result).isEqualTo(1)
    }

    // 없는 식재료 Id로 entity 삭제, 삭제 개수 결과 0 반환
    @Test
    fun deleteIngreWithNoExistingIdAndGet0() {
        val list = listOf(ingre1, ingre2, ingre3)

        val completable = dao.insertAll(list)
        completable.test().assertComplete()

        val result = dao.deleteById(Int.MAX_VALUE)
        assertThat(result).isEqualTo(0)
    }

    //냉장고 id 0으로 식재료 삭제, 냉장고 id 1인 식재료만 존재
    @Test
    fun deleteIngreWithFridgeId0AndGetIngreListWithFridgeId3() {
        val list = listOf(ingre1, ingre2, ingre3, ingre4, ingre5, ingre6)
        val completable = dao.insertAll(list)
        completable.test().assertComplete()

        val result = dao.dropByFridgeId(fridge0)
        assertThat(result).isEqualTo(3)

        val completable1 = dao.getIngreListByFridgeId(fridge1)
        completable1.test().assertValue(listOf(ingre4, ingre5, ingre6))
    }


    @After
    fun tearDown() {
        database.close()
    }

    companion object {
        private const val TAG = "IngreDaoTest_debuk"
    }

}