package com.sp.domain.member

import com.sp.domain.DomainService
import com.sp.domain.member.entity.Member
import com.sp.domain.member.model.MemberRegisterModel
import com.sp.domain.member.model.MemberUpdateModel
import org.springframework.data.repository.findByIdOrNull

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

    fun update(params: MemberUpdateModel, oldPassword: String?) {
        with(params) {
            memberRepository.findByIdOrNull(params.no)?.also  {
                validatePassword(it, oldPassword)
                it.modify(params)
            } ?: throw RequestException("memberNo : ${no}")
        }
    }

    private fun validatePassword(member: Member, oldPassword: String?) {
        if (oldPassword != null && !member.matchesPassword(oldPassword)) throw InvalidPasswordException()
    }
}
