package dhandev.android.oknoted.ui.detail

import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dhandev.android.oknoted.data.NoteItemData
import dhandev.android.oknoted.data.local.NotesLocalStorage
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val notesLocalStorage: NotesLocalStorage
): ViewModel() {
    val isEditMode = MutableLiveData(false)
    val enableSave = MediatorLiveData<Boolean>()
    private val timeStamp = MutableLiveData(0L)
    val title = MutableLiveData("")
    val note = MutableLiveData("")
    var originalNote: NoteItemData? = null

    init {
        enableSave.addSource(title) { checkEnableSave() }
        enableSave.addSource(note) { checkEnableSave() }
    }
    private fun checkEnableSave() {
        enableSave.value = !title.value.isNullOrEmpty() && !note.value.isNullOrEmpty()
    }

    fun updateTimeStamp(newTimeStamp: Long) {
        timeStamp.value = newTimeStamp
        if (timeStamp.value != 0L) {
            isEditMode.value = true
        }
    }
    fun updateTitle(newTitle: String) {
        title.value = newTitle
    }
    fun updateNote(newNote: String) {
        note.value = newNote
    }

    fun addNote(onSaved: () -> Unit) {
        viewModelScope.launch {
            notesLocalStorage.addNote(
                NoteItemData(
                    title = title.value ?: "",
                    note = note.value ?: ""
                )
            ){
                onSaved()
            }
        }
    }

    fun editNote(onSaved: ()->Unit) {
        viewModelScope.launch {
            notesLocalStorage.editNote(
                timeStamp.value ?: 0L,
                NoteItemData(
                    title = title.value ?: "",
                    note = note.value ?: ""
                )
            ){
                onSaved()
            }
        }
    }

    fun getNoteByTimestamp(
        result: (NoteItemData?) -> Unit
    ){
        viewModelScope.launch {
            val savedNote = notesLocalStorage.getNoteByTimestamp(timeStamp.value ?: 0L).first()
            originalNote = savedNote
            timeStamp.value = savedNote?.timeStamp ?: 0L
            result(originalNote)
        }
    }

    fun removeNote(
        onSaved: () -> Unit
    ) {
        viewModelScope.launch {
            originalNote?.let {
                notesLocalStorage.removeNote(it) {
                    onSaved()
                }
            }
        }
    }
}