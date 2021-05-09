package com.sp.presentation

/**
 * @author Jaedoo Lee
 */
object MemberInfoConstant {
    const val ATTRIBUTE_NAME = "Member-Info"
    const val ACCESS_TOKEN_HEADER = "accessToken"
    const val TEST_ACCESS_TOKEN = "test-access-token"

    val testMemberInfo = MemberInfo(
        no = 1L,
        email = "dlwoen9@naver.com",
        nickname = "두두"
    )
}
