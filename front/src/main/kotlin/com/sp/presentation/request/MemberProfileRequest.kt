package com.sp.presentation.request

import com.sp.application.member.*
import com.sp.domain.*

/**
 * @author Jaedoo Lee
 */
data class MemberProfileRequest (
    val nickname: String,
) {
    fun toModel(memberNo: Long) = MemberProfileParams(
        no = memberNo,
        nickname = nickname
    )

    fun validate() {
        if (nickname.isBlank()) throw RequestException("nickname : $nickname")
    }
}
