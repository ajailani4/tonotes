package com.tonotes.note_ui.note_detail

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tonotes.core.Constants
import com.tonotes.core.Resource
import com.tonotes.core.UIState
import com.tonotes.note_domain.model.Note
import com.tonotes.note_domain.use_case.GetNoteDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteDetailViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getNoteDetailUseCase: GetNoteDetailUseCase
) : ViewModel() {
    var noteDetailState by mutableStateOf<UIState<Note>>(UIState.Idle)
        private set

    init {
        getNoteDetail()
    }

    private fun getNoteDetail() {
        viewModelScope.launch {
            val resource = getNoteDetailUseCase(savedStateHandle.get<Int>(Constants.NOTE_ID)!!)

            resource.catch {
                noteDetailState = UIState.Error(it.localizedMessage)
            }.collect {
                noteDetailState = when (it) {
                    is Resource.Success -> UIState.Success(it.data)

                    is Resource.Error -> UIState.Fail(it.message)
                }
            }
        }
    }
}