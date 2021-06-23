package com.sp.application.member

import com.sp.application.auth.AuthQueryService
import com.sp.domain.member.DuplicatedEmailException
import com.sp.domain.member.MemberDomainService
import com.sp.domain.member.MemberRepository
import com.sp.domain.member.RequestException
import com.sp.infrastructure.model.LoginInfoRequest
import com.sp.presentation.request.LoginRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.support.TransactionTemplate

/**
 * @author Jaedoo Lee
 */

@Service
class MemberCommandService(
    private val memberDomainService: MemberDomainService,
    private val transactionTemplate: TransactionTemplate,
    private val authQueryService: AuthQueryService,
    private val memberRepository: MemberRepository
) {

    suspend fun registerMember(params: MemberRegisterParams): Long {
        validateDuplicatedEmail(params.email)
        return transactionTemplate.execute {
            memberDomainService.register(params.toModel())
        }!!
    }

    suspend fun createToken(params: LoginRequest): String {
        val member = transactionTemplate.execute {
            memberDomainService.checkMemberInfo(params.email)
        } ?: throw RequestException("email : ${params.email}")

        return member
            .also { it.matchesPassword(params.password) }
            .let { authQueryService.createToken(LoginInfoRequest(member)) }
    }

    private fun validateDuplicatedEmail(email: String) {
        if (memberRepository.findByEmail(email) != null) throw DuplicatedEmailException();
    }

    suspend fun update(params: MemberProfileParams) {
        transactionTemplate.execute {
            memberDomainService.update(params.toModel(), params.oldPassword)
        }
    }

}
