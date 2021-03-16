package com.sp.presentation.handler

import org.springframework.stereotype.*
import org.springframework.web.reactive.function.server.*

/**
 * @author Jaedoo Lee
 */
@Component
class FooHandler {
    suspend fun search(request: ServerRequest): ServerResponse {

        return ServerResponse.ok().bodyValueAndAwait("Hi~ sp-member~ Bye~")
    }
}
