package com.sp.domain.member.model

import com.sp.enums.JoinRoute
import com.sp.enums.MemberType

/**
 * @author Jaedoo Lee
 */
data class MemberRegisterModel(
    val email: String,
    val password: String,
    val nickname: String,
    val memberType: MemberType,
    val joinRoute: JoinRoute
)
