package com.sp.presentation

import com.sp.domain.member.*
import com.sp.presentation.MemberInfoConstant.ATTRIBUTE_NAME
import org.springframework.web.reactive.function.server.*

/**
 * @author Jaedoo Lee
 */
fun ServerRequest.extractMemberInfo() : MemberInfo {
    return attributes()[ATTRIBUTE_NAME] as MemberInfo?
        ?: throw MemberTokenInvalidException()
}
