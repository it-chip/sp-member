package com.sp.domain.member.util

import org.jasypt.springsecurity4.crypto.password.*
import org.jasypt.util.password.*

/**
 * @author Jaedoo Lee
 */
object MemberPasswordEncryptor {

    private val passwordEncoder = PasswordEncoder().apply {
        setPasswordEncryptor(StrongPasswordEncryptor())
    }

    fun matches(encodedPassword: String?, rawPassword: String): Boolean {
        return passwordEncoder.matches(rawPassword, encodedPassword)
    }

    fun encode(rawPassword: String): String {
        return passwordEncoder.encode(rawPassword)
    }

}
