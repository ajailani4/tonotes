package com.tonotes.note_domain.use_case

import androidx.work.*
import com.tonotes.core.util.Constants.WorkerName
import com.tonotes.note_data.repository.NoteRepository
import com.tonotes.note_data.worker.UploadNotesWorker
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class UploadNotesUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {
    operator fun invoke(backupType: Int) {
        noteRepository.uploadNotes(backupType)
    }
}