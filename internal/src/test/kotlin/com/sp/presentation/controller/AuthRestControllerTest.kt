package com.sp.presentation.controller

import com.epages.restdocs.apispec.ResourceDocumentation
import com.epages.restdocs.apispec.ResourceSnippetParameters
import com.ninjasquad.springmockk.MockkBean
import com.sp.presentation.handler.AuthHandler
import io.mockk.coEvery
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.ApplicationContext
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient

/**
 * @author Jaedoo Lee
 */
@WebFluxTest
@ExtendWith(RestDocumentationExtension::class)
@ContextConfiguration(classes = [AuthRestController::class])
internal class AuthRestControllerTest(private val context: ApplicationContext) {

    private lateinit var webTestClient: WebTestClient

    private val TAG = "MEMBER"

    @MockkBean
    private lateinit var authHandler: AuthHandler

    @BeforeEach
    fun setup(restDocumentation: RestDocumentationContextProvider) {
        webTestClient = WebTestClient.bindToApplicationContext(context)
            .configureClient()
            .baseUrl("http://localhost:8081")
            .filter(WebTestClientRestDocumentation.documentationConfiguration(restDocumentation))
            .build()
    }

    @Test
    fun `토큰 검증`() {
        val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJubyI6MSwibmlja25hbWUiOiJlbmVuIiwiaXNzIjoiU1AiLCJpYXQiOjE2MTc2MzYwNzcsImVtYWlsIjoiZGx3b2VuOUBuYXZlci5jb20ifQ.rrATAXpcrhAo6nG3KqZOu_IqFGr5NxUk_Hg9h3FJajk"
        val decodedToken = "{no\": 1,\"email\": \"dlwoen90@naver.com\",\"nickname\": \"두두\"}"

        coEvery { authHandler.getAuthentication(any()) } returns decodedToken

        webTestClient.get()
            .uri("/internal/members/token/check")
            .header("Version", "1.0")
            .header("accessToken", token)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isOk
            .expectBody().consumeWith(
                WebTestClientRestDocumentation.document(
                    "authentication-token",
                    ResourceDocumentation.resource(
                        ResourceSnippetParameters.builder()
                            .tag(TAG)
                            .summary("회원 토큰 검증")
                            .description("회원 토큰 검증")
                            .requestHeaders(
                                ResourceDocumentation.headerWithName("Version")
                                    .description("버전"),
                                ResourceDocumentation.headerWithName("accessToken")
                                    .description(token)
                            ).build()
                    )
                )
            )
    }
}

