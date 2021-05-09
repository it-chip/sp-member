package com.sp.domain.member

import com.sp.application.member.*
import com.sp.domain.*
import com.sp.domain.member.entity.*
import com.sp.domain.member.model.*
import org.springframework.data.repository.*

/**
 * @author Jaedoo Lee
 */
@DomainService
class MemberDomainService(
    private val memberRepository: MemberRepository
) {

    fun register(params: MemberRegisterModel): Long {
        return memberRepository.save(Member.create(params)).no
    }

    fun checkMemberInfo(email: String): Member? {
        return memberRepository.findByEmail(email)
    }

    fun update(params: MemberProfileParams) {
        with(params) {
            memberRepository.findByIdOrNull(no)?.also  {
                it.modify(nickname)
            } ?: throw RequestException("memberNo : ${no}")
        }
    }

}
