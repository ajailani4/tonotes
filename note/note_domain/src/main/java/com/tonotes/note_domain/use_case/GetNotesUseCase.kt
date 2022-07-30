package com.tonotes.note_domain.use_case

import com.tonotes.note_domain.repository.NoteRepository
import javax.inject.Inject

class GetNotesUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {
    operator fun invoke(searchQuery: String) = noteRepository.getNotes(searchQuery)
}