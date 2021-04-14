package com.sp.presentation

import org.springframework.web.reactive.function.client.*
import org.springframework.web.util.*
import reactor.core.publisher.*

/**
 * @author Jaedoo Lee
 */
class FrontApiTestSupportFilterFunction : ExchangeFilterFunction {

    override fun filter(request: ClientRequest, next: ExchangeFunction): Mono<ClientResponse> {
        val url = request.url()
        val backendUrl = UriComponentsBuilder.fromUri(url)
            .replacePath("/backend${url.path}")
            .replaceQuery(url.query)
            .build()
            .toUri()
        return next.exchange(
            ClientRequest.from(request)
            .url(backendUrl)
            .build()
        )
    }
}
