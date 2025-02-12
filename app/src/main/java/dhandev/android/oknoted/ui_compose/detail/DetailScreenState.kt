package dhandev.android.oknoted.ui_compose.detail

import dhandev.android.oknoted.data.NoteItemData

data class DetailScreenState(
    val isEditMode: Boolean = false,
    val enableSave: Boolean = false,
    val timeStamp: Long = 0L,
    val title: String = "",
    val note: String = "",
    val originalNote: NoteItemData? = null,
    val alertDialog: AlertDialogState? = null
)

data class AlertDialogState(
    val title: String,
    val message: String,
    val confirmText: String = "Yes",
    val cancelText: String = "No",
    val delegate: DialogDelegate
)
