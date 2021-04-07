package com.sp.presentation.request

import com.sp.domain.member.*

/**
 * @author Jaedoo Lee
 */
data class LoginRequest(
    val email: String,
    val password: String,
) {
    fun validate() {
        if (email.isBlank() || password.isBlank()) throw LoginException()
    }
}
