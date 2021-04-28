package com.sp.domain.extensions

import com.fasterxml.jackson.databind.*
import com.fasterxml.jackson.datatype.hibernate5.*
import com.fasterxml.jackson.datatype.jsr310.*
import com.fasterxml.jackson.datatype.jsr310.deser.*
import com.fasterxml.jackson.datatype.jsr310.ser.*
import com.fasterxml.jackson.module.kotlin.*
import java.time.*
import java.time.format.*

/**
 * @author Jaedoo Lee
 */
val objectMapper: ObjectMapper = ObjectMapper().apply {
    registerModule(KotlinModule(nullToEmptyCollection = true, nullToEmptyMap = true, nullIsSameAsDefault = true))
    registerModule(Hibernate5Module())
    registerModule(JavaTimeModule().apply {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        addSerializer(LocalDateTime::class.java, LocalDateTimeSerializer(formatter))
        addDeserializer(LocalDateTime::class.java, LocalDateTimeDeserializer(formatter))
    })
    disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
    disable(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS)
    enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
    disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
    enable(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES)
}

fun Any.toJson(): String = objectMapper.writeValueAsString(this)
inline fun <reified T> String.toModel(): T = objectMapper.readValue(this, jacksonTypeRef<T>())
