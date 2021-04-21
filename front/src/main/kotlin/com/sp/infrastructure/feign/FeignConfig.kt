package com.sp.infrastructure.feign

import org.springframework.beans.factory.*
import org.springframework.boot.autoconfigure.http.*
import org.springframework.context.annotation.*
import org.springframework.http.converter.*
import kotlin.streams.*

/**
 * @author Jaedoo Lee
 */
@Configuration
class FeignConfig {

    @Bean
    fun messageConverters(converters: ObjectProvider<HttpMessageConverter<*>>): HttpMessageConverters {
        return HttpMessageConverters(true, converters.orderedStream().toList())
    }

}
