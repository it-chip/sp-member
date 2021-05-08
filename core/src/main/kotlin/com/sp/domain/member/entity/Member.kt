package com.sp.domain.member.entity

import com.sp.domain.member.model.*
import com.sp.domain.member.util.*
import org.hibernate.annotations.*
import java.time.*
import javax.persistence.*
import javax.persistence.Entity
import javax.persistence.Table

/**
 * @author Jaedoo Lee
 */
@Entity
@Table(name = "mb_member")
@DynamicUpdate
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
    var nickname: String = ""

) {

    @Column(name = "join_ymdt")
    val joinDateTime: LocalDateTime = LocalDateTime.now()

    @Column(name = "update_ymdt")
    var updateDateTime: LocalDateTime? = null

    companion object {
        fun create(params: MemberRegisterModel) = Member(
            email = params.email,
            password = params.password,
            nickname = params.nickname
        )
    }

    fun matchesPassword(rawPassword: String): Boolean {
        return try {
            MemberPasswordEncryptor.matches(password, rawPassword)
        } catch (e: Exception) {
            false
        }
    }

    fun modify(nickname: String) {
        this.nickname = nickname
        this.updateDateTime = LocalDateTime.now()
    }
}
