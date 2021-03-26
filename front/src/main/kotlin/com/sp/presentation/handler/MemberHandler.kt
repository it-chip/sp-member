package com.sp.presentation.handler

import com.sp.application.member.*
import com.sp.presentation.request.*
import org.springframework.stereotype.*
import org.springframework.web.reactive.function.server.*
import org.springframework.web.reactive.function.server.ServerResponse.*
import java.net.*

/**
 * @author Jaedoo Lee
 */
@Component
class MemberHandler(
    private val memberCommandService: MemberCommandService
) {
    suspend fun signUp(request: ServerRequest): ServerResponse {
        val params = request.awaitBody<MemberRegisterRequest>()

        val memberNo = memberCommandService.registerMember(params)

        return created(URI.create(memberNo.toString())).buildAndAwait()
    }
}
