package com.tonotes.note_data.repository

import android.content.Context
import androidx.work.WorkManager
import com.tonotes.core.data.PreferencesDataStore
import com.tonotes.core.util.Resource
import com.tonotes.core_ui.R
import com.tonotes.note_data.local.NoteLocalDataSource
import com.tonotes.note_data.remote.NoteRemoteDataSource
import com.tonotes.note_data.remote.dto.BaseResponse
import com.tonotes.note_data.util.note
import com.tonotes.note_data.util.noteEntity
import com.tonotes.note_data.util.notes
import com.tonotes.note_data.util.notesDto
import com.tonotes.note_data.util.notesEntity
import com.tonotes.note_domain.repository.NoteRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import okhttp3.ResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class NoteRepositoryTest {

    @Mock
    private lateinit var noteLocalDataSource: NoteLocalDataSource

    @Mock
    private lateinit var noteRemoteDataSource: NoteRemoteDataSource

    @Mock
    private lateinit var preferencesDataStore: PreferencesDataStore

    @Mock
    private lateinit var workManager: WorkManager

    @Mock
    private lateinit var context: Context

    private lateinit var noteRepository: NoteRepository

    @Before
    fun setUp() {
        noteRepository = NoteRepositoryImpl(
            noteLocalDataSource,
            noteRemoteDataSource,
            preferencesDataStore,
            workManager,
            context
        )
    }

    @Test
    fun `Get notes should return success`() = runBlocking {
        val result = flowOf(notesEntity)

        doReturn(result).`when`(noteLocalDataSource).getNotes(anyString())

        val actualResource = noteRepository.getNotes("").first()

        assertEquals(
            "Resource should be success",
            notes,
            actualResource
        )
    }

    @Test
    fun `Get note detail should return success`() = runBlocking {
        val resource = flowOf(noteEntity)

        doReturn(resource).`when`(noteLocalDataSource).getNoteDetail(anyInt())

        val actualResource = noteRepository.getNoteDetail(1).first()

        assertEquals(
            "Resource should be success",
            note,
            actualResource
        )
    }

    @Test
    fun `Sync notes should return success`() = runBlocking {
        val resource = Response.success(
            200,
            BaseResponse(
                code = 200,
                status = "OK",
                data = notesDto
            )
        )

        doReturn("Success").`when`(context).getString(R.string.notes_sync_successfully)
        doReturn(resource).`when`(noteRemoteDataSource).syncNotes()

        val actualResource = noteRepository.syncNotes().first()

        assertEquals(
            "Resource should be success",
            Resource.Success("Success"),
            actualResource
        )
    }

    @Test
    fun `Sync notes should return fail`() = runBlocking {
        val resource: Response<Unit> = Response.error(
            400,
            ResponseBody.create(null, "")
        )

        doReturn(null).`when`(context).getString(R.string.something_wrong_happened)
        doReturn(resource).`when`(noteRemoteDataSource).syncNotes()

        val actualResource = noteRepository.syncNotes().first()

        assertEquals(
            "Resource should be fail",
            Resource.Error<String>(null),
            actualResource
        )
    }
}