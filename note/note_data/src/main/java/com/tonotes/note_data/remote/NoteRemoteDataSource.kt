package com.tonotes.note_data.remote

import com.tonotes.note_data.remote.dto.NotesRequest
import javax.inject.Inject

class NoteRemoteDataSource @Inject constructor(
    private val noteService: NoteService
) {
    suspend fun uploadNotes(notesRequest: NotesRequest) =
        noteService.uploadNotes(
            isList = true,
            notesRequest = notesRequest
        )
}