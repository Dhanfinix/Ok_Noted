package dhandev.android.oknoted.ui_compose.main

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import dhandev.android.oknoted.data.NoteItemData
import dhandev.android.oknoted.ui_compose.theme.OkNotedTheme

@Composable
fun NoteItemComp(
    modifier: Modifier = Modifier,
    noteItemData: NoteItemData,
    onClick: ()->Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors().copy(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(1.dp, Color.Gray),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 16.dp)
        ) {
            Text(
                text = noteItemData.title,
                maxLines = 1,
                style = MaterialTheme.typography.headlineSmall,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = noteItemData.note,
                maxLines = 3,
                style = MaterialTheme.typography.bodyLarge,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Preview
@Composable
private fun NoteItemPreview(
    @PreviewParameter(NoteItemPreviewProvider::class)
    noteItemData: NoteItemData
) {
    OkNotedTheme {
        NoteItemComp(
            noteItemData = noteItemData
        ) { }
    }
}

private class NoteItemPreviewProvider: PreviewParameterProvider<NoteItemData> {
    override val values = sequenceOf(
        NoteItemData(
            title = "This is my note app",
            note = "This is my note app"
        ),
        NoteItemData(
            title = "Longer text that can trigger overflow text with ellipsis",
            note = "This is also a very long text that can reach more " +
                    "than three lines, so it should be ellipsized. " +
                    "Hmm that's not enough? here another text just in case"
        )
    )

}