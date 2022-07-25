package com.tonotes.note_data.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.tonotes.core.util.Resource
import com.tonotes.note_data.mapper.toNoteDto
import com.tonotes.note_data.remote.dto.NotesRequest
import com.tonotes.note_data.repository.NoteRepository
import dagger.assisted.Assisted

@HiltWorker
class UploadNotesWorker(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val noteRepository: NoteRepository
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        try {
            noteRepository.getNotes("").collect {
                when (it) {
                    is Resource.Success -> {
                        val notesDto = it.data?.map { noteEntity ->
                            noteEntity.toNoteDto()
                        }

                        if (notesDto != null) {
                            noteRepository.uploadNotes(
                                NotesRequest(notesDto)
                            )
                        }
                    }

                    else -> {}
                }
            }
        } catch (e: Exception) {
            return Result.retry()
        }

        return Result.success()
    }
}