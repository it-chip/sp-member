package com.sp.domain.member

import com.sp.domain.*
import com.sp.domain.member.error.*

/**
 * @author Jaedoo Lee
 */
class MemberTokenInvalidException : CommonException(MemberErrorCode.TOKEN_EXPIRED)
