package com.sp.presentation.request

import com.sp.domain.member.RequestException
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

/**
 * @author Jaedoo Lee
 */
internal class MemberProfileRequestTest {

    @Test
    @DisplayName("email이 빈 값일때")
    fun `request_검증1`() {
        val request = MemberProfileRequest(
            email = "",
            nickname = "닉네임",
            oldPassword = "old",
            newPassword = "new"
        )

        assertThrows<RequestException> { request.validate() }
    }

    @Test
    @DisplayName("email이 null 일때")
    fun `request_검증2`() {
        val request = MemberProfileRequest(
            email = null,
            nickname = "닉네임",
            oldPassword = "old",
            newPassword = "new"
        )

        assertNotNull(request.validate())
    }

    @Test
    @DisplayName("oldPassword값이 있는데 newPassword가 null일 때 ")
    fun `request_검증3`() {
        val request = MemberProfileRequest(
            email = null,
            nickname = "닉네임",
            oldPassword = "old",
            newPassword = null
        )

        assertThrows<RequestException> { request.validate() }
    }

    @Test
    @DisplayName("oldPassword값이 있는데 newPassword가 빈 값일 때 ")
    fun `request_검증4`() {
        val request = MemberProfileRequest(
            email = null,
            nickname = "닉네임",
            oldPassword = "old",
            newPassword = ""
        )

        assertThrows<RequestException> { request.validate() }
    }

    @Test
    @DisplayName("닉네임만 변경")
    fun `request_검증5`() {
        val request = MemberProfileRequest(
            email = null,
            nickname = "닉네임",
            oldPassword = null,
            newPassword = null
        )

        assertNotNull(request.validate())
    }
}
