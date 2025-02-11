package dhandev.android.oknoted

import androidx.recyclerview.widget.RecyclerView
import dhandev.android.oknoted.data.NoteItemData
import dhandev.android.oknoted.databinding.NoteItemHolderBinding

class NoteItemHolder(
    private val viewBinding: NoteItemHolderBinding,
    private val delegate: NoteItemDelegate
) : RecyclerView.ViewHolder(viewBinding.root) {
    fun show(value: NoteItemData){
        viewBinding.apply {
            tvNoteTitle.text = value.title
            tvNote.text = value.note
            root.setOnClickListener {
                delegate.onClick(value)
            }
        }
    }
}