package com.tonotes.note_domain.use_case

import com.tonotes.note_domain.repository.NoteRepository
import javax.inject.Inject

class SaveSelectedBackupTypeUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(backupType: Int) {
        noteRepository.saveSelectedBackupType(backupType)
    }
}