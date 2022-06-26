package com.tonotes.note_domain.use_case

import com.tonotes.note_data.repository.NoteRepository
import com.tonotes.note_domain.mapper.toNoteEntity
import com.tonotes.note_domain.model.Note
import java.util.*
import javax.inject.Inject

class EditNoteUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(
        id: Int,
        title: String,
        description: String,
        date: Date
    ) {
        val note = Note(
            id = id,
            title = title,
            description = description,
            date = date
        )

        noteRepository.editNote(note.toNoteEntity())
    }
}