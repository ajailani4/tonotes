package com.tonotes.note_domain.use_case

import com.tonotes.note_domain.repository.NoteRepository
import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(id: Int) {
        noteRepository.deleteNote(id)
    }
}