package com.sp.presentation.handler

import com.sp.application.member.*
import com.sp.domain.extensions.*
import com.sp.presentation.request.*
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.*
import kotlinx.coroutines.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.*
import org.springframework.http.*
import org.springframework.mock.http.server.reactive.*
import org.springframework.mock.web.server.*
import org.springframework.web.reactive.function.server.*

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
                .post("/backend/members")
                .contentType(MediaType.APPLICATION_JSON)
                .body(requestBody.toJson())
        val exchange =
            MockServerWebExchange
                .from(request)
                .let { ServerRequest.create(it, HandlerStrategies.withDefaults().messageReaders()) }

//        mockkObject(URIUtils)
//        every { URIUtils.createdResourceURI(request, any()) } returns returnUri
        coEvery { memberCommandService.registerMember(any()) } returns 1L
        //when
        val response = runBlocking { memberHandler.signUp(exchange) }
        //then
        assertEquals(HttpStatus.CREATED, response.statusCode())
//        assertEquals(returnUri.toString(), response.headers()["Location"]?.first())
        coVerify { memberCommandService.registerMember(any()) }
    }

}
