package com.sp.presentation.request

import com.sp.domain.member.model.*
import com.sp.domain.member.util.*

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
        password = MemberPasswordEncryptor.encode(password),
        nickname = nickname
    )
}
