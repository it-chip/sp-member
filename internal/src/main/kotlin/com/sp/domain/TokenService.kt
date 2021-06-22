package com.sp.domain

import com.sp.application.model.LoginMemberInfo

/**
 * @author Jaedoo Lee
 */
interface TokenService {

    fun createAccessToken(tokenModel: LoginMemberInfo): String
    fun getPayload(accessToken: String): String
}
