package com.tonotes.note_data.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.tonotes.core_ui.R
import com.tonotes.note_data.local.NoteLocalDataSource
import com.tonotes.note_data.mapper.toNoteDto
import com.tonotes.note_data.remote.NoteRemoteDataSource
import com.tonotes.note_data.remote.dto.NotesRequest
import com.tonotes.note_data.service.BackupStatusNotificationService
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.first

@HiltWorker
class UploadNotesWorker @AssistedInject constructor(
    @Assisted private val context: Context,
    @Assisted workerParams: WorkerParameters,
    private val noteLocalDataSource: NoteLocalDataSource,
    private val noteRemoteDataSource: NoteRemoteDataSource,
    private val backupStatusNotificationService: BackupStatusNotificationService
) : CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        try {
            val notes = noteLocalDataSource.getNotes("").first()
            val response = noteRemoteDataSource.uploadNotes(
                NotesRequest(
                    notes.map { noteEntity ->
                        noteEntity.toNoteDto()
                    }
                )
            )

            when (response.code()) {
                201 -> {
                    backupStatusNotificationService.showNotification(
                        context.getString(R.string.backed_up_successfully)
                    )
                }

                else -> {
                    backupStatusNotificationService.showNotification(
                        context.getString(R.string.failed_to_back_up)
                    )
                }
            }
        } catch (e: Exception) {
            Log.d("UploadNotes status", "Error $e")
            return Result.retry()
        }

        return Result.success()
    }
}