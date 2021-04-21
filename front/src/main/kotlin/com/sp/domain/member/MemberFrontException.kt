package com.sp.domain.member

import com.sp.domain.*

/**
 * @author Jaedoo Lee
 */
abstract class MemberFrontException(
    val errorCode: ErrorCode,
    val args: Array<out Any> = emptyArray()
) : RuntimeException() {
    override val message: String = MessageConverter.getMessage(errorCode.code, *args)
}
