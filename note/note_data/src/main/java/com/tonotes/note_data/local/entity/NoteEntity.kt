package com.tonotes.note_data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class NoteEntity(
    @PrimaryKey
    val id: Int? = null,
    val title: String,
    val description: String,
    val date: String
)