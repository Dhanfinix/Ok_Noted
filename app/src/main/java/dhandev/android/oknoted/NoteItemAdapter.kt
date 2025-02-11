package dhandev.android.oknoted

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dhandev.android.oknoted.data.NoteItemData
import dhandev.android.oknoted.databinding.NoteItemHolderBinding

class NoteItemAdapter(
    private val myList: List<NoteItemData>
): RecyclerView.Adapter<NoteItemHolder>() {
    lateinit var delegate : NoteItemDelegate
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteItemHolder {
        val binding = NoteItemHolderBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return NoteItemHolder(binding, delegate)
    }

    override fun getItemCount() = myList.size

    override fun onBindViewHolder(holder: NoteItemHolder, position: Int) {
        holder.show(myList[position])
    }
}