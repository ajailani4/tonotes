package com.tonotes.note_data.util

import com.tonotes.note_data.local.entity.NoteEntity

val noteEntity = NoteEntity(
    id = 1,
    title = "Note",
    description = "This is a note",
    date = "30 Jun 2022"
)

val noteEntityList = listOf(noteEntity, noteEntity)