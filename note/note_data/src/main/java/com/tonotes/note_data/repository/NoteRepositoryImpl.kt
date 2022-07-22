package com.tonotes.note_data.repository

import com.tonotes.core.util.Resource
import com.tonotes.note_data.local.NoteLocalDataSource
import com.tonotes.note_data.local.entity.NoteEntity
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(
    private val noteLocalDataSource: NoteLocalDataSource
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
}