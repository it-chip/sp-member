package com.sp.infrastructure.feign

import com.sp.infrastructure.model.LoginInfoRequest
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

/**
 * @author Jaedoo Lee
 */
@FeignClient("member-internal", configuration = [FeignConfig::class])
interface MemberFeignClient {

    @PostMapping(value = ["internal/members/token"], headers = ["Version=1.0"])
    fun createToken(@RequestBody loginInfo: LoginInfoRequest): String
}
