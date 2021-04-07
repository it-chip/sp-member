package com.sp.infrastructure.adapter

import com.sp.application.auth.*
import com.sp.infrastructure.feign.*
import com.sp.infrastructure.model.*
import org.springframework.stereotype.*

/**
 * @author Jaedoo Lee
 */
@Component
class AuthQueryAdapter(private val authFeignClient: AuthFeignClient): AuthQueryService {
    override fun createToken(request: LoginInfoRequest): String {
        return authFeignClient.createToken(request)
    }
}
