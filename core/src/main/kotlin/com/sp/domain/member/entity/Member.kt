package com.sp.domain.member.entity

import com.sp.domain.member.model.*
import java.time.*
import javax.persistence.*

/**
 * @author Jaedoo Lee
 */
@Entity
@Table(name = "mb_member")
data class Member(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_no")
    val no: Long = 0,

    @Column(name = "email")
    val email: String,

    @Column(name = "password")
    val password: String,

    @Column(name = "nickname")
    val nickname: String? = null,

    @Column(name = "join_ymdt")
    val joinDateTime: LocalDateTime = LocalDateTime.now()

) {

    companion object {
        fun create(params: MemberRegisterModel) = Member(
            email = params.email,
            password = params.password,
            nickname = params.nickname
        )
    }
}
