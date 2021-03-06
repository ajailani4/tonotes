package com.tonotes.note_ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.tonotes.core.util.Constants.NavArgument
import com.tonotes.core_ui.UIState
import com.tonotes.note_domain.use_case.DeleteNoteUseCase
import com.tonotes.note_domain.use_case.GetNoteDetailUseCase
import com.tonotes.note_ui.note_detail.NoteDetailEvent
import com.tonotes.note_ui.note_detail.NoteDetailViewModel
import com.tonotes.note_ui.util.TestCoroutineRule
import com.tonotes.note_ui.util.note
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyInt
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class NoteDetailViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var getNoteDetailUseCase: GetNoteDetailUseCase

    @Mock
    private lateinit var deleteNoteUseCase: DeleteNoteUseCase

    private lateinit var savedStateHandle: SavedStateHandle

    private lateinit var noteDetailViewModel: NoteDetailViewModel

    private lateinit var onEvent: (NoteDetailEvent) -> Unit

    @Before
    fun setUp() {
        savedStateHandle = SavedStateHandle().apply {
            set(NavArgument.NOTE_ID, 1)
        }
        noteDetailViewModel = NoteDetailViewModel(
            savedStateHandle,
            getNoteDetailUseCase,
            deleteNoteUseCase
        )
        onEvent = noteDetailViewModel::onEvent
    }

    @Test
    fun `Get note detail should return success`() {
        testCoroutineRule.runTest {
            val resource = flowOf(note)

            doReturn(resource).`when`(getNoteDetailUseCase)(anyInt())

            onEvent(NoteDetailEvent.GetNoteDetail)

            val note = when (val noteDetailState = noteDetailViewModel.noteDetailState) {
                is UIState.Success -> noteDetailState.data

                else -> null
            }

            assertNotNull("Should not null", note)
            assertEquals("Note id should be 1", 1, note?.id)
        }
    }

    @Test
    fun `Get note detail should return fail`() {
        testCoroutineRule.runTest {
            val resource = flowOf(Throwable())

            doReturn(resource).`when`(getNoteDetailUseCase)(anyInt())

            onEvent(NoteDetailEvent.GetNoteDetail)

            val isSuccess = when (noteDetailViewModel.noteDetailState) {
                is UIState.Success -> true

                else -> false
            }

            assertEquals("Should be fail", false, isSuccess)
        }
    }
}