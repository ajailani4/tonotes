package com.tonotes.note_domain.use_case

import com.tonotes.note_domain.repository.NoteRepositoryFake
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
        val actualResource = getNoteDetailUseCase(1).first()

        assertEquals(
            "Should be success",
            note,
            actualResource
        )
    }
}