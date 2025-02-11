package dhandev.android.oknoted.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dhandev.android.oknoted.data.NoteItemData
import dhandev.android.oknoted.data.local.NotesLocalStorage
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val notesLocalStorage: NotesLocalStorage
): ViewModel() {
    private val _notes = MutableLiveData<List<NoteItemData>>(emptyList())
    val notes: LiveData<List<NoteItemData>> = _notes
    init {
        getNotes()
    }

    private fun getNotes() {
        viewModelScope.launch {
            notesLocalStorage.getNotes().collect { notesList ->
                _notes.value = notesList
            }
        }
    }
}