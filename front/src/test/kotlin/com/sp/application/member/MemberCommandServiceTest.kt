package com.sp.application.member

import com.sp.application.auth.*
import com.sp.domain.member.*
import com.sp.domain.member.entity.*
import com.sp.presentation.request.*
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.*
import kotlinx.coroutines.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.*
import org.springframework.transaction.support.*
import java.time.*

/**
 * @author Jaedoo Lee
 */
@ExtendWith(MockKExtension::class)
internal class MemberCommandServiceTest {

    @MockK
    private lateinit var memberDomainService: MemberDomainService

    @MockK
    private lateinit var transactionTemplate: TransactionTemplate

    @MockK
    private lateinit var authQueryService: AuthQueryService

    @MockK
    private lateinit var memberRepository: MemberRepository

    private lateinit var memberCommandService: MemberCommandService

    @BeforeEach
    fun setUp() {
        memberCommandService =
            MemberCommandService(memberDomainService, transactionTemplate, authQueryService, memberRepository)

        every { transactionTemplate.execute(any<TransactionCallback<*>>()) } answers {
            (firstArg() as TransactionCallback<*>).doInTransaction(mockk())
        }
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
        coEvery { memberRepository.findByEmail(any()) } returns null
        coEvery { memberDomainService.register(any()) } returns 1L
        runBlocking {
            memberCommandService.registerMember(request)
        }

        // then
        coVerify {
            memberDomainService.register(any())
        }
    }

    @Test
    fun `중복 이메일로 회원 가입 시도 테스트`() {
        // given
        val request = MemberRegisterRequest(
            email = "dlwoen9@naver.com",
            password = "qwert12345",
            nickname = "두두"
        )

        val member = Member(
            no = 1L,
            email = request.email,
            password = request.password,
            nickname = request.nickname,
            joinDateTime = LocalDateTime.now()
        )

        // when
        coEvery { memberRepository.findByEmail(any()) } returns member

        // then
        assertThrows<DuplicatedEmailException> {
            runBlocking {
                memberCommandService.registerMember(request)
            }
        }
    }

}
