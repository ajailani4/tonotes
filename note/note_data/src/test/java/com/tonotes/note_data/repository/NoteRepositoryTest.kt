package com.tonotes.note_data.repository

import com.tonotes.core.util.Resource
import com.tonotes.note_data.local.NoteLocalDataSource
import com.tonotes.note_data.local.entity.NoteEntity
import com.tonotes.note_data.util.noteEntity
import com.tonotes.note_data.util.noteEntityList
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class NoteRepositoryTest {

    @Mock
    private lateinit var noteLocalDataSource: NoteLocalDataSource

    private lateinit var noteRepository: NoteRepository

    @Before
    fun setUp() {
        noteRepository = NoteRepositoryImpl(noteLocalDataSource)
    }

    @Test
    fun `Get notes should return success`() = runBlocking {
        val result = flowOf(noteEntityList)

        doReturn(result).`when`(noteLocalDataSource).getNotes(anyString())

        val actualResource = noteRepository.getNotes("").first()

        assertEquals(
            "Resource should be success",
            Resource.Success(noteEntityList),
            actualResource
        )
    }

    @Test
    fun `Get notes should return fail`() = runBlocking {
        val result = flow<List<NoteEntity>> { throw Throwable() }

        doReturn(result).`when`(noteLocalDataSource).getNotes(anyString())

        val actualResource = noteRepository.getNotes("").first()

        assertEquals(
            "Resource should be fail",
            Resource.Error<List<NoteEntity>>(),
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
            Resource.Success(noteEntity),
            actualResource
        )
    }

    @Test
    fun `Get note detail should return fail`() = runBlocking {
        val resource = flow<NoteEntity> { throw Throwable() }

        doReturn(resource).`when`(noteLocalDataSource).getNoteDetail(anyInt())

        val actualResource = noteRepository.getNoteDetail(1).first()

        assertEquals(
            "Resource should be fail",
            Resource.Error<NoteEntity>(),
            actualResource
        )
    }
}