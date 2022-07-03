package com.tonotes.note_ui.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tonotes.core.Resource
import com.tonotes.core.UIState
import com.tonotes.note_domain.model.Note
import com.tonotes.note_domain.use_case.GetNotesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNotesUseCase: GetNotesUseCase
) : ViewModel() {
    var notesState by mutableStateOf<UIState<List<Note>>>(UIState.Idle)
        private set

    var searchQuery by mutableStateOf("")
        private set

    init {
        onEvent(HomeEvent.GetNotes)
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.GetNotes -> getNotes()

            is HomeEvent.OnSearchQueryChanged -> searchQuery = event.searchQuery
        }
    }

    private fun getNotes() {
        viewModelScope.launch {
            val resource = getNotesUseCase(searchQuery)

            resource.catch {
                notesState = UIState.Error(it.localizedMessage)
            }.collect {
                notesState = when (it) {
                    is Resource.Success -> UIState.Success(it.data)

                    is Resource.Error -> UIState.Fail(it.message)
                }
            }
        }
    }
}