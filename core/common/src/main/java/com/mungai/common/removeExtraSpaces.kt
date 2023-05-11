package com.mungai.common

fun String.removeExtraSpaces(): String {
    return this.trim().replace(Regex("\\s{2,}"), " ")
}
