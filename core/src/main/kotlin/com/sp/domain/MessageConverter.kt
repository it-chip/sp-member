package com.sp.domain

import org.springframework.context.*
import org.springframework.context.i18n.*
import org.springframework.context.support.*

/**
 * @author Jaedoo Lee
 */
object MessageConverter {

    private val message: MessageSource = ResourceBundleMessageSource().apply {
        setBasenames(
            "messages/message", "messages/error", "messages/menu",
            // 덮여 써져야 하는 거
            "messages/base-message", "messages/base-error"
        )
        setDefaultEncoding("UTF-8")
    }

    fun getMessage(code: String, vararg args: Any): String {
        val default = "No message for $code"
        return message.getMessage(code, args, default, LocaleContextHolder.getLocale()) ?: default
    }

}
