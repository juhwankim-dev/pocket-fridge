package com.andback.pocketfridge.present.utils

import com.andback.pocketfridge.domain.model.Ingredient
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test
import java.util.*


class IngreCheckerTest {
    private lateinit var normalIngre: Ingredient
    private lateinit var minusOneFridgeId: Ingredient
    private lateinit var blankName: Ingredient
    private lateinit var zeroQuantity: Ingredient
    private lateinit var minusCategory: Ingredient
    private lateinit var nonFormatDateString: String
    private lateinit var normalDateString: String
    private lateinit var now: Date
    private lateinit var futurePurchased: Ingredient
    private lateinit var pastPurchased: Ingredient
    private lateinit var wrongExpiryDate: Ingredient

    @Before
    fun setUp() {
        normalIngre = Ingredient(1, 1, 1, "2022-05-01", "2022-05-10", "가지", 1, Storage.Fridge, 9)
        minusOneFridgeId = Ingredient(1, 1, 1, "2022-05-01", "2022-05-10", "가지", -1, Storage.Fridge, 9)
        blankName = Ingredient(1, 1, 1, "2022-05-01", "2022-05-10", "", 1, Storage.Fridge, 9)
        zeroQuantity = Ingredient(1, 1, 0, "2022-05-01", "2022-05-10", "가지", 1, Storage.Fridge, 9)
        minusCategory = Ingredient(1, -1, 1, "2022-05-01", "2022-05-10", "가지", 1, Storage.Fridge, 9)
        nonFormatDateString = "20220501"
        normalDateString = "2022-05-01"
        val cal = Calendar.getInstance().also {
            it.set(2022, 0, 1, 0, 0, 0)
        }
        now = cal.time
        futurePurchased = Ingredient(1, -1, 1, "2022-05-01", "2022-05-10", "가지", 1, Storage.Fridge, 9)
        pastPurchased = Ingredient(1, -1, 1, "2022-01-01", "2022-05-10", "가지", 1, Storage.Fridge, 9)
        wrongExpiryDate = Ingredient(1, -1, 1, "2022-05-11", "2022-05-10", "가지", 1, Storage.Fridge, 9)
    }

    @Test
    fun `빈 이름이면 false`() {
        val result = IngreChecker.checkName(blankName)
        assertThat(result).isFalse()
    }

    @Test
    fun `정상 이름이면 true`() {
        val result = IngreChecker.checkName(normalIngre)
        assertThat(result).isTrue()
    }

    @Test
    fun `냉장고 아이디가 -1이면 false`() {
        assertThat(IngreChecker.checkFridge(minusOneFridgeId)).isFalse()
    }

    @Test
    fun `정상 냉장고 아이디이면 true`() {
        assertThat(IngreChecker.checkFridge(normalIngre)).isTrue()
    }

    @Test
    fun `카테고리가 음수이면 false`() {
        val result = IngreChecker.checkCategory(minusCategory)
        assertThat(result).isFalse()
    }

    @Test
    fun `정상 카테고리이면 true`() {
        val result = IngreChecker.checkCategory(normalIngre)
        assertThat(result).isTrue()
    }

    @Test
    fun `스트링 날짜를 변환할 때 포맷이 맞지 않으면 null`() {
        val result = IngreChecker.convertDate(nonFormatDateString)
        assertThat(result).isEqualTo(null)
    }

    @Test
    fun `정상 스트링 날짜를 변환하면 null 아님`() {
        val result = IngreChecker.convertDate(normalDateString)
        assertThat(result).isNotNull()
    }

    @Test
    fun `2022-01-01보다 미래의 날짜의 구매일이면 false`() {
        val result = IngreChecker.checkDatePurchased(now, futurePurchased)
        assertThat(result).isFalse()
    }

    @Test
    fun `2022-01-01 포함 과거의 구매일이면 true`() {
        val result = IngreChecker.checkDatePurchased(now, pastPurchased)
        assertThat(result).isTrue()
    }

    @Test
    fun `구매일보다 이전의 유통기한이면 false`() {
        val result = IngreChecker.checkExpiryDate(wrongExpiryDate)
        assertThat(result).isFalse()
    }

    @Test
    fun `구매일 포함 이후의 유통기한이면 true`() {
        val result = IngreChecker.checkExpiryDate(normalIngre)
        assertThat(result).isTrue()
    }

    @Test(expected = IngreQuantityException::class)
    fun `개수 0이면 IngreQuantityException 발생`() {
        IngreChecker.check(zeroQuantity)
    }

    @Test(expected = IngreNameException::class)
    fun `빈 이름이면 IngreQuantityException 발생`() {
        IngreChecker.check(blankName)
    }
}