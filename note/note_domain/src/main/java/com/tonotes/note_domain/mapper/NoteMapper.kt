package com.tonotes.note_domain.mapper

import com.tonotes.core.convertToDate
import com.tonotes.note_data.local.entity.NoteEntity
import com.tonotes.note_domain.model.Note

fun NoteEntity.toNote(): Note {
    return Note(
        id = id,
        title = title,
        description = description,
        date = date.convertToDate()
    )
}

fun Note.toNoteEntity(): NoteEntity {
    return NoteEntity(
        id = id,
        title = title,
        description = description,
        date = date.toString()
    )
}