package com.tonotes.note_domain.use_case

import com.tonotes.core.util.Resource
import com.tonotes.note_domain.model.Note
import com.tonotes.note_domain.repository.NoteRepositoryFake
import com.tonotes.note_domain.util.ResourceType
import com.tonotes.note_domain.util.notes
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetNotesUseCaseTest {
    private lateinit var noteRepositoryFake: NoteRepositoryFake
    private lateinit var getNotesUseCase: GetNotesUseCase

    @Before
    fun setUp() {
        noteRepositoryFake = NoteRepositoryFake()
        getNotesUseCase = GetNotesUseCase(noteRepositoryFake)
    }

    @Test
    fun `Get notes should return success`() = runBlocking {
        noteRepositoryFake.setResourceType(ResourceType.SUCCESS)

        val actualResource = getNotesUseCase("").first()

        assertEquals(
            "Resource should be success",
            Resource.Success(notes),
            actualResource
        )
    }

    @Test
    fun `Get notes should return fail`() = runBlocking {
        noteRepositoryFake.setResourceType(ResourceType.ERROR)

        val actualResource = getNotesUseCase("").first()

        assertEquals(
            "Resource should be fail",
            Resource.Error<List<Note>>(),
            actualResource
        )
    }
}