package com.sp.application.member

import com.sp.domain.member.model.MemberRegisterModel
import com.sp.enums.JoinRoute
import com.sp.enums.MemberType

/**
 * @author Jaedoo Lee
 */
data class MemberRegisterParams(
    val email: String,
    val password: String,
    val nickname: String,
    val memberType: MemberType,
    val joinRoute: JoinRoute
) {
    fun toModel() = MemberRegisterModel(
        email = email,
        password = password,
        nickname = nickname,
        memberType = memberType,
        joinRoute = joinRoute
    )
}
