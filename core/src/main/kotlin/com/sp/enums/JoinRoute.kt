package com.sp.enums

import com.sp.domain.MessageConverter

/**
 * @author Jaedoo Lee
 */
enum class JoinRoute(
    override val value: String,
    override val code: String
) : GenericEnum<String>, DisplayEnum {

    PC("JIRT0001", "member.join-route.pc"),

    MOBILE_WEB("JIRT0002", "member.join-route.mobile-web"),

    MOBILE_APP("JIRT0003", "member.join-route.mobile-app");

    override val label = MessageConverter.getMessage(code)
}
