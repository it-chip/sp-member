package com.sp.domain.member.error

import com.sp.domain.*

/**
 * @author Jaedoo Lee
 */
enum class MemberErrorCode(override val simpleCode: String) : ErrorCode {

    NO_PERMISSION("M0001"),
    DUPLICATED_EMAIL("M0002"),
    TOKEN_EXPIRED("T0001");
    ;

    override val code: String
        get() = "error.member.$simpleCode"
}
