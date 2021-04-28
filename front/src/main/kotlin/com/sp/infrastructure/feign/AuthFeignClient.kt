package com.sp.infrastructure.feign

import com.sp.infrastructure.model.*
import org.springframework.cloud.openfeign.*
import org.springframework.web.bind.annotation.*

/**
 * @author Jaedoo Lee
 */
@FeignClient("auth-internal", configuration = [FeignConfig::class])
interface AuthFeignClient {

    @PostMapping(value = ["internal/auth/token"], headers = ["Version=1.0"])
    fun createToken(@RequestBody loginInfo: LoginInfoRequest): String
}
