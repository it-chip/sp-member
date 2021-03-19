package com.sp.presentation.router

import com.sp.presentation.handler.*
import org.springframework.context.annotation.*
import org.springframework.http.*
import org.springframework.web.reactive.function.server.*

/**
 * @author Jaedoo Lee
 */
@Configuration
class FooRouter(private val fooHandler: FooHandler) {

    @Bean
    fun routerScatter(): RouterFunction<ServerResponse> {
        return coRouter {
            (accept(MediaType.APPLICATION_JSON) and "/backend/members").nest {
                GET("", fooHandler::search)
            }
        }
    }
}
