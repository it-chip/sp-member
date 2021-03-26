package com.sp.application.member

import com.sp.domain.member.*
import com.sp.presentation.request.*
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.*
import kotlinx.coroutines.*
import org.jasypt.springsecurity4.crypto.password.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.*

/**
 * @author Jaedoo Lee
 */
@ExtendWith(MockKExtension::class)
internal class MemberCommandServiceTest {

    @MockK
    private lateinit var memberDomainService: MemberDomainService

    @MockK
    private lateinit var passwordEncoder: PasswordEncoder

    private lateinit var memberCommandService: MemberCommandService

    @BeforeEach
    fun setUp() {
        memberCommandService = MemberCommandService(memberDomainService, passwordEncoder)
    }

    @Test
    fun `회원 가입`() {
        // given
        val request = MemberRegisterRequest(
            email = "dlwoen9@naver.com",
            password = "qwert12345",
            nickname = "두두"
        )

        // when
        coEvery { passwordEncoder.encode(any()) } returns "encoded"
        coEvery { memberDomainService.register(any()) } returns 1L
        runBlocking {
            memberCommandService.registerMember(request)
        }

        // then
        coVerify {
            passwordEncoder.encode(any())
            memberDomainService.register(any())
        }
    }

}
