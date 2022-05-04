package com.andback.pocketfridge.present.utils

import com.google.common.truth.Truth

import org.junit.Test

class SignUpCheckerTest {

    // 정상
    val normalName = "김주환"
    val normalNickname = "살려줘"
    val normalEmail = "ssafy@gmail.com"
    val normalPw = "ssafy"
    val normalConfirmPw = "ssafy"

    val numberName = "1234"
    val englishName = "John"
    val numberNickName = "1234"
    val englishNickName = "Johnny"

    // 비정상
    val shortName = "얍"
    val longName = "가나다라마바사아자차카타파하가나다라마바사아자차카타파하"
    val specialCharName = "!@#$"
    val emptyName = ""
    val containSpaceName = "아이 디"

    val shortNickname = "얍"
    val longNickname = "가나다라마바사아자차카타파하"
    val specialCharNickname = "!@#$"
    val emptyNickname = ""
    val containSpaceNickname = "닉네 임"

    val notEmail = "ThisIsNotEmail"
    val emptyEmail = ""
    val containSpaceEmail = "ssafy @ google.com"

    val shortPw = "a123"
    val longPw = "a123456789123456"
    val onlyNumberPw = "12345"
    val emptyPw = ""
    val containSpacePw = "ssa fy"

    val differntConfirmPw = "ssafyssafy"
    val emptyConfirmPw = ""
    val containSpaceConfirmPw = "ssa fy"


    @Test
    fun `정상 이름이면 true`() {
        val result = SignUpChecker.validateName(normalName)
        Truth.assertThat(result.isValid).isTrue()
    }

    @Test
    fun `정상 닉네임이면 true`() {
        val result = SignUpChecker.validateNickname(normalNickname)
        Truth.assertThat(result.isValid).isTrue()
    }

    @Test
    fun `정상 이메일이면 true`() {
        val result = SignUpChecker.validateEmail(normalEmail)
        Truth.assertThat(result.isValid).isTrue()
    }

    @Test
    fun `정상 비밀번호면 true`() {
        val result = SignUpChecker.validatePw(normalPw)
        Truth.assertThat(result.isValid).isTrue()
    }

    @Test
    fun `정상 확인 비밀번호면 true`() {
        val result = SignUpChecker.validateConfirmPw(normalPw, normalConfirmPw)
        Truth.assertThat(result.isValid).isTrue()
    }

    @Test
    fun `숫자 이름이면 true`() {
        val result = SignUpChecker.validateName(numberName)
        Truth.assertThat(result.isValid).isTrue()
    }

    @Test
    fun `영어 이름이면 true`() {
        val result = SignUpChecker.validateName(englishName)
        Truth.assertThat(result.isValid).isTrue()
    }

    @Test
    fun `숫자 닉네임이면 true`() {
        val result = SignUpChecker.validateNickname(numberNickName)
        Truth.assertThat(result.isValid).isTrue()
    }

    @Test
    fun `영어 닉네임이면 true`() {
        val result = SignUpChecker.validateNickname(englishNickName)
        Truth.assertThat(result.isValid).isTrue()
    }

    // 이 아래로 비정상

    @Test
    fun `2자 미만 이름이면 false`() {
        val result = SignUpChecker.validateName(shortName)
        Truth.assertThat(result.isValid).isFalse()
    }

    @Test
    fun `15자 초과 이름이면 false`() {
        val result = SignUpChecker.validateName(longName)
        Truth.assertThat(result.isValid).isFalse()
    }

    @Test
    fun `특수문자 이름이면 false`() {
        val result = SignUpChecker.validateName(specialCharName)
        Truth.assertThat(result.isValid).isFalse()
    }

    @Test
    fun `빈 이름이면 false`() {
        val result = SignUpChecker.validateName(emptyName)
        Truth.assertThat(result.isValid).isFalse()
    }

    @Test
    fun `공백이 포함된 이름이면 false`() {
        val result = SignUpChecker.validateName(containSpaceName)
        Truth.assertThat(result.isValid).isFalse()
    }

    @Test
    fun `2자 미만 닉네임이면 false`() {
        val result = SignUpChecker.validateNickname(shortNickname)
        Truth.assertThat(result.isValid).isFalse()
    }

    @Test
    fun `10자 초과 닉네임이면 false`() {
        val result = SignUpChecker.validateNickname(longNickname)
        Truth.assertThat(result.isValid).isFalse()
    }

    @Test
    fun `특수문자 닉네임이면 false`() {
        val result = SignUpChecker.validateNickname(specialCharNickname)
        Truth.assertThat(result.isValid).isFalse()
    }

    @Test
    fun `빈 닉네임이면 false`() {
        val result = SignUpChecker.validateNickname(emptyNickname)
        Truth.assertThat(result.isValid).isFalse()
    }

    @Test
    fun `공백이 포함된 닉네임이면 false`() {
        val result = SignUpChecker.validateNickname(containSpaceNickname)
        Truth.assertThat(result.isValid).isFalse()
    }

    @Test
    fun `이메일이 아니면 false`() {
        val result = SignUpChecker.validateEmail(notEmail)
        Truth.assertThat(result.isValid).isFalse()
    }

    @Test
    fun `빈 이메일이면 false`() {
        val result = SignUpChecker.validateEmail(emptyEmail)
        Truth.assertThat(result.isValid).isFalse()
    }

    @Test
    fun `공백이 포함된 이메일이면 false`() {
        val result = SignUpChecker.validateEmail(containSpaceEmail)
        Truth.assertThat(result.isValid).isFalse()
    }

    @Test
    fun `비밀번호가 2자 미만이면 false`() {
        val result = SignUpChecker.validatePw(shortPw)
        Truth.assertThat(result.isValid).isFalse()
    }

    @Test
    fun `비밀번호가 15자 초과면 false`() {
        val result = SignUpChecker.validatePw(longPw)
        Truth.assertThat(result.isValid).isFalse()
    }

    @Test
    fun `숫자 비밀번호면 false`() {
        val result = SignUpChecker.validatePw(onlyNumberPw)
        Truth.assertThat(result.isValid).isFalse()
    }

    @Test
    fun `빈 비밀번호면 false`() {
        val result = SignUpChecker.validatePw(emptyPw)
        Truth.assertThat(result.isValid).isFalse()
    }

    @Test
    fun `공백이 포함된 비밀번호면 false`() {
        val result = SignUpChecker.validatePw(containSpacePw)
        Truth.assertThat(result.isValid).isFalse()
    }

    @Test
    fun `비밀번호가 다르면 false`() {
        val result = SignUpChecker.validateConfirmPw(normalPw, differntConfirmPw)
        Truth.assertThat(result.isValid).isFalse()
    }

    @Test
    fun `빈 확인 비밀번호면 false`() {
        val result = SignUpChecker.validateConfirmPw(normalPw, emptyConfirmPw)
        Truth.assertThat(result.isValid).isFalse()
    }

    @Test
    fun `공백이 포함된 확인 비밀번호면 false`() {
        val result = SignUpChecker.validateConfirmPw(normalPw, containSpaceConfirmPw)
        Truth.assertThat(result.isValid).isFalse()
    }
}