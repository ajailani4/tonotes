package com.tonotes.note_ui.viewmodel

import com.tonotes.core.Resource
import com.tonotes.core.UIState
import com.tonotes.note_domain.use_case.GetNotesUseCase
import com.tonotes.note_ui.home.HomeViewModel
import com.tonotes.note_ui.util.notes
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.verify

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @Mock
    private lateinit var getNotesUseCase: GetNotesUseCase

    private lateinit var homeViewModel: HomeViewModel

    @Before
    fun setUp() {
        homeViewModel = HomeViewModel(getNotesUseCase)
    }

    @Test
    fun `Get notes should return success`() = runBlocking {
        val resource = flowOf(notes)

        doReturn(resource).`when`(getNotesUseCase)

        val notes = when (val notesState = homeViewModel.notesState) {
            is UIState.Success -> notesState.data

            else -> listOf()
        }

        assertEquals("Notes size should be 2", 2, notes?.size)
    }
}