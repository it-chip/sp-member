package com.sp.presentation.handler

import com.sp.application.member.MemberCommandService
import com.sp.domain.extensions.toJson
import com.sp.presentation.request.MemberRegisterRequest
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.unmockkAll
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.mock.http.server.reactive.MockServerHttpRequest
import org.springframework.mock.web.server.MockServerWebExchange
import org.springframework.web.reactive.function.server.HandlerStrategies
import org.springframework.web.reactive.function.server.ServerRequest

/**
 * @author Jaedoo Lee
 */
@ExtendWith(MockKExtension::class)
internal class MemberHandlerTest {

    @MockK
    private lateinit var memberCommandService: MemberCommandService

    private lateinit var memberHandler: MemberHandler

    @BeforeEach
    internal fun setUp() {
        memberHandler = MemberHandler(memberCommandService)
    }

    @AfterEach
    internal fun after() {
        unmockkAll()
    }

    @Test
    fun `회원 가입`() {
        val requestBody = MemberRegisterRequest(
            email = "dlwoen9@naver.com",
            password = "qwert12345",
            nickname = "두두"
        )

        val request =
            MockServerHttpRequest
                .post("/front/members")
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestBody.toJson())
        val exchange =
            MockServerWebExchange
                .from(request)
                .let { ServerRequest.create(it, HandlerStrategies.withDefaults().messageReaders()) }

        coEvery { memberCommandService.registerMember(any()) } returns 1L

        //when
        val response = runBlocking { memberHandler.signUp(exchange) }
        //then
        assertEquals(HttpStatus.CREATED, response.statusCode())
        coVerify { memberCommandService.registerMember(any()) }
    }

}
