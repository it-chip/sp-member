package com.sp.domain.member

import com.sp.domain.member.error.*
import com.sp.domain.member.error.MemberErrorCode.*

/**
 * @author Jaedoo Lee
 */
abstract class AbstractMemberFrontExceptions(
    errorCode: MemberErrorCode,
    vararg args: Any
) : MemberFrontException(errorCode, args)

class DuplicatedEmailException : AbstractMemberFrontExceptions(DUPLICATED_EMAIL)
