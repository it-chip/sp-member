package com.sp.domain.member.error

import com.sp.domain.*

/**
 * @author Jaedoo Lee
 */
enum class MemberFrontErrorcode(override val simpleCode: String) : ErrorCode {

    INVALID_LOGIN_INFO("MF001"),
    ;

    override val code: String
        get() = "error.front.$simpleCode"
}
