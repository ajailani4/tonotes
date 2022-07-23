package com.tonotes.core.util

import java.text.SimpleDateFormat
import java.util.*

private val localeID = Locale(Locale.getDefault().displayLanguage, "ID")

fun String.convertToDate(): Date {
    val dateFormatter = SimpleDateFormat("yyyy-MM-dd hh:mm:ss", localeID)

    return dateFormatter.parse(this)!!
}

fun Date.convertToString(pattern: String): String {
    val dateFormatter = SimpleDateFormat(pattern, localeID)

    return dateFormatter.format(this)
}