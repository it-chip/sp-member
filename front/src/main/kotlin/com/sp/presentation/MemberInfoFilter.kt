package com.sp.presentation

import com.sp.domain.extensions.*
import org.apache.commons.codec.binary.Base64
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
        with(exchange) {
            request.headers[MemberInfoConstant.ATTRIBUTE_NAME]
                ?.firstOrNull()
                ?.takeIf { it.isNotBlank() }
                ?.let { attributes[MemberInfoConstant.ATTRIBUTE_NAME] = String(Base64.decodeBase64(it)).toModel<MemberInfo>() }
        }

        return chain.filter(exchange)
    }
}
