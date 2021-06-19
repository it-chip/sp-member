package com.sp.presentation.request

import com.sp.application.member.MemberRegisterParams
import com.sp.domain.member.util.MemberPasswordEncryptor
import com.sp.enums.JoinRoute
import com.sp.enums.MemberType

/**
 * @author Jaedoo Lee
 */
data class MemberRegisterRequest(
    val email: String,
    val password: String,
    val nickname: String,
    val memberType: MemberType,
    val joinRoute: JoinRoute
) {
    fun valueOf() = MemberRegisterParams(
        email = email,
        password = MemberPasswordEncryptor.encode(password),
        nickname = nickname,
        memberType = memberType,
        joinRoute = joinRoute
    )
}
