package com.tonotes.note_data.util

import com.tonotes.core.util.convertToDate
import com.tonotes.note_data.local.entity.NoteEntity
import com.tonotes.note_domain.model.Note

val noteEntity = NoteEntity(
    id = 1,
    title = "Note",
    description = "This is a note",
    date = "2022-07-18 10:00:00"
)

val noteEntityList = listOf(noteEntity, noteEntity)

val note = Note(
    id = 1,
    title = "Note",
    description = "This is a note",
    date = "2022-07-18 10:00:00".convertToDate()
)

val notes = listOf(note, note)