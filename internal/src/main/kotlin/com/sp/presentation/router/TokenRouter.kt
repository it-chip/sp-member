package com.sp.presentation.router

import com.sp.presentation.handler.AuthHandler
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
class TokenRouter(
    private val authHandler: AuthHandler
) {
    @Bean
    fun routeToken(): RouterFunction<ServerResponse> {
        return coRouter {
            ("/internal/auth" and headers { "1.1" in it.header("Version") }).nest {
                accept(MediaType.APPLICATION_JSON).nest {
                    POST("token", authHandler::createToken)
                }
            }
        }
    }
}
