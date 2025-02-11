package dhandev.android.oknoted.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dhandev.android.oknoted.data.NoteItemData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.json.Json

private val Context.notesDataStore: DataStore<Preferences> by preferencesDataStore(name = "notes")

class NotesLocalStorage(
    context: Context
) : BaseDataStorePreference() {
    override val dataStore = context.notesDataStore
    private val NOTES_KEY = stringPreferencesKey("notes_key")
    suspend fun saveNotes(notes: List<NoteItemData>, onSaved: () -> Unit = {}) {
        val notesJson = Json.encodeToString(notes)
        savePreference(NOTES_KEY, notesJson)
        onSaved()
    }

    fun getNotes(): Flow<List<NoteItemData>> = getPreference(NOTES_KEY, "[]").map { notesJson ->
        Json.decodeFromString(notesJson)
    }

    suspend fun addNote(
        newNote: NoteItemData,
        onSaved: () -> Unit = {}
    ) {
        val currentNotes = getNotes().first()
        val updatedNotes = currentNotes.toMutableList().apply { add(newNote) }
        saveNotes(updatedNotes, onSaved)
    }

    suspend fun removeNote(
        note: NoteItemData,
        onSaved: () -> Unit
    ) {
        val currentNotes = getNotes().first()
        val updatedNotes = currentNotes.toMutableList().apply { remove(note) }
        saveNotes(updatedNotes, onSaved)
    }

    suspend fun editNote(
        timestamp: Long,
        newNote: NoteItemData,
        onSaved: () -> Unit
    ) {
        val currentNotes = getNotes().first()
        val updatedNotes = currentNotes.toMutableList().apply {
            val index = indexOf(this.find { it.timeStamp == timestamp })
            if (index != -1) set(index, newNote)
        }
        saveNotes(updatedNotes, onSaved)
    }

    fun getNoteByTimestamp(timestamp: Long): Flow<NoteItemData?> = getNotes().map { notes ->
        notes.find { it.timeStamp == timestamp }
    }
}