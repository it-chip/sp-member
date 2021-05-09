package com.sp.application.member

import com.sp.domain.member.model.*

/**
 * @author Jaedoo Lee
 */
data class MemberRegisterParams(
    val email: String,
    val password: String,
    val nickname: String
) {
    fun toModel() = MemberRegisterModel(
        email = email,
        password = password,
        nickname = nickname
    )
}
