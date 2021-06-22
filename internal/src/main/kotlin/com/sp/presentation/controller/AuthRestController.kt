package com.sp.presentation.controller

import com.sp.presentation.handler.AuthHandler
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.ok
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/**
 * @author Jaedoo Lee
 */
@RestController
@RequestMapping(path = ["/internal/members"])
class AuthRestController(
    private val authHandler: AuthHandler
) {
    @GetMapping(
        path = ["token/check"],
        headers = ["Version=1.0"],
        consumes = [MediaType.APPLICATION_JSON_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    suspend fun checkToken(@RequestHeader accessToken: String?): ResponseEntity<String> {
        //TODO Exception class 생성
        if (accessToken.isNullOrBlank()) throw Exception()
        return ok(authHandler.getAuthentication(accessToken))
    }
}
