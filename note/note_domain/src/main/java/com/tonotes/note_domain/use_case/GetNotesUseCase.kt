package com.tonotes.note_domain.use_case

import com.tonotes.core.util.Resource
import com.tonotes.note_data.repository.NoteRepository
import com.tonotes.note_domain.mapper.toNote
import com.tonotes.note_domain.model.Note
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class GetNotesUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(): Flow<Resource<List<Note>>> =
        flow {
            noteRepository.getNotes().collect {
                when (it) {
                    is Resource.Success -> {
                        val notes = it.data?.map { noteEntity ->
                            noteEntity.toNote()
                        }

                        emit(Resource.Success(notes))
                    }

                    is Resource.Error -> emit(Resource.Error(it.message))
                }
            }
        }
}