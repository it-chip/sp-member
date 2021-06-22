package com.sp.infrastructure.token

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import com.auth0.jwt.exceptions.TokenExpiredException
import com.auth0.jwt.interfaces.DecodedJWT
import com.sp.application.model.LoginMemberInfo
import com.sp.domain.FrontTokenExpiredException
import com.sp.domain.InvalidFrontTokenException
import com.sp.domain.TokenService
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

/**
 * @author Jaedoo Lee
 */
class JwtTokenService : TokenService {
    private val accessTokenAlgorithm: Algorithm = Algorithm.HMAC256(SECRET)
    private val verifier: JWTVerifier = JWT.require(accessTokenAlgorithm).withIssuer(ISSUER).build()

    companion object {
        private const val SECRET = "ITCHIP"
        private const val ISSUER = "SP"
        private const val ACCESSTOKEN_VALID_MINUTES = 30L
    }

    override fun createAccessToken(tokenModel: LoginMemberInfo): String {
        val jwtCreator = JWT.create()
            .withClaim("no", tokenModel.no)
            .withClaim("email", tokenModel.email)
            .withClaim("nickname", tokenModel.nickname)


        tokenModel.email.let {
            jwtCreator.withClaim("email", it)
        }

        val now = ZonedDateTime.now(ZoneId.systemDefault())

        return jwtCreator.withIssuedAt(Date.from(now.toInstant()))
            .withExpiresAt(Date.from(now.plusMinutes(ACCESSTOKEN_VALID_MINUTES).toInstant()))
            .withIssuer(ISSUER)
            .sign(accessTokenAlgorithm)
    }

    override fun getPayload(accessToken: String): String {
        val jwt = decodeToken(accessToken)

        return jwt.payload
    }

    private fun decodeToken(token: String): DecodedJWT {
        return try {
            verifier.verify(token)
        } catch (e: TokenExpiredException) {
            throw FrontTokenExpiredException()
        } catch (e: JWTVerificationException) {
            throw InvalidFrontTokenException()
        }
    }
}
