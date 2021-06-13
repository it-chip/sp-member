package com.sp.domain.member.model

/**
 * @author Jaedoo Lee
 */
data class MemberUpdateModel(
    val no: Long,
    val email: String?,
    val nickname: String?,
    val newPassword: String?
)
