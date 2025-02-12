package dhandev.android.oknoted.ui_compose

import dhandev.android.oknoted.R
import kotlinx.serialization.Serializable

sealed class Destinations {
    abstract val title: Int

    @Serializable
    data class Main(
        override val title: Int = R.string.app_name
    ): Destinations()

    @Serializable
    data class Detail(
        val noteItemDataJson: String? = null,
        override val title: Int =
            if (noteItemDataJson != null)
                R.string.note_detail
            else
                R.string.add
    ): Destinations()
}