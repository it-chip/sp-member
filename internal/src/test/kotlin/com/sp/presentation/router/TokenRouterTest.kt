package com.sp.presentation.router

import com.epages.restdocs.apispec.ResourceDocumentation
import com.epages.restdocs.apispec.ResourceSnippetParameters
import com.ninjasquad.springmockk.MockkBean
import com.sp.application.TokenCommandService
import com.sp.presentation.handler.AuthHandler
import com.sp.presentation.request.LoginRequest
import io.mockk.coEvery
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.ApplicationContext
import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient

/**
 * @author Jaedoo Lee
 */
@WebFluxTest
@ExtendWith(RestDocumentationExtension::class)
@ContextConfiguration(classes = [TokenRouter::class, AuthHandler::class])
internal class TokenRouterTest(private val context: ApplicationContext) {

    private lateinit var webTestClient: WebTestClient

    private val TAG = "MEMBER"

    @MockkBean
    private lateinit var tokenCommandService: TokenCommandService

    @BeforeEach
    fun setup(restDocumentation: RestDocumentationContextProvider) {
        webTestClient = WebTestClient.bindToApplicationContext(context)
            .configureClient()
            .baseUrl("http://localhost:8081")
            .filter(WebTestClientRestDocumentation.documentationConfiguration(restDocumentation))
            .build()
    }

    @Test
    fun `토큰 발급`() {
        val request = LoginRequest(
            memberNo = 1L,
            email = "dlwoen9@naver.com",
            nickname = "두두"
        )

        val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJubyI6MSwibmlja25hbWUiOiJlbmVuIiwiaXNzIjoiU1AiLCJpYXQiOjE2MTc2MzYwNzcsImVtYWlsIjoiZGx3b2VuOUBuYXZlci5jb20ifQ.rrATAXpcrhAo6nG3KqZOu_IqFGr5NxUk_Hg9h3FJajk"

        coEvery { tokenCommandService.createToken(any()) } returns token

        webTestClient.post()
            .uri("/internal/auth/token")
            .header("Version", "1.1")
            .accept(MediaType.APPLICATION_JSON)
            .bodyValue(request)
            .exchange()
            .expectStatus().isOk
            .expectBody().consumeWith(
                WebTestClientRestDocumentation.document(
                    "create-token",
                    ResourceDocumentation.resource(
                        ResourceSnippetParameters.builder()
                            .tag(TAG)
                            .summary("회원 토큰 발급")
                            .description("회원 토큰 발급")
                            .requestHeaders(
                                ResourceDocumentation.headerWithName("Version")
                                    .description("버전")
                            )
                            .requestFields(
                                PayloadDocumentation.fieldWithPath("memberNo")
                                    .description("회원번호")
                                    .type(JsonFieldType.NUMBER),
                                PayloadDocumentation.fieldWithPath("email")
                                    .description("이메일 주소(아이디)")
                                    .type(JsonFieldType.STRING),
                                PayloadDocumentation.fieldWithPath("nickname")
                                    .description("닉네임")
                                    .type(JsonFieldType.STRING)
                            ).build()
                    )
                )
            )
    }
}
