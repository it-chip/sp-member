package com.sp.domain

import com.sp.enums.GenericEnum
import org.hibernate.HibernateException
import org.hibernate.engine.spi.SharedSessionContractImplementor
import org.hibernate.usertype.DynamicParameterizedType
import org.hibernate.usertype.UserType
import java.io.Serializable
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Types
import java.util.*

/**
 * @author Jaedoo Lee
 */
class GenericEnumType : DynamicParameterizedType, UserType {

    companion object {
        const val NAME = "com.sp.domain.GenericEnumType"
    }

    private lateinit var enumClass: Class<*>

    override fun setParameterValues(parameters: Properties) {
        val params = parameters[DynamicParameterizedType.PARAMETER_TYPE] as DynamicParameterizedType.ParameterType
        enumClass = params.returnedClass
    }

    override fun sqlTypes(): IntArray {
        return intArrayOf(Types.VARCHAR)
    }

    override fun returnedClass(): Class<*>? {
        return enumClass
    }

    @Throws(HibernateException::class)
    override fun equals(x: Any?, y: Any?): Boolean {
        return x === y
    }

    @Throws(HibernateException::class)
    override fun hashCode(x: Any?): Int {
        return x?.hashCode() ?: 0
    }

    @Throws(HibernateException::class, SQLException::class)
    override fun nullSafeGet(
        rs: ResultSet, names: Array<String>, session: SharedSessionContractImplementor,
        owner: Any?
    ): Any? {
        val value: String? = rs.getString(names[0])
        return value?.let {
            enumClass.enumConstants
                .asSequence()
                .filterIsInstance<GenericEnum<*>>()
                .filter { it.value == value }
                .firstOrNull()
        }
    }

    @Throws(HibernateException::class, SQLException::class)
    override fun nullSafeSet(
        st: PreparedStatement, value: Any?, index: Int,
        session: SharedSessionContractImplementor
    ) {
        when (value) {
            null -> st.setNull(index, Types.VARCHAR)
            !is GenericEnum<*> -> st.setString(index, value as String)
            else -> st.setString(index, value.value as String)
        }
    }

    @Throws(HibernateException::class)
    override fun deepCopy(value: Any?): Any? {
        return value
    }

    override fun isMutable(): Boolean {
        return false
    }

    @Throws(HibernateException::class)
    override fun disassemble(value: Any): Serializable? {
        return null
    }

    @Throws(HibernateException::class)
    override fun assemble(cached: Serializable, owner: Any): Any {
        return cached
    }

    @Throws(HibernateException::class)
    override fun replace(original: Any, target: Any, owner: Any): Any {
        return original
    }

}
