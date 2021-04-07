package com.sp.application.member

import com.sp.application.auth.*
import com.sp.domain.member.*
import com.sp.presentation.request.*
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.*
import kotlinx.coroutines.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.*
import org.springframework.transaction.support.*

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

    private lateinit var memberCommandService: MemberCommandService

    @BeforeEach
    fun setUp() {
        memberCommandService =
            MemberCommandService(memberDomainService, transactionTemplate, authQueryService)

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
        coEvery { memberDomainService.register(any()) } returns 1L
        runBlocking {
            memberCommandService.registerMember(request)
        }

        // then
        coVerify {
            memberDomainService.register(any())
        }
    }

}
