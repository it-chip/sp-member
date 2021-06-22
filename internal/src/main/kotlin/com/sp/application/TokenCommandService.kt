package com.sp.application

import com.sp.domain.TokenService
import com.sp.presentation.request.LoginRequest
import org.springframework.stereotype.Service

/**
 * @author Jaedoo Lee
 */
@Service
class TokenCommandService(
    private val tokenServices: TokenService
) {
    fun createToken(request: LoginRequest): String {
        return tokenServices.createAccessToken(request.toMemberInfo())
    }

    fun getAuthentication(tokenValue: String): String {
        return tokenServices.getPayload(tokenValue)
    }
}
