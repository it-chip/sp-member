package com.sp.infrastructure.model

import com.sp.domain.member.entity.*

/**
 * @author Jaedoo Lee
 */
data class LoginInfoRequest(
    val memberNo: Long,
    val email: String,
    val nickname: String?
) {
    constructor(member: Member) : this(
        memberNo = member.no,
        email = member.email,
        nickname = member.nickname
    )
}
