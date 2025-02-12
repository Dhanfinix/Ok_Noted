package dhandev.android.oknoted.ui_compose.detail

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults.exitUntilCollapsedScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dhandev.android.oknoted.R
import dhandev.android.oknoted.data.NoteItemData
import dhandev.android.oknoted.ui_compose.Destinations
import dhandev.android.oknoted.ui_compose.theme.OkNotedTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailComposeViewModel = hiltViewModel(),
    noteItemData: NoteItemData? = null,
    onBack: ()->Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val scrollBehavior = exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    LaunchedEffect(Unit) {
        viewModel.updateTimeStamp(noteItemData?.timeStamp ?: 0L)
        viewModel.getNoteByTimestamp()
    }
    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(
                title = { Text(stringResource(Destinations.Detail().title)) },
                navigationIcon = {
                    IconButton(
                        onClick = {onBackClicked(viewModel, onBack)}
                    ) {
                        Icon(
                            painterResource(R.drawable.baseline_arrow_back_24),
                            stringResource(R.string.back)
                        )
                    }
                },
                actions = {
                    if (uiState.isEditMode){
                        IconButton(
                            onClick = {
                                viewModel.setAlertDialog(
                                    AlertDialogState(
                                        title = "Remove Note?",
                                        message = "Do you really want to delete this note from your notes list?",
                                        delegate = object : DialogDelegate{
                                            override fun onConfirm() {
                                                viewModel.removeNote {
                                                    onBack()
                                                }
                                            }
                                            override fun onCancel() {
                                                viewModel.setAlertDialog(null)
                                            }
                                        }
                                    )
                                )
                            }
                        ) {
                            Icon(
                                painterResource(R.drawable.baseline_delete_24),
                                stringResource(R.string.delete)
                            )
                        }
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 52.dp)
            ) {
                item {
                    val titleValue = if (LocalInspectionMode.current)
                        noteItemData?.title ?: ""
                    else uiState.title
                    BasicTextField(
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                            .fillMaxWidth(),
                        maxLines = 2,
                        value = titleValue,
                        onValueChange = {newValue->
                            viewModel.updateTitle(newValue)
                        },
                        textStyle = MaterialTheme.typography.headlineSmall.copy(
                            color = MaterialTheme.colorScheme.onSurface
                        ),
                        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary)
                    ){inputField->
                        if (titleValue.isEmpty())
                            Text(
                                text = stringResource(R.string.note_title),
                                style = MaterialTheme.typography.headlineSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        inputField()
                    }
                }
                item {
                    val noteValue = if (LocalInspectionMode.current)
                        noteItemData?.note ?: ""
                    else uiState.note
                    BasicTextField(
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                            .fillMaxSize(),
                        value = noteValue,
                        onValueChange = {newValue->
                            viewModel.updateNote(newValue)
                        },
                        textStyle = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onSurface
                        ),
                        cursorBrush = SolidColor(MaterialTheme.colorScheme.primary)
                    ){inputField->
                        if (noteValue.isEmpty())
                            Text(
                                text = stringResource(R.string.note_content),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        inputField()
                    }
                }
            }

            val saveEnabled by viewModel.enableSave.collectAsState(false)
            Button(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                enabled = saveEnabled,
                onClick = {
                    viewModel.addNote {
                        onBack()
                    }
                }
            ) {
                Text(stringResource(R.string.save))
            }
        }
        if (uiState.alertDialog != null){
            AlertDialog(
                onDismissRequest = { viewModel.setAlertDialog(null) },
                title = { Text(uiState.alertDialog?.title ?: "") },
                text = { Text(uiState.alertDialog?.message ?: "") },
                confirmButton = {
                    TextButton(
                        onClick = {
                            viewModel.setAlertDialog(null)
                            uiState.alertDialog?.delegate?.onConfirm()
                        }
                    ) { Text(uiState.alertDialog?.confirmText ?: "") }
                },
                dismissButton = {
                    TextButton(
                        onClick = { uiState.alertDialog?.delegate?.onCancel() }
                    ) { Text(uiState.alertDialog?.cancelText ?: "") }
                }
            )
        }
    }

    //for system back
    BackHandler {
        onBackClicked(viewModel){
            onBack()
        }
    }
}

private fun onBackClicked(
    viewModel: DetailComposeViewModel,
    onBack: () -> Unit
) {
    if (viewModel.isBackEnable())
        onBack()
    else
        viewModel.setAlertDialog(
            AlertDialogState(
                title = "Discard Changes?",
                message = "Are you sure you want to discard your note changes?",
                delegate = object : DialogDelegate{
                    override fun onConfirm() {
                        onBack()
                    }
                    override fun onCancel() {
                        viewModel.setAlertDialog(null)
                    }
                }
            )
        )
}

@Preview
@Composable
private fun DetailScreenPreview(
    @PreviewParameter(DetailScreenProvider::class)
    noteItemData: NoteItemData?
) {
    OkNotedTheme {
        DetailScreen(
            viewModel = DetailComposeViewModel(null),
            noteItemData = noteItemData
        ) { }
    }
}

private class DetailScreenProvider: PreviewParameterProvider<NoteItemData?>{
    override val values = sequenceOf(
        null,
        NoteItemData(
            title = "Work Tasks",
            note = "Finish report by EOD, Schedule meeting with the marketing team, Email client about the project updates, " +
                    "Review the budget for Q4, Prepare presentation for the board meeting, Update the project timeline, " +
                    "Coordinate with the design team for new assets, Follow up on pending approvals."
        )
    )

}