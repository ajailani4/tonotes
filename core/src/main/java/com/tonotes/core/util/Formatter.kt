package com.tonotes.core.util

import java.text.SimpleDateFormat
import java.util.*

fun String.convertToDate(): Date {
    val dateFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())

    return dateFormatter.parse(this)!!
}

fun Date.convertToString(pattern: String): String {
    val dateFormatter = SimpleDateFormat(pattern, Locale.getDefault())

    return dateFormatter.format(this)
}