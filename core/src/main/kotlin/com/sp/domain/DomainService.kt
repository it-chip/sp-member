package com.sp.domain

import org.springframework.core.annotation.*
import org.springframework.stereotype.*

/**
 * @author Jaedoo Lee
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Service
annotation class DomainService(@get:AliasFor(annotation = Service::class) val value: String = "")
