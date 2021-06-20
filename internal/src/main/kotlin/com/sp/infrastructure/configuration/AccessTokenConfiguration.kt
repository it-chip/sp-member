package com.sp.infrastructure.configuration

import com.sp.domain.TokenService
import com.sp.infrastructure.token.JwtTokenService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * @author Jaedoo Lee
 */
@Configuration
class AccessTokenConfiguration {

    @Bean
    fun adminTokenService(): TokenService {
        return JwtTokenService()
    }

}
