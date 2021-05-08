package com.sp.application.member

import com.sp.application.auth.*
import com.sp.domain.*
import com.sp.domain.member.*
import com.sp.infrastructure.model.*
import com.sp.presentation.request.*
import org.springframework.stereotype.*
import org.springframework.transaction.support.*

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
        }?: throw RequestException("email : ${params.email}")

        return member
            .also { it.matchesPassword(params.password) }
            .let { authQueryService.createToken(LoginInfoRequest(member)) }
    }

    private fun validateDuplicatedEmail(email: String) {
        if (memberRepository.findByEmail(email) != null) throw DuplicatedEmailException();
    }

    suspend fun update(params: MemberProfileParams) {
        transactionTemplate.execute {
            memberDomainService.update(params)
        }
    }

}
