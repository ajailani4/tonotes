package com.tonotes.note_domain.model

import java.util.*

data class Note(
    val id: Int? = null,
    val title: String,
    val description: String,
    val date: Date
)
