package com.sp.domain.member

import com.sp.domain.member.error.*
import com.sp.domain.member.error.MemberFrontErrorcode.*

/**
 * @author Jaedoo Lee
 */
abstract class AbstractMemberFrontExceptions(
    errorCode: MemberFrontErrorcode,
    vararg args: Any
) : MemberFrontException(errorCode, args)

class LoginException : AbstractMemberFrontExceptions(INVALID_LOGIN_INFO)
