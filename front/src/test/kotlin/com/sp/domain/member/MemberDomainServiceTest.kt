package com.sp.domain.member

import com.sp.domain.member.entity.Member
import com.sp.domain.member.model.MemberUpdateModel
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.data.repository.findByIdOrNull
import java.time.LocalDateTime

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
    @DisplayName("oldPassword가 null일 때")
    fun `회원 정보 수정1`() {
        val oldPassword = null
        val params = MemberUpdateModel(
            no = 1L,
            email = null,
            nickname = "닉네임",
            newPassword = null
        )

        val member = mockk<Member> {
            every { no } returns 1L
            every { email } returns "dlwoen9@naver.com"
            every { password } returns "qwert12345"
            every { nickname } returns "두두두두"
            every { joinDateTime } returns LocalDateTime.now()
        }

        every { memberRepository.findByIdOrNull(any()) } returns member
        every { member.modifyProfile(any()) } just runs

        memberDomainService.update(params, oldPassword)

        verify {
            memberRepository.findByIdOrNull(any())
            member.modifyProfile(any())
        }
    }

    @Test
    @DisplayName("oldPassword가 있고 일치할 때")
    fun `회원 정보 수정2`() {
        val oldPassword = "qwert12345"
        val params = MemberUpdateModel(
            no = 1L,
            email = null,
            nickname = "닉네임",
            newPassword = null
        )

        val member = mockk<Member> {
            every { no } returns 1L
            every { email } returns "dlwoen9@naver.com"
            every { password } returns oldPassword
            every { nickname } returns "두두두두"
            every { joinDateTime } returns LocalDateTime.now()
        }

        every { memberRepository.findByIdOrNull(any()) } returns member
        every { member.matchesPassword(any()) } returns true
        every { member.modifyProfile(any()) } just runs

        memberDomainService.update(params, oldPassword)

        verify {
            memberRepository.findByIdOrNull(any())
            member.matchesPassword(any())
            member.modifyProfile(any())
        }
    }

    @Test
    @DisplayName("oldPassword가 있는데 일치하지 않을 때")
    fun `회원 정보 수정3`() {
        val oldPassword = "qwert12345"
        val params = MemberUpdateModel(
            no = 1L,
            email = null,
            nickname = "닉네임",
            newPassword = null
        )

        val member = mockk<Member> {
            every { no } returns 1L
            every { email } returns "dlwoen9@naver.com"
            every { password } returns "hello1234"
            every { nickname } returns "두두두두"
            every { joinDateTime } returns LocalDateTime.now()
        }

        every { memberRepository.findByIdOrNull(any()) } returns member
        every { member.matchesPassword(any()) } returns false

        assertThrows<InvalidPasswordException> { memberDomainService.update(params, oldPassword) }
    }
}
