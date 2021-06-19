package com.sp.presentation.router

import com.epages.restdocs.apispec.ResourceDocumentation
import com.epages.restdocs.apispec.ResourceSnippetParameters
import com.ninjasquad.springmockk.MockkBean
import com.sp.application.member.MemberCommandService
import com.sp.enums.JoinRoute
import com.sp.enums.MemberType
import com.sp.presentation.FrontApiTestSupportFilterFunction
import com.sp.presentation.MemberInfoConstant
import com.sp.presentation.MemberInfoFilter
import com.sp.presentation.handler.MemberHandler
import com.sp.presentation.request.LoginRequest
import com.sp.presentation.request.MemberProfileRequest
import com.sp.presentation.request.MemberRegisterRequest
import com.sp.presentation.response.AccessTokenResponse
import com.sp.presentation.toMarkdownList
import io.mockk.coEvery
import io.mockk.just
import io.mockk.runs
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.context.ApplicationContext
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.payload.JsonFieldType
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient

/**
 * @author Jaedoo Lee
 */
@WebFluxTest
@ExtendWith(RestDocumentationExtension::class)
@ContextConfiguration(classes = [MemberRouter::class, MemberHandler::class, MemberInfoFilter::class])
internal class MemberRouterTest(private val context: ApplicationContext) {

    private lateinit var webTestClient: WebTestClient

    private val TAG = "MEMBER"

    @MockkBean
    private lateinit var memberCommandService: MemberCommandService

    @BeforeEach
    fun setup(restDocumentation: RestDocumentationContextProvider) {
        webTestClient = WebTestClient.bindToApplicationContext(context)
            .configureClient()
            .baseUrl("http://localhost:8080")
            .filter(WebTestClientRestDocumentation.documentationConfiguration(restDocumentation))
            .filter(FrontApiTestSupportFilterFunction())
            .build()
    }

    @Test
    fun `회원 가입`() {
        val memberNo = 1L
        val request = MemberRegisterRequest(
            email = "dlwoen9@naver.com",
            password = "qwert12345",
            nickname = "두두",
            memberType = MemberType.NORMAL,
            joinRoute = JoinRoute.PC
        )

        coEvery { memberCommandService.registerMember(any()) } returns 1L

        webTestClient.post()
            .uri("/members")
            .header("Version", "1.0")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .bodyValue(request)
            .exchange()
            .expectStatus().isCreated
            .expectHeader().valueEquals(HttpHeaders.LOCATION, memberNo.toString())
            .expectBody().consumeWith(
                WebTestClientRestDocumentation.document(
                    "member-sign-up",
                    ResourceDocumentation.resource(
                        ResourceSnippetParameters.builder()
                            .tag(TAG)
                            .summary("회원 가입")
                            .description(
                                """
                                |## 회원 가입
                                |
                                |### memberType : 회원유형
                                | ${MemberType.values().toMarkdownList()}
                                | 
                                |### joinRoute : 가입유형
                                | ${JoinRoute.values().toMarkdownList()}
                                |""".trimMargin()
                            )
                            .requestHeaders(
                                ResourceDocumentation.headerWithName("Version")
                                    .description("버전")
                            )
                            .requestFields(
                                fieldWithPath("email")
                                    .description("이메일 주소(아이디)")
                                    .type(JsonFieldType.STRING),
                                fieldWithPath("password")
                                    .description("비밀번호")
                                    .type(JsonFieldType.STRING),
                                fieldWithPath("nickname")
                                    .description("닉네임")
                                    .type(JsonFieldType.STRING),
                                fieldWithPath("memberType")
                                    .description("회원유형")
                                    .type(JsonFieldType.STRING),
                                fieldWithPath("joinRoute")
                                    .description("가입경로")
                                    .type(JsonFieldType.STRING)
                            ).build()
                    )
                )
            )
    }

    @Test
    fun `토큰 발급`() {
        val request = LoginRequest(
            email = "dlwoen9@naver.com",
            password = "qwert12345"
        )

        val response = AccessTokenResponse(
            token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJubyI6MSwibmlja25hbWUiOiJlbmVuIiwiaXNzIjoiU1AiLCJpYXQiOjE2MTc2MzYwNzcsImVtYWlsIjoiZGx3b2VuOUBuYXZlci5jb20ifQ.rrATAXpcrhAo6nG3KqZOu_IqFGr5NxUk_Hg9h3FJajk"
        )

        coEvery { memberCommandService.createToken(request) } returns response.token

        webTestClient.post()
            .uri("/members/login")
            .header("Version", "1.0")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .bodyValue(request)
            .exchange()
            .expectStatus().isOk
            .expectBody().consumeWith(
                WebTestClientRestDocumentation.document(
                    "member-login",
                    ResourceDocumentation.resource(
                        ResourceSnippetParameters.builder()
                            .tag(TAG)
                            .summary("회원 로그인")
                            .description("회원 로그인")
                            .requestHeaders(
                                ResourceDocumentation.headerWithName("Version")
                                    .description("버전")
                            )
                            .requestFields(
                                fieldWithPath("email")
                                    .description("이메일 주소(아이디)")
                                    .type(JsonFieldType.STRING),
                                fieldWithPath("password")
                                    .description("비밀번호")
                                    .type(JsonFieldType.STRING)
                            )
                            .responseFields(
                                fieldWithPath("token").description("accessToken")
                                    .type(JsonFieldType.STRING)
                            )
                            .build()
                    )
                )
            )
    }

    @Test
    fun `회원 정보 수정`() {
        val request = MemberProfileRequest(
            email = null,
            nickname = "두두루두두",
            oldPassword = null,
            newPassword = null
        )

        coEvery { memberCommandService.update(any()) } just runs

        webTestClient.put()
            .uri("/members/profile")
            .header("Version", "1.0")
            .header(MemberInfoConstant.ACCESS_TOKEN_HEADER, MemberInfoConstant.TEST_ACCESS_TOKEN)
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .accept(MediaType.APPLICATION_JSON)
            .bodyValue(request)
            .exchange()
            .expectStatus().isNoContent
            .expectBody().consumeWith(
                WebTestClientRestDocumentation.document(
                    "member-profile-update",
                    ResourceDocumentation.resource(
                        ResourceSnippetParameters.builder()
                            .tag(TAG)
                            .summary("회원 정보 수정")
                            .description(
                                """
                                |## 회원 정보 수정
                                |변경할 값만 request로 전송(""은 400 응답)
                                |""".trimMargin()
                            )
                            .requestHeaders(
                                ResourceDocumentation.headerWithName("Version")
                                    .description("버전"),
                                ResourceDocumentation.headerWithName(MemberInfoConstant.ACCESS_TOKEN_HEADER)
                                    .description("AccessToken")
                            )
                            .requestFields(
                                fieldWithPath("email")
                                    .description("회원 이메일(아이디)")
                                    .type(JsonFieldType.STRING)
                                    .optional(),
                                fieldWithPath("nickname")
                                    .description("회원 닉네임")
                                    .type(JsonFieldType.STRING)
                                    .optional(),
                                fieldWithPath("oldPassword")
                                    .description("기존 비밀번호")
                                    .type(JsonFieldType.STRING)
                                    .optional(),
                                fieldWithPath("newPassword")
                                    .description("변경할 비밀번호")
                                    .type(JsonFieldType.STRING)
                                    .optional(),
                            ).build()
                    )
                )
            )
    }

}
