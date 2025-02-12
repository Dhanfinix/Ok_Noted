package dhandev.android.oknoted.ui_compose.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults.exitUntilCollapsedScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dhandev.android.oknoted.R
import dhandev.android.oknoted.data.NoteItemData
import dhandev.android.oknoted.data.dummyNoteList
import dhandev.android.oknoted.ui_compose.Destinations
import dhandev.android.oknoted.ui_compose.theme.OkNotedTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainComposeViewModel = hiltViewModel(),
    navigateToDetail: (NoteItemData?)->Unit = {}
) {
    val isPreview = LocalInspectionMode.current
    val notes by viewModel.notes.collectAsState()
    val scrollBehavior = exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = { Text(stringResource(Destinations.Main().title)) },
                scrollBehavior = scrollBehavior
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navigateToDetail(null) }
            ) {
                Icon(
                    painter = painterResource(R.drawable.baseline_add_24),
                    contentDescription = stringResource(R.string.add)
                )
            }
        }
    ) {
        val usedNote = if (isPreview)
            dummyNoteList() else notes
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            itemsIndexed(usedNote) {index, note->
                NoteItemComp(
                    modifier = Modifier
                        .padding(
                            top = if (index == 0) 8.dp else 0.dp,
                            bottom = if (index == usedNote.lastIndex) 8.dp else 0.dp
                        ),
                    noteItemData = note
                ){
                    navigateToDetail(note)
                }
            }
        }
        if (usedNote.isEmpty()){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(R.string.empty_notes),
                    style = MaterialTheme.typography.headlineMedium
                )
                Text(
                    text = stringResource(R.string.add_new_note_instruction),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}


@Preview
@Composable
private fun MainScreenPreview() {
    OkNotedTheme {
        MainScreen(viewModel = MainComposeViewModel(null))
    }
}