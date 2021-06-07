package com.sp.presentation

import org.springframework.web.reactive.function.client.ClientRequest
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.reactive.function.client.ExchangeFilterFunction
import org.springframework.web.reactive.function.client.ExchangeFunction
import org.springframework.web.util.UriComponentsBuilder
import reactor.core.publisher.Mono

/**
 * @author Jaedoo Lee
 */
class FrontApiTestSupportFilterFunction : ExchangeFilterFunction {

    override fun filter(request: ClientRequest, next: ExchangeFunction): Mono<ClientResponse> {
        val url = request.url()
        val backendUrl = UriComponentsBuilder.fromUri(url)
            .replacePath("/front${url.path}")
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
