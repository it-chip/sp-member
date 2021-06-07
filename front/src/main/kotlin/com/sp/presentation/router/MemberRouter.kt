package com.sp.presentation.router

import com.sp.presentation.handler.MemberHandler
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
class MemberRouter(private val memberHandler: MemberHandler) {

    @Bean
    fun routeMember(): RouterFunction<ServerResponse> {
        return coRouter {
            ("/front/members" and headers { "1.0" in it.header("Version") }).nest {
                accept(MediaType.APPLICATION_JSON).nest {
                    POST("", memberHandler::signUp)
                    POST("login", memberHandler::createToken)
                    PUT("profile", memberHandler::modifyProfile)
                }
            }
        }
    }

}
