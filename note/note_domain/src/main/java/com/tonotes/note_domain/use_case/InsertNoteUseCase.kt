package com.tonotes.note_domain.use_case

import com.tonotes.note_data.repository.NoteRepository
import com.tonotes.note_domain.mapper.toNoteEntity
import com.tonotes.note_domain.model.Note
import java.util.*
import javax.inject.Inject

class InsertNoteUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(
        title: String,
        description: String
    ) {
        val note = Note(
            title = title,
            description = description,
            date = Date()
        )

        noteRepository.insertNote(note.toNoteEntity())
    }
}