package com.sp.presentation.request

import com.sp.application.member.MemberProfileParams
import com.sp.domain.member.RequestException

/**
 * @author Jaedoo Lee
 */
data class MemberProfileRequest (
    val email: String?,
    val nickname: String?,
    val oldPassword: String?,
    val newPassword: String?
) {
    fun toModel(memberNo: Long) = MemberProfileParams(
        no = memberNo,
        email = email,
        nickname = nickname,
        oldPassword = oldPassword,
        newPassword = newPassword
    )

    fun validate() {
        when {
            email?.isBlank() == true -> throw RequestException("email : $email")
            nickname?.isBlank() == true -> throw RequestException("nickname : $nickname")
            oldPassword?.isBlank() == true -> throw RequestException("oldPassword : $oldPassword")
            newPassword?.isBlank() == true -> throw RequestException("newPassword : $newPassword")
            oldPassword?.isNotBlank() == true && newPassword.isNullOrBlank() -> throw RequestException("newPassword : $newPassword")
            newPassword?.isNotBlank() == true && oldPassword.isNullOrBlank() -> throw RequestException("oldPassword : $oldPassword")
        }
    }
}
