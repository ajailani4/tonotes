package com.tonotes.note_domain.use_case

import com.tonotes.core.util.Resource
import com.tonotes.note_domain.model.Note
import com.tonotes.note_domain.repository.NoteRepositoryFake
import com.tonotes.note_domain.util.ResourceType
import com.tonotes.note_domain.util.note
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
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

        val actualResource = getNoteDetailUseCase(1).first()

        assertEquals(
            "Resource should be success",
            Resource.Success(note),
            actualResource
        )
    }

    @Test
    fun `Get note detail should return fail`() = runBlocking {
        noteRepositoryFake.setResourceType(ResourceType.ERROR)

        val actualResource = getNoteDetailUseCase(1).first()

        assertEquals(
            "Resource should be fail",
            Resource.Error<Note>(),
            actualResource
        )
    }
}