package dhandev.android.oknoted.ui.main.note_rv

import dhandev.android.oknoted.data.NoteItemData

interface NoteItemDelegate {
    fun onClick(value: NoteItemData)
}