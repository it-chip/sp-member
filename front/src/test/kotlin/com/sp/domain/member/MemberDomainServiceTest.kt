package com.sp.domain.member

import com.sp.application.member.*
import com.sp.domain.member.entity.*
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.extension.*
import org.springframework.data.repository.*
import java.time.*

/**
 * @author Jaedoo Lee
 */
@ExtendWith(MockKExtension::class)
internal class MemberDomainServiceTest {

    @MockK
    private lateinit var memberRepository: MemberRepository

    private lateinit var memberDomainService: MemberDomainService

    @BeforeEach
    internal fun setUp() {
        memberDomainService = MemberDomainService(memberRepository)
    }

    @Test
    fun `회원 정보 수정`() {
        val params = MemberProfileParams(
            no = 1L,
            nickname = "닉네임"
        )

        val member = mockk<Member> {
            every { no } returns 1L
            every { email } returns "dlwoen9@naver.com"
            every { password } returns "qwert12345"
            every { nickname } returns "두두두두"
            every { joinDateTime } returns LocalDateTime.now()
        }

        every { memberRepository.findByIdOrNull(params.no) } returns member
        every { member.modify(params.nickname) } just runs

        memberDomainService.update(params)

        verify {
            memberRepository.findByIdOrNull(params.no)
            member.modify(params.nickname)
        }
    }

}
