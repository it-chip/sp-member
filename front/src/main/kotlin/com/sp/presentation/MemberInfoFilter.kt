package com.sp.presentation

import com.sp.domain.extensions.*
import com.sp.presentation.MemberInfoConstant.ACCESS_TOKEN_HEADER
import com.sp.presentation.MemberInfoConstant.ATTRIBUTE_NAME
import org.apache.commons.codec.binary.*
import org.springframework.core.env.*
import org.springframework.stereotype.*
import org.springframework.web.server.*
import reactor.core.publisher.*

/**
 * @author Jaedoo Lee
 */
@Component
class MemberInfoFilter(private val environment: Environment) : WebFilter {

    override fun filter(exchange: ServerWebExchange, chain: WebFilterChain): Mono<Void> {

        when {
            "local" in environment.activeProfiles || "alpha" in environment.activeProfiles ->
                injectTestMemberInfo(exchange)
        }

        with(exchange) {
            request.headers[ATTRIBUTE_NAME]
                ?.firstOrNull()
                ?.takeIf { it.isNotBlank() }
                ?.let { attributes[ATTRIBUTE_NAME] = String(Base64.decodeBase64(it)).toModel<MemberInfo>() }
        }

        return chain.filter(exchange)
    }

    private fun injectTestMemberInfo(exchange: ServerWebExchange) {
        with(exchange) {
            when (request.headers[ACCESS_TOKEN_HEADER]?.firstOrNull()) {
                MemberInfoConstant.TEST_ACCESS_TOKEN -> attributes[ATTRIBUTE_NAME] =
                    MemberInfoConstant.testMemberInfo
            }
        }
    }
}
