package com.mungai.common

import java.time.Duration
import java.time.Instant

fun String.freshness(): String {
    val instant = Instant.parse(this)
    val now = Instant.now()
    val duration = Duration.between(instant, now)
    val seconds = duration.seconds

    val days = seconds / (24 * 3600)
    if (days > 1) {
        return "$days days ago"
    } else if (days == 1L) {
        return "$days day ago"
    }

    val hours = seconds / 3600
    if (hours > 1) {
        return "$hours hours ago"
    } else if (hours == 1L) {
        return "$hours hour ago"
    }
    val minutes = seconds / 60
    if (minutes > 15L) {
        return "$minutes minutes ago"
    }
    return "just now"
}
