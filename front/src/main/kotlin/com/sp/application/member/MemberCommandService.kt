package com.sp.application.member

import com.sp.domain.member.*
import com.sp.presentation.request.*
import org.jasypt.springsecurity4.crypto.password.*
import org.springframework.stereotype.*
import org.springframework.transaction.support.*

/**
 * @author Jaedoo Lee
 */

@Service
class MemberCommandService(
    private val memberDomainService: MemberDomainService,
    private val passwordEncryptor: PasswordEncoder,
    private val transactionTemplate: TransactionTemplate,
) {

    suspend fun registerMember(params: MemberRegisterRequest): Long {
        return transactionTemplate.execute {
            memberDomainService.register(params.copy(password = passwordEncryptor.encode(params.password)))
        }!!
    }

}
