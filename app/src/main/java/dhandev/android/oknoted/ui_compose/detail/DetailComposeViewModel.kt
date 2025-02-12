package dhandev.android.oknoted.ui_compose.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dhandev.android.oknoted.data.NoteItemData
import dhandev.android.oknoted.data.local.NotesLocalStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailComposeViewModel @Inject constructor(
    private val notesLocalStorage: NotesLocalStorage?
): ViewModel() {
    private val _uiState = MutableStateFlow(DetailScreenState())
    val uiState : StateFlow<DetailScreenState>
        get() = _uiState

    val enableSave = _uiState.map { state ->
        state.title.isNotEmpty() && state.note.isNotEmpty()
    }
    fun isBackEnable(): Boolean {
        val state = _uiState.value
        return if (state.isEditMode) {
            state.originalNote?.title == state.title && state.originalNote?.note == state.note
        } else {
            state.title.isEmpty() && state.note.isEmpty()
        }
    }

    fun updateTimeStamp(newTimeStamp: Long) {
        _uiState.update {
            it.copy(
                timeStamp = newTimeStamp,
                isEditMode = if (newTimeStamp != 0L) true else it.isEditMode
            )
        }
    }
    fun updateTitle(newTitle: String) {
        _uiState.update {
            it.copy(title = newTitle)
        }
    }
    fun updateNote(newNote: String) {
        _uiState.update {
            it.copy(note = newNote)
        }
    }

    fun addNote(onSaved: () -> Unit) {
        viewModelScope.launch {
            if (uiState.value.isEditMode){
                editNote(onSaved)
            }
            notesLocalStorage?.addNote(
                NoteItemData(
                    title = uiState.value.title,
                    note = uiState.value.note
                )
            ){
                onSaved()
            }
        }
    }

    private fun editNote(onSaved: ()->Unit) {
        viewModelScope.launch {
            notesLocalStorage?.editNote(
                uiState.value.timeStamp,
                NoteItemData(
                    title = uiState.value.title,
                    note = uiState.value.note
                )
            ){
                onSaved()
            }
        }
    }

    fun getNoteByTimestamp(){
        viewModelScope.launch {
            val savedNote = notesLocalStorage?.getNoteByTimestamp(uiState.value.timeStamp)?.first()
            _uiState.update {
                it.copy(
                    originalNote = savedNote,
                    title = savedNote?.title ?: "",
                    note = savedNote?.note ?: "",
                    timeStamp = savedNote?.timeStamp ?: 0L
                )
            }
        }
    }

    fun removeNote(
        onSaved: () -> Unit
    ) {
        viewModelScope.launch {
            uiState.value.originalNote?.let {
                notesLocalStorage?.removeNote(it) {
                    onSaved()
                }
            }
        }
    }

    fun setAlertDialog(state: AlertDialogState?) {
        _uiState.update {
            it.copy(alertDialog = state)
        }
    }
}