package com.tonotes.note_data.util

import com.tonotes.core.util.convertToDate
import com.tonotes.note_data.local.entity.NoteEntity
import com.tonotes.note_data.remote.dto.NoteDto
import com.tonotes.note_domain.model.Note

val noteEntity = NoteEntity(
    id = 1,
    title = "Note",
    description = "This is a note",
    date = "2022-07-18 10:00:00"
)

val notesEntity = listOf(noteEntity, noteEntity)

val noteDto = NoteDto(
    id = 1,
    title = "Note",
    description = "This is a note",
    date = "2022-07-18 10:00:00"
)

val notesDto = listOf(noteDto, noteDto)

val note = Note(
    id = 1,
    title = "Note",
    description = "This is a note",
    date = "2022-07-18 10:00:00".convertToDate()
)

val notes = listOf(note, note)
