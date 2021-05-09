package com.sp.presentation.request

import com.sp.application.member.*
import com.sp.domain.member.util.*

/**
 * @author Jaedoo Lee
 */
data class MemberRegisterRequest(
    val email: String,
    val password: String,
    val nickname: String
) {
    fun valueOf() = MemberRegisterParams(
        email = email,
        password = MemberPasswordEncryptor.encode(password),
        nickname = nickname
    )
}
