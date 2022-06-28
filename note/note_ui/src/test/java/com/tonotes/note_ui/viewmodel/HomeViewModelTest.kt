package com.tonotes.note_ui.viewmodel

import com.tonotes.core.Resource
import com.tonotes.core.UIState
import com.tonotes.note_domain.use_case.GetNotesUseCase
import com.tonotes.note_ui.home.HomeEvent
import com.tonotes.note_ui.home.HomeViewModel
import com.tonotes.note_ui.util.TestCoroutineRule
import com.tonotes.note_ui.util.notes
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @Mock
    private lateinit var getNotesUseCase: GetNotesUseCase

    private lateinit var homeViewModel: HomeViewModel

    private lateinit var onEvent: (HomeEvent) -> Unit

    @Before
    fun setUp() {
        homeViewModel = HomeViewModel(getNotesUseCase)
        onEvent = homeViewModel::onEvent
        onEvent(HomeEvent.OnSearchQueryChanged(""))
    }

    @Test
    fun `Get notes should return success`() {
        testCoroutineRule.runTest {
            val resource = flowOf(Resource.Success(notes))

            doReturn(resource).`when`(getNotesUseCase)(anyString())

            onEvent(HomeEvent.GetNotes)

            val notes = when (val notesState = homeViewModel.notesState) {
                is UIState.Success -> notesState.data

                else -> listOf()
            }

            assertEquals("Notes size should be 2", 2, notes?.size)
        }
    }
}