package com.tonotes.note_data.remote.dto

import com.squareup.moshi.Json

data class NoteDto(
    @Json(name = "noteId")
    val id: Int,
    val title: String,
    val description: String,
    val date: String
)
