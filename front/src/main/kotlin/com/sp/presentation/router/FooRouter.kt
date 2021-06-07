package com.sp.presentation.router

import com.sp.presentation.handler.FooHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.RouterFunction
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.coRouter

/**
 * @author Jaedoo Lee
 */
@Configuration
class FooRouter(private val fooHandler: FooHandler) {

    @Bean
    fun routerScatter(): RouterFunction<ServerResponse> {
        return coRouter {
            (accept(MediaType.APPLICATION_JSON) and "/front/members").nest {
                GET("", fooHandler::search)
            }
        }
    }
}
