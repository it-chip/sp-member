package com.sp.domain.member.error

import com.sp.domain.ErrorCode

/**
 * @author Jaedoo Lee
 */
enum class MemberErrorCode(override val simpleCode: String) : ErrorCode {

    NO_PERMISSION("M0001"),
    DUPLICATED_EMAIL("M0002"),
    INVALID_PASSWORD("M0003"),
    TOKEN_EXPIRED("T0001");
    ;

    override val code: String
        get() = "error.member.$simpleCode"
}
