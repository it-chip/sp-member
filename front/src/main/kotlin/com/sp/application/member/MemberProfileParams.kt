package com.sp.application.member

import com.sp.domain.member.model.MemberUpdateModel

/**
 * @author Jaedoo Lee
 */
data class MemberProfileParams(
    val no: Long,
    val email: String?,
    val nickname: String?,
    val oldPassword: String?,
    val newPassword: String?
) {
    fun toModel() = MemberUpdateModel(
        no = no,
        email = email,
        nickname = nickname,
        newPassword = newPassword
    )
}
