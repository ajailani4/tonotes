package com.tonotes.note_domain.use_case

import com.tonotes.core.Resource
import com.tonotes.note_domain.repository.NoteRepositoryFake
import com.tonotes.note_domain.util.ResourceType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class GetNoteDetailUseCaseTest {
    private lateinit var noteRepositoryFake: NoteRepositoryFake
    private lateinit var getNoteDetailUseCase: GetNoteDetailUseCase

    @Before
    fun setUp() {
        noteRepositoryFake = NoteRepositoryFake()
        getNoteDetailUseCase = GetNoteDetailUseCase(noteRepositoryFake)
    }

    @Test
    fun `Get note detail should return success`() = runBlocking {
        noteRepositoryFake.setResourceType(ResourceType.SUCCESS)

        val isSuccess = when (getNoteDetailUseCase(1).first()) {
            is Resource.Success -> true
            is Resource.Error -> false
        }

        assertEquals("Resource should be success", true, isSuccess)
    }

    @Test
    fun `Get note detail should return fail`() = runBlocking {
        noteRepositoryFake.setResourceType(ResourceType.ERROR)

        val isSuccess = when (getNoteDetailUseCase(1).first()) {
            is Resource.Success -> true
            is Resource.Error -> false
        }

        assertEquals("Resource should be fail", false, isSuccess)
    }
}