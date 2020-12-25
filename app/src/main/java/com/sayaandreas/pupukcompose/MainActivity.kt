package com.sayaandreas.pupukcompose

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.savedinstancestate.Saver
import androidx.compose.runtime.savedinstancestate.rememberSavedInstanceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.ui.tooling.preview.Preview
import com.sayaandreas.pupukcompose.model.Movie
import com.sayaandreas.pupukcompose.ui.PupukComposeTheme
import com.sayaandreas.pupukcompose.ui.screens.HomeScreen
import com.sayaandreas.pupukcompose.utils.loadPicture

class MainActivity : AppCompatActivity() {
    private val mainViewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MultiBottomNavApp(mainViewModel)
        }
    }
}

@Composable
fun MultiBottomNavApp(mainViewModel: MainViewModel) {
    BottomNavApp {
        MultiNavTabContent(screen = it, mainViewModel)
    }
}

@Composable
fun MultiNavTabContent(screen: Screen, mainViewModel: MainViewModel) {
    val homeNavState =
        rememberSavedInstanceState(saver = NavStateSaver()) { mutableStateOf(Bundle()) }
    when (screen) {
        Screen.Home -> HomeTab(homeNavState, mainViewModel)
        Screen.Explore -> Explore()
        Screen.Favorite -> Favorite()
        else -> Profile()
    }
}

@Composable
fun HomeTab(navState: MutableState<Bundle>, mainViewModel: MainViewModel) {
    val navController = rememberNavController()

    onCommit {
        val callback = NavController.OnDestinationChangedListener { controller, _, _ ->
            navState.value = controller.saveState() ?: Bundle()
        }
        navController.addOnDestinationChangedListener(callback)
        navController.restoreState(navState.value)

        onDispose {
            navController.removeOnDestinationChangedListener(callback)
            navController.enableOnBackPressed(false)
        }
    }

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) { HomeScreen(navController, mainViewModel) }
        composable(
            Screen.MovieDetail.route + "/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            backStackEntry.arguments?.getInt("id")?.let {
                MovieDetail(
                    it,
                    mainViewModel
                )
            }
        }
    }
}

@Composable
fun MovieDetail(id: Int, mainViewModel: MainViewModel) {
    val movie = mainViewModel.getMovieDetail(id)
    Column(modifier = Modifier.fillMaxSize().then(Modifier.padding(8.dp))) {
        movie?.let { Text(text = it.title) }
    }
}

@Composable
fun Explore() {
    Column(modifier = Modifier.fillMaxSize().then(Modifier.padding(8.dp))) {
        Text(text = Screen.Explore.route)
    }
}

@Composable
fun Favorite() {
    Column(modifier = Modifier.fillMaxSize().then(Modifier.padding(8.dp))) {
        Text(text = Screen.Favorite.route)
    }
}

@Composable
fun Profile() {
    Column(modifier = Modifier.fillMaxSize().then(Modifier.padding(8.dp))) {
        Text(text = Screen.Profile.route)
    }
}

@Composable
fun AsyncImage(url: String) {
    val image = loadPicture(url, defaultImage = R.drawable.ic_launcher_background).value
    image?.let { img ->
        Image(
            bitmap = img.asImageBitmap(),
            modifier = Modifier.fillMaxWidth().preferredHeight(250.dp),
            contentScale = ContentScale.Inside
        )
    }
}

@Composable
fun BottomNavApp(
    bodyContent: @Composable (Screen) -> Unit
) {
    var currentTab by rememberSavedInstanceState(saver = ScreenSaver()) {
        mutableStateOf(
            Screen.Home
        )
    }

    PupukComposeTheme {
        Surface(color = MaterialTheme.colors.background) {
            Scaffold(
                bodyContent = {
                    bodyContent(currentTab)
                },
                bottomBar = {
                    BottomNavigation {
                        BottomNavigationItem(
                            icon = { Screen.Home.icon?.let { Icon(it) } },
                            label = { Text(Screen.Home.route) },
                            selected = currentTab == Screen.Home,
                            onClick = { currentTab = Screen.Home }
                        )
                        BottomNavigationItem(
                            icon = { Screen.Explore.icon?.let { Icon(it) } },
                            label = { Text(Screen.Explore.route) },
                            selected = currentTab == Screen.Explore,
                            onClick = { currentTab = Screen.Explore }
                        )
                        BottomNavigationItem(
                            icon = { Screen.Favorite.icon?.let { Icon(it) } },
                            label = { Text(Screen.Favorite.route) },
                            selected = currentTab == Screen.Favorite,
                            onClick = { currentTab = Screen.Favorite }
                        )
                        BottomNavigationItem(
                            icon = { Screen.Profile.icon?.let { Icon(it) } },
                            label = { Text(Screen.Profile.route) },
                            selected = currentTab == Screen.Profile,
                            onClick = { currentTab = Screen.Profile }
                        )
                    }
                }
            )
        }
    }
}

fun NavBackStackEntry.getRoute(): String {
    return arguments?.getString(KEY_ROUTE) ?: ""
}

sealed class Screen(val route: String, val icon: ImageVector? = null) {
    object Home : Screen("Home", Icons.Default.Home)
    object Explore : Screen("Explore", Icons.Default.Search)
    object Favorite : Screen("Favorite", Icons.Default.Favorite)
    object Profile : Screen("Profile", Icons.Default.AccountCircle)
    object MovieDetail : Screen("MovieDetail")

    fun saveState(): Bundle {
        return bundleOf(KEY_SCREEN to route)
    }

    companion object {
        fun restoreState(bundle: Bundle): Screen {
            return when (bundle.getString(KEY_SCREEN, Profile.route)) {
                Home.route -> Home
                MovieDetail.route -> MovieDetail
                Explore.route -> Explore
                Favorite.route -> Favorite
                Profile.route -> Profile
                else -> Home
            }
        }

        const val KEY_SCREEN = "route"
    }
}

fun ScreenSaver(
): Saver<MutableState<Screen>, *> = Saver(
    save = { it.value.saveState() },
    restore = { mutableStateOf(Screen.restoreState(it)) }
)

fun NavStateSaver(): Saver<MutableState<Bundle>, out Any> = Saver(
    save = { it.value },
    restore = { mutableStateOf(it) }
)

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PupukComposeTheme {
        AsyncImage("https://fakestoreapi.com/img/71pWzhdJNwL._AC_UL640_QL65_ML3_.jpg")
    }
}