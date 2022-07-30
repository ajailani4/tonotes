package com.tonotes.note_data.mapper

import com.tonotes.core.util.convertToDate
import com.tonotes.core.util.convertToString
import com.tonotes.note_data.local.entity.NoteEntity
import com.tonotes.note_data.remote.dto.NoteDto
import com.tonotes.note_domain.model.Note

fun NoteEntity.toNoteDto() =
    NoteDto(
        id = id!!,
        title = title,
        description = description,
        date = date
    )

fun NoteEntity.toNote() =
    Note(
        id = id,
        title = title,
        description = description,
        date = date.convertToDate()
    )

fun Note.toNoteEntity() =
    NoteEntity(
        id = id,
        title = title,
        description = description,
        date = date.convertToString("yyyy-MM-dd HH:mm:ss")
    )

fun NoteDto.toNoteEntity() =
    NoteEntity(
        id = id,
        title = title,
        description = description,
        date = date
    )