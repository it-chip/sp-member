package com.sp.presentation.request

import com.sp.domain.member.model.*

/**
 * @author Jaedoo Lee
 */
data class MemberRegisterRequest(
    val email: String,
    val password: String,
    val nickname: String
) {
    fun valueOf() = MemberRegisterModel(
        email = email,
        password = password,
        nickname = nickname
    )
}
