package com.tonotes.note_domain.use_case

import com.tonotes.core.util.Resource
import com.tonotes.note_domain.repository.NoteRepositoryFake
import com.tonotes.note_domain.util.ResourceType
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SyncNotesUseCaseTest {
    private lateinit var noteRepositoryFake: NoteRepositoryFake
    private lateinit var syncNotesUseCase: SyncNotesUseCase

    @Before
    fun setUp() {
        noteRepositoryFake = NoteRepositoryFake()
        syncNotesUseCase = SyncNotesUseCase(noteRepositoryFake)
    }

    @Test
    fun `Sync notes should return success`() = runBlocking {
        noteRepositoryFake.setResourceType(ResourceType.SUCCESS)

        val actualResource = syncNotesUseCase().first()

        assertEquals(
            "Resource should be success",
            Resource.Success("Synced successfully"),
            actualResource
        )
    }

    @Test
    fun `Sync notes should return fail`() = runBlocking {
        noteRepositoryFake.setResourceType(ResourceType.ERROR)

        val actualResource = syncNotesUseCase().first()

        assertEquals(
            "Resource should be success",
            Resource.Error<String>(),
            actualResource
        )
    }
}