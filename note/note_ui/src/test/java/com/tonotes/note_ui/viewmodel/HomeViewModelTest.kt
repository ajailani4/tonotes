package com.tonotes.note_ui.viewmodel

import com.tonotes.core.domain.use_case.GetAccessTokenUseCase
import com.tonotes.core_ui.UIState
import com.tonotes.note_domain.use_case.GetNotesUseCase
import com.tonotes.note_domain.use_case.GetSelectedBackupTypeUseCase
import com.tonotes.note_domain.use_case.SaveSelectedBackupTypeUseCase
import com.tonotes.note_domain.use_case.UploadNotesUseCase
import com.tonotes.note_ui.home.HomeEvent
import com.tonotes.note_ui.home.HomeViewModel
import com.tonotes.note_ui.util.TestCoroutineRule
import com.tonotes.note_ui.util.notes
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var getNotesUseCase: GetNotesUseCase

    @Mock
    private lateinit var getAccessTokenUseCase: GetAccessTokenUseCase

    @Mock
    private lateinit var uploadNotesUseCase: UploadNotesUseCase

    @Mock
    private lateinit var saveSelectedBackupTypeUseCase: SaveSelectedBackupTypeUseCase

    @Mock
    private lateinit var getSelectedBackupTypeUseCase: GetSelectedBackupTypeUseCase

    private lateinit var homeViewModel: HomeViewModel

    private lateinit var onEvent: (HomeEvent) -> Unit

    @Before
    fun setUp() {
        homeViewModel = HomeViewModel(
            getNotesUseCase,
            getAccessTokenUseCase,
            uploadNotesUseCase,
            saveSelectedBackupTypeUseCase,
            getSelectedBackupTypeUseCase
        )
        onEvent = homeViewModel::onEvent
        onEvent(HomeEvent.OnSearchQueryChanged(""))
    }

    @Test
    fun `Get notes should return success`() {
        testCoroutineRule.runTest {
            val resource = flowOf(notes)

            doReturn(resource).`when`(getNotesUseCase)(anyString())

            onEvent(HomeEvent.GetNotes)

            val notes = when (val notesState = homeViewModel.notesState) {
                is UIState.Success -> notesState.data

                else -> listOf()
            }

            assertEquals("Notes size should be 2", 2, notes?.size)
        }
    }

    @Test
    fun `Get notes should return fail`() {
        testCoroutineRule.runTest {
            val resource = flowOf(Throwable())

            doReturn(resource).`when`(getNotesUseCase)(anyString())

            onEvent(HomeEvent.GetNotes)

            val isSuccess = when (homeViewModel.notesState) {
                is UIState.Success -> true

                else -> false
            }

            assertEquals("Should be fail", false, isSuccess)
        }
    }
}