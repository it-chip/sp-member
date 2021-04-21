package com.sp.application.auth

import com.sp.infrastructure.model.*

/**
 * @author Jaedoo Lee
 */
interface AuthQueryService {

    fun createToken(request: LoginInfoRequest): String

}
