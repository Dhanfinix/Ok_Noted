package dhandev.android.oknoted.ui_compose.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dhandev.android.oknoted.data.NoteItemData
import dhandev.android.oknoted.data.local.NotesLocalStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainComposeViewModel @Inject constructor(
    private val notesLocalStorage: NotesLocalStorage?
): ViewModel() {
    private val _notes = MutableStateFlow<List<NoteItemData>>(emptyList())
    val notes: StateFlow<List<NoteItemData>> = _notes
    init {
        getNotes()
    }

    private fun getNotes() {
        viewModelScope.launch {
            notesLocalStorage?.getNotes()?.collect { notesList ->
                _notes.value = notesList
            }
        }
    }
}