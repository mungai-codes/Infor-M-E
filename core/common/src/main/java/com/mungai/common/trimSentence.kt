package com.mungai.common

fun String.trimSentence(): String {
    if (this.length <= 15) {
        return this
    }
    return this.substring(0, 15).trim() + "..."
}