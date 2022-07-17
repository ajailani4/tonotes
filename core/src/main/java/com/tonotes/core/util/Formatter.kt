package com.tonotes.core.util

import java.text.SimpleDateFormat
import java.util.*

private val localeID = Locale(Locale.getDefault().displayLanguage, "ID")
private val dateFormatter = SimpleDateFormat("yyyy-MM-dd", localeID)

fun String.convertToDate() = dateFormatter.parse(this)!!

fun Date.convertToString(pattern: String? = null): String {
    if (pattern != null) dateFormatter.applyPattern(pattern)

    return dateFormatter.format(this)
}