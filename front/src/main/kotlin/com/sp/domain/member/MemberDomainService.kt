package com.sp.domain.member

import com.sp.domain.*
import com.sp.domain.member.entity.*
import com.sp.presentation.request.*

/**
 * @author Jaedoo Lee
 */
@DomainService
class MemberDomainService(
    private val memberRepository: MemberRepository
) {

    fun register(params: MemberRegisterRequest): Long {
        return memberRepository.save(Member.create(params.valueOf())).no
    }

    fun checkMemberInfo(email: String): Member? {
        return memberRepository.findByEmail(email)
    }

}
