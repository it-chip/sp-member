package com.sp.domain.member.entity

import com.sp.domain.member.model.MemberRegisterModel
import com.sp.domain.member.model.MemberUpdateModel
import com.sp.domain.member.util.MemberPasswordEncryptor
import org.hibernate.annotations.DynamicUpdate
import java.time.LocalDateTime
import javax.persistence.*

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
    var email: String,

    @Column(name = "password")
    var password: String,

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

    fun modify(params: MemberUpdateModel) {
        if (!params.email.isNullOrBlank()) this.email = params.email
        if (!params.newPassword.isNullOrBlank()) this.password = params.newPassword
        if (!params.nickname.isNullOrBlank()) this.nickname = params.nickname
        this.updateDateTime = LocalDateTime.now()
    }
}
