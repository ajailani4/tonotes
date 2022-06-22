package com.tonotes.note_domain.mapper

import com.tonotes.note_data.local.entity.NoteEntity
import com.tonotes.note_domain.model.Note
import java.text.SimpleDateFormat
import java.util.*

val localeID = Locale(Locale.getDefault().displayLanguage, "ID")
val dateFormatter = SimpleDateFormat("dd MM yyyy", localeID)

fun NoteEntity.toNote(): Note {
    return Note(
        id = id,
        title = title,
        description = description,
        date = dateFormatter.format(date!!)
    )
}

fun Note.toNoteEntity(): NoteEntity {
    return NoteEntity(
        id = id,
        title = title,
        description = description,
        date = dateFormatter.parse(date)
    )
}