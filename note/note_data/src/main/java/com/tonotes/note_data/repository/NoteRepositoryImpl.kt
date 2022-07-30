package com.tonotes.note_data.repository

import android.content.Context
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.tonotes.core.data.PreferencesDataStore
import com.tonotes.core.util.Constants
import com.tonotes.core.util.Resource
import com.tonotes.core_ui.R
import com.tonotes.note_data.local.NoteLocalDataSource
import com.tonotes.note_data.mapper.toNote
import com.tonotes.note_data.mapper.toNoteEntity
import com.tonotes.note_data.remote.NoteRemoteDataSource
import com.tonotes.note_data.worker.UploadNotesWorker
import com.tonotes.note_domain.model.Note
import com.tonotes.note_domain.repository.NoteRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteLocalDataSource: NoteLocalDataSource,
    private val noteRemoteDataSource: NoteRemoteDataSource,
    private val preferencesDataStore: PreferencesDataStore,
    private val workManager: WorkManager,
    @ApplicationContext private val context: Context
) : NoteRepository {
    override fun getNotes(searchQuery: String) =
        flow<Resource<List<Note>>> {
            noteLocalDataSource.getNotes(searchQuery).catch {
                emit(Resource.Error(it.localizedMessage))
            }.collect {
                emit(
                    Resource.Success(
                        it.map { noteEntity ->
                            noteEntity.toNote()
                        }
                    )
                )
            }
        }

    override fun getNotesFromRemote() =
        flow {
            val response = noteRemoteDataSource.getNotes()

            when (response.code()) {
                200 -> {
                    val notesDto = response.body()?.data

                    notesDto?.forEach { noteDto ->
                        noteLocalDataSource.insertNote(noteDto.toNoteEntity())
                    }

                    emit(Resource.Success(context.getString(R.string.notes_sync_successfully)))
                }

                else -> emit(Resource.Error(context.getString(R.string.something_wrong_happened)))
            }
        }

    override fun getNoteDetail(id: Int) =
        flow<Resource<Note>> {
            noteLocalDataSource.getNoteDetail(id).catch {
                emit(Resource.Error(it.localizedMessage))
            }.collect {
                emit(Resource.Success(it?.toNote()))
            }
        }

    override suspend fun insertNote(note: Note) {
        noteLocalDataSource.insertNote(note.toNoteEntity())
    }

    override suspend fun editNote(note: Note) {
        noteLocalDataSource.editNote(note.toNoteEntity())
    }

    override suspend fun deleteNote(id: Int) {
        noteLocalDataSource.deleteNote(id)
    }

    override fun uploadNotes(backupType: Int) {
        val uploadNotesRequest = when (backupType) {
            0 -> OneTimeWorkRequestBuilder<UploadNotesWorker>().build()

            1 -> PeriodicWorkRequestBuilder<UploadNotesWorker>(1, TimeUnit.DAYS).build()

            2 -> PeriodicWorkRequestBuilder<UploadNotesWorker>(7, TimeUnit.DAYS).build()

            else -> OneTimeWorkRequestBuilder<UploadNotesWorker>().build()
        }

        if (backupType == 0) {
            workManager.enqueueUniqueWork(
                Constants.WorkerName.UPLOAD_NOTES_WORKER,
                ExistingWorkPolicy.REPLACE,
                uploadNotesRequest as OneTimeWorkRequest
            )
        } else {
            workManager.enqueueUniquePeriodicWork(
                Constants.WorkerName.UPLOAD_NOTES_WORKER,
                ExistingPeriodicWorkPolicy.REPLACE,
                uploadNotesRequest as PeriodicWorkRequest
            )
        }
    }

    override suspend fun saveSelectedBackupType(backupType: Int) {
        preferencesDataStore.saveSelectedBackupType(backupType)
    }

    override fun getSelectedBackupType() = preferencesDataStore.getSelectedBackupType()
}