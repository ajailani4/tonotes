package com.tonotes.note_domain.use_case

import com.tonotes.note_domain.model.Note
import com.tonotes.note_domain.repository.NoteRepository
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
        noteRepository.editNote(
            Note(
                id = id,
                title = title,
                description = description,
                date = date
            )
        )
    }
}