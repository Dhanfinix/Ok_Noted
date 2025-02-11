package dhandev.android.oknoted.data

import kotlinx.serialization.Serializable

@Serializable
data class NoteItemData(
    val timeStamp: Long = System.currentTimeMillis(),
    val title: String,
    val note: String
)
