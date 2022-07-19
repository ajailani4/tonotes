package com.tonotes.note_domain.util

import com.tonotes.core.util.convertToDate
import com.tonotes.note_domain.model.Note

val note = Note(
    id = 1,
    title = "Note 1",
    description = "This is a note",
    date = "2022-07-18".convertToDate()
)

val notes = listOf(note)