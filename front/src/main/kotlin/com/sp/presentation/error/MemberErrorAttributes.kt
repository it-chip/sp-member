package com.sp.presentation.error

import com.sp.domain.*
import com.sp.domain.member.*
import org.springframework.beans.*
import org.springframework.boot.web.reactive.error.*
import org.springframework.core.annotation.*
import org.springframework.core.codec.*
import org.springframework.http.*
import org.springframework.stereotype.*
import org.springframework.web.bind.annotation.*
import org.springframework.web.reactive.function.server.*
import org.springframework.web.server.*
import java.time.*

/**
 * @author Jaedoo Lee
 */
@Component
class MemberErrorAttributes : ErrorAttributes {
    override fun getError(request: ServerRequest): Throwable {
        return request.attributeOrNull("MEMBER") as Throwable?
            ?: IllegalStateException("Missing exception attribute in ServerWebExchange")
    }

    override fun getErrorAttributes(request: ServerRequest, includeStackTrace: Boolean): Map<String, Any> {
        val attributes = linkedMapOf<String, Any>()
        attributes["timestamp"] = LocalDateTime.now()
        attributes["path"] = "${request.methodName()} ${request.path()}"
        val error = getError(request)
        val httpStatus = determineHttpStatus(error)
        attributes["status"] = httpStatus.value()
        attributes["error"] = httpStatus.reasonPhrase
        val internalApi = request.path().startsWith("/internal")
        attributes["code"] = determineCode(error)
        attributes["message"] = determineMessage(error, internalApi)

        return attributes
    }

    override fun storeErrorInformation(error: Throwable?, exchange: ServerWebExchange) {
        exchange.attributes.putIfAbsent("MEMBER", error)
    }

    private fun determineHttpStatus(error: Throwable): HttpStatus = when (error) {
        is ResponseStatusException -> error.status
        is TypeMismatchException, is DecodingException, is NumberFormatException -> HttpStatus.BAD_REQUEST
        is MemberFrontException -> AnnotatedElementUtils.findMergedAnnotation(
            error::class.java,
            ResponseStatus::class.java
        )
            ?.code
            ?: HttpStatus.BAD_REQUEST
        is TokenException -> HttpStatus.UNAUTHORIZED
        else -> HttpStatus.INTERNAL_SERVER_ERROR
    }

    private fun determineCode(error: Throwable): String = when (error) {
        is TypeMismatchException, is DecodingException, is NumberFormatException -> CommonErrorCode.REQUEST_ERROR.simpleCode
        is MemberFrontException -> error.errorCode.simpleCode
        is TokenException -> error.errorCode.simpleCode
        else -> "99999"
    }

    private fun determineMessage(error: Throwable, internalApi: Boolean): String = when (error) {
        is ResponseStatusException -> error.cause?.message ?: "${error.reason}"
        is TypeMismatchException, is DecodingException, is NumberFormatException ->
            MessageConverter.getMessage(CommonErrorCode.REQUEST_ERROR.code)
        is MemberFrontException -> error.message
        is TokenException -> error.message
        else -> if (internalApi) "${error.message}" else "Server error"
    }
}
