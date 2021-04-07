package com.sp.domain

/**
 * @author Jaedoo Lee
 */
abstract class TokenException(val errorCode: ErrorCode) : RuntimeException() {

    override val message: String = MessageConverter.getMessage(errorCode.code)
}
