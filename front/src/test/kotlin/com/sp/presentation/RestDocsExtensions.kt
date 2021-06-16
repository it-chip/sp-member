package com.sp.presentation

import com.sp.enums.DisplayEnum

/**
 * @author Jaedoo Lee
 */
fun <T : DisplayEnum> Array<T>.toMarkdownList(): String {
    return joinToString("\n") { "- ${it.name}: ${it.label}" }
}
