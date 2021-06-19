package com.sp.enums

import com.sp.domain.MessageConverter

/**
 * @author Jaedoo Lee
 */
enum class MemberType(
    override val value: String,
    override val code: String
) : GenericEnum<String>, DisplayEnum {

    NORMAL("MBTP0001", "member.type.normal"),

    NAVER("MBTP0002", "member.type.naver");

    override val label = MessageConverter.getMessage(code)
}
