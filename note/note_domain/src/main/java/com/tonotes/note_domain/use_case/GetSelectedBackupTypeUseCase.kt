package com.tonotes.note_domain.use_case

import com.tonotes.note_data.repository.NoteRepository
import javax.inject.Inject

class GetSelectedBackupTypeUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {
    operator fun invoke() = noteRepository.getSelectedBackupType()
}