package com.sp.domain.member

import com.sp.domain.member.entity.*
import org.springframework.data.jpa.repository.*

/**
 * @author Jaedoo Lee
 */
interface MemberRepository : JpaRepository<Member, Long> {
    fun findByEmail(email: String): Member?
}
