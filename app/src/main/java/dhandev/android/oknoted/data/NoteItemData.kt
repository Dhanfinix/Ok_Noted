package dhandev.android.oknoted.data

import kotlinx.serialization.Serializable

@Serializable
data class NoteItemData(
    val title: String,
    val note: String
)
