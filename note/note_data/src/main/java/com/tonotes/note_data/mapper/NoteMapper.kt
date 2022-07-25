package com.tonotes.note_data.mapper

import com.tonotes.note_data.local.entity.NoteEntity
import com.tonotes.note_data.remote.dto.NoteDto

fun NoteEntity.toNoteDto() =
    NoteDto(
        id = id!!,
        title = title,
        description = description,
        date = date
    )