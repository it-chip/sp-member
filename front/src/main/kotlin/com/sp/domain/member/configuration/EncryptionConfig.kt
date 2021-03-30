package com.sp.domain.member.configuration

import org.jasypt.springsecurity4.crypto.password.*
import org.jasypt.util.password.*
import org.springframework.context.annotation.*

/**
 * @author Jaedoo Lee
 */
@Configuration
class EncryptionConfig {

    @Bean
    fun passwordEncryptor(): PasswordEncoder {
        val passwordEncryptor = PasswordEncoder()
        passwordEncryptor.setPasswordEncryptor(StrongPasswordEncryptor())
        return passwordEncryptor
    }

}
