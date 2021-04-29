package com.sp.domain.member

import com.sp.domain.member.entity.*
import org.flywaydb.test.annotation.*
import org.flywaydb.test.junit5.annotation.*
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.*
import org.springframework.boot.test.autoconfigure.jdbc.*
import org.springframework.boot.test.autoconfigure.orm.jpa.*
import org.springframework.context.annotation.*
import org.springframework.stereotype.*
import org.springframework.test.context.*

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
            nickname = "두두"
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
            nickname = "두두"
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
            nickname = "두두"
        )

        //when
        memberRepository.save(expected)

        //then
        assertNull(memberRepository.findByEmail("아이디가될수없는요상한값"))
    }

}
