package com.tonotes.note_ui.util

import com.tonotes.note_domain.model.Note
import java.util.*

val note = Note(
    id = 1,
    title = "Note",
    description = "This is a note",
    date = Date()
)

val notes = listOf(note, note)