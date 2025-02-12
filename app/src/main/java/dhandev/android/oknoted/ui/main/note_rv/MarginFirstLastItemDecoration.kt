package dhandev.android.oknoted.ui.main.note_rv

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * A {@link RecyclerView.ItemDecoration} that adds top margin to the first item and bottom margin to the last item in a RecyclerView.
 *
 * This class provides a simple way to visually separate the first and last items in a RecyclerView from the surrounding content.
 * It's particularly useful for lists where you want the top and bottom items to have some breathing room.
 * @param topMargin The margin to be applied to the top of the first item, in pixels.
 * @param bottomMargin The margin to be applied to the bottom of the last item, in pixels.
 */
class MarginFirstLastItemDecoration(
    private val topMargin: Int,
    private val bottomMargin: Int
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        val itemCount = parent.adapter?.itemCount ?: 0
        if (position == 0) {
            outRect.top = topMargin
        }
        if (position == itemCount-1){
            outRect.bottom = bottomMargin
        }
    }
}
