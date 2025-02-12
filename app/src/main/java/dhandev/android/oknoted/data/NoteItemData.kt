package dhandev.android.oknoted.data

import kotlinx.serialization.Serializable

@Serializable
data class NoteItemData(
    val timeStamp: Long = System.currentTimeMillis(),
    val title: String,
    val note: String
)

fun dummyNoteList(isEmpty: Boolean = false) =
    if (isEmpty) emptyList()
    else listOf(
            NoteItemData(title = "Grocery List", note = "Milk, Eggs, Bread, Butter"),
            NoteItemData(
                title = "Work Tasks",
                note = "Finish report by EOD, Schedule meeting with the marketing team, Email client about the project updates, " +
                        "Review the budget for Q4, Prepare presentation for the board meeting, Update the project timeline, " +
                        "Coordinate with the design team for new assets, Follow up on pending approvals."
            ),
            NoteItemData(title = "Fitness Goals", note = "Run 5k, Do yoga, Lift weights"),
            NoteItemData(
                title = "Book Recommendations",
                note = "Atomic Habits by James Clear - a great book on building good habits and breaking bad ones. " +
                        "Sapiens by Yuval Noah Harari - a fascinating exploration of human history. " +
                        "Dune by Frank Herbert - a classic science fiction novel with deep world-building and political intrigue. " +
                        "The Alchemist by Paulo Coelho - a motivational story about following your dreams. " +
                        "Thinking, Fast and Slow by Daniel Kahneman - a deep dive into how our minds work."
            ),
            NoteItemData(title = "Weekend Plans", note = "Visit museum, Dinner with friends, Watch movie")
        )
