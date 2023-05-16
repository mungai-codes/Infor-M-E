package com.mungai.common

fun String.trimSentence(length: Int = 15): String {
    if (this.length <= length) {
        return this
    }
    return this.substring(0, length).trim() + "..."
}