package com.tonotes.note_domain.use_case

import com.tonotes.note_domain.repository.NoteRepository
import javax.inject.Inject

class GetNoteDetailUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {
    operator fun invoke(id: Int) = noteRepository.getNoteDetail(id)
}