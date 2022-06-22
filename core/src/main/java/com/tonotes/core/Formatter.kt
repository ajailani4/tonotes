package com.tonotes.core

import java.text.SimpleDateFormat
import java.util.*

private val localeID = Locale(Locale.getDefault().displayLanguage, "ID")
private val dateFormatter = SimpleDateFormat("dd MMM yyyy", localeID)

fun String.convertToDate(): Date = dateFormatter.parse(this)!!

fun Date.convertToString(): String = dateFormatter.format(this)