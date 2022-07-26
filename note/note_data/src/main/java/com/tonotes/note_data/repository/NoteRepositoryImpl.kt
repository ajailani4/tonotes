package com.tonotes.note_data.repository

import android.content.Context
import com.tonotes.core.data.PreferencesDataStore
import com.tonotes.core.util.Resource
import com.tonotes.core_ui.R
import com.tonotes.note_data.local.NoteLocalDataSource
import com.tonotes.note_data.local.entity.NoteEntity
import com.tonotes.note_data.remote.NoteRemoteDataSource
import com.tonotes.note_data.remote.dto.NotesRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteLocalDataSource: NoteLocalDataSource,
    private val noteRemoteDataSource: NoteRemoteDataSource,
    private val preferencesDataStore: PreferencesDataStore,
    @ApplicationContext private val context: Context
) : NoteRepository {
    override fun getNotes(searchQuery: String) =
        flow<Resource<List<NoteEntity>>> {
            noteLocalDataSource.getNotes(searchQuery).catch {
                emit(Resource.Error(it.localizedMessage))
            }.collect {
                emit(Resource.Success(it))
            }
        }

    override fun getNoteDetail(id: Int) =
        flow<Resource<NoteEntity>> {
            noteLocalDataSource.getNoteDetail(id).catch {
                emit(Resource.Error(it.localizedMessage))
            }.collect {
                emit(Resource.Success(it))
            }
        }

    override suspend fun insertNote(noteEntity: NoteEntity) {
        noteLocalDataSource.insertNote(noteEntity)
    }

    override suspend fun editNote(noteEntity: NoteEntity) {
        noteLocalDataSource.editNote(noteEntity)
    }

    override suspend fun deleteNote(id: Int) {
        noteLocalDataSource.deleteNote(id)
    }

    override fun uploadNotes(notesRequest: NotesRequest) =
        flow {
            val response = noteRemoteDataSource.uploadNotes(notesRequest)

            when (response.code()) {
                201 -> emit(Resource.Success(response.body()?.data))

                else -> emit(Resource.Error(context.getString(R.string.something_wrong_happened)))
            }
        }

    override suspend fun saveSelectedBackupType(backupType: Int) {
        preferencesDataStore.saveSelectedBackupType(backupType)
    }

    override fun getSelectedBackupType() = preferencesDataStore.getSelectedBackupType()
}