package com.tonotes.note_domain.use_case

import com.tonotes.note_domain.model.Note
import com.tonotes.note_domain.repository.NoteRepository
import java.util.*
import javax.inject.Inject

class InsertNoteUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(
        title: String,
        description: String
    ) {
        noteRepository.insertNote(
            Note(
                title = title,
                description = description,
                date = Date()
            )
        )
    }
}