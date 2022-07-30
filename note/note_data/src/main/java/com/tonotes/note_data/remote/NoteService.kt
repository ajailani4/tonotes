package com.tonotes.note_data.remote

import com.tonotes.note_data.remote.dto.BaseResponse
import com.tonotes.note_data.remote.dto.NoteDto
import com.tonotes.note_data.remote.dto.NotesRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface NoteService {
    @GET("notes")
    suspend fun getNotes(): Response<BaseResponse<List<NoteDto>>>

    @POST("notes")
    suspend fun uploadNotes(
        @Query("isList") isList: Boolean,
        @Body notesRequest: NotesRequest
    ): Response<BaseResponse<Any>>
}