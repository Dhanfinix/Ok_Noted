package dhandev.android.oknoted

import dhandev.android.oknoted.data.NoteItemData

interface NoteItemDelegate {
    fun onClick(value: NoteItemData)
}