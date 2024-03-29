package com.sp.domain.member

import com.sp.domain.member.entity.Member
import com.sp.enums.JoinRoute
import com.sp.enums.MemberType
import org.flywaydb.test.annotation.FlywayTest
import org.flywaydb.test.junit5.annotation.FlywayTestExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.stereotype.Repository
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestConstructor

/**
 * @author Jaedoo Lee
 */
@DataJpaTest
@FlywayTestExtension
@FlywayTest(invokeCleanDB = false)
@ActiveProfiles("test")
@TestConstructor(autowireMode = TestConstructor.AutowireMode.ALL)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ComponentScan(useDefaultFilters = false, includeFilters = [ComponentScan.Filter(Repository::class)])
class MemberRepositoryTest(
    val memberRepository: MemberRepository
) {

    @Test
    fun `생성 테스트`() {
        //given
        val request = Member(
            email = "test@naver.com",
            password = "qwert12345",
            nickname = "두두",
            memberType = MemberType.NORMAL,
            joinRoute = JoinRoute.PC
        )

        //when
        val saved = memberRepository.save(request)

        //then
        val result = memberRepository.findById(saved.no).get()
        assertEquals(saved.no, result.no)
        assertEquals(request.email, result.email)
        assertEquals(request.password, result.password)
        assertEquals(request.nickname, result.nickname)
    }

    @Test
    fun `아이디(email) 조회 테스트`() {
        //given
        val expected = Member(
            email = "test@naver.com",
            password = "qwert12345",
            nickname = "두두",
            memberType = MemberType.NORMAL,
            joinRoute = JoinRoute.PC
        )

        //when
        val saved = memberRepository.save(expected)

        //then
        val result = memberRepository.findByEmail(expected.email)
        assertEquals(saved.no, result!!.no)
    }

    @Test
    fun `아이디(email) 조회 실패 테스트`() {
        //given
        val expected = Member(
            email = "test@naver.com",
            password = "qwert12345",
            nickname = "두두",
            memberType = MemberType.NORMAL,
            joinRoute = JoinRoute.PC
        )

        //when
        memberRepository.save(expected)

        //then
        assertNull(memberRepository.findByEmail("아이디가될수없는요상한값"))
    }

}
