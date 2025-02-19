package dhandev.android.oknoted.ui_compose

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import dhandev.android.oknoted.data.NoteItemData
import dhandev.android.oknoted.ui_compose.detail.DetailScreen
import dhandev.android.oknoted.ui_compose.main.MainScreen
import kotlinx.serialization.json.Json

@Composable
fun NavigationHost(
    modifier: Modifier = Modifier
) {
    val navController = rememberNavController()
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Destinations.Main()
    ){
        composable<Destinations.Main> {
            MainScreen{data->
                val json = data?.let { Json.encodeToString(it) }
                navController.navigate(Destinations.Detail(json))
            }
        }
        composable<Destinations.Detail> {backStackEntry->
            val json = backStackEntry.toRoute<Destinations.Detail>().noteItemDataJson
            val title = backStackEntry.toRoute<Destinations.Detail>().title
            val noteItemData = json?.let { Json.decodeFromString<NoteItemData>(it) }
            DetailScreen(
                noteItemData = noteItemData,
                title = title
            ){
                navController.navigateUp()
            }
        }
    }
}