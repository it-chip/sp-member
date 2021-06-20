package com.sp.presentation.handler

import com.sp.application.TokenCommandService
import com.sp.presentation.request.LoginRequest
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.ServerResponse.ok
import org.springframework.web.reactive.function.server.awaitBodyOrNull
import org.springframework.web.reactive.function.server.bodyValueAndAwait

/**
 * @author Jaedoo Lee
 */
@Component
class AuthHandler(
    private val tokenCommandService: TokenCommandService
) {


    suspend fun getAuthentication(accessToken: String): String {
        return tokenCommandService.getAuthentication(accessToken)
    }

    suspend fun createToken(request: ServerRequest): ServerResponse {
        //TODO Exception class 생성
        val params = request.awaitBodyOrNull<LoginRequest>() ?: throw Exception()

        return ok().bodyValueAndAwait(tokenCommandService.createToken(params))
    }

}
