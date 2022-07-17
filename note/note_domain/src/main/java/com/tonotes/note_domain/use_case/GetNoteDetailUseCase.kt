package com.tonotes.note_domain.use_case

import com.tonotes.core.util.Resource
import com.tonotes.note_data.repository.NoteRepository
import com.tonotes.note_domain.mapper.toNote
import com.tonotes.note_domain.model.Note
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetNoteDetailUseCase @Inject constructor(
    private val noteRepository: NoteRepository
) {
    suspend operator fun invoke(id: Int) =
        flow<Resource<Note>> {
            noteRepository.getNoteDetail(id).collect {
                when (it) {
                    is Resource.Success -> emit(Resource.Success(it.data?.toNote()))

                    is Resource.Error -> emit(Resource.Error(it.message))
                }
            }
        }
}