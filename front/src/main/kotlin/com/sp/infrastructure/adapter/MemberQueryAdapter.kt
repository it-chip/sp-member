package com.sp.infrastructure.adapter

import com.sp.application.auth.MemberQueryService
import com.sp.infrastructure.feign.MemberFeignClient
import com.sp.infrastructure.model.LoginInfoRequest
import org.springframework.stereotype.Component

/**
 * @author Jaedoo Lee
 */
@Component
class MemberQueryAdapter(private val memberFeignClient: MemberFeignClient): MemberQueryService {
    override fun createToken(request: LoginInfoRequest): String {
        return memberFeignClient.createToken(request)
    }
}
