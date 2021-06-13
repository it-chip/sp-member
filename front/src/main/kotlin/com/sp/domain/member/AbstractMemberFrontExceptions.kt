package com.sp.domain.member

import com.sp.domain.CommonErrorCode
import com.sp.domain.member.error.MemberErrorCode
import com.sp.domain.member.error.MemberErrorCode.DUPLICATED_EMAIL
import com.sp.domain.member.error.MemberErrorCode.INVALID_PASSWORD

/**
 * @author Jaedoo Lee
 */
abstract class AbstractMemberFrontExceptions(
    errorCode: MemberErrorCode,
    vararg args: Any
) : MemberFrontException(errorCode, args)

class RequestException(param: String) : MemberFrontException(CommonErrorCode.REQUEST_ERROR, arrayOf(param))
class DuplicatedEmailException : AbstractMemberFrontExceptions(DUPLICATED_EMAIL)
class InvalidPasswordException: AbstractMemberFrontExceptions(INVALID_PASSWORD)
