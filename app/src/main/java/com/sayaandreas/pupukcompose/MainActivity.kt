package com.sayaandreas.pupukcompose

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.savedinstancestate.Saver
import androidx.compose.runtime.savedinstancestate.rememberSavedInstanceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
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
import com.sayaandreas.pupukcompose.model.Product
import com.sayaandreas.pupukcompose.ui.PupukComposeTheme
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
    val gardenNavState =
        rememberSavedInstanceState(saver = NavStateSaver()) { mutableStateOf(Bundle()) }
    when (screen) {
        Screen.Garden -> GardenTab(gardenNavState, mainViewModel)
        Screen.Explore -> Explore()
        Screen.Reminders -> Reminders()
        else -> Profile()
    }
}

@Composable
fun GardenTab(navState: MutableState<Bundle>, mainViewModel: MainViewModel) {
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
        startDestination = Screen.Garden.route
    ) {
        composable(Screen.Garden.route) { Garden(navController, mainViewModel) }
        composable(Screen.Plant.route) { Plant(it.arguments?.get("plantId") as String) }
    }
}

@Composable
fun Garden(navController: NavController, mainViewModel: MainViewModel) {
    val items: List<Product> by mainViewModel.productList.observeAsState(listOf())
    LazyColumn {
        items(items) { product ->
            Product(
                product,
                onClick = {
                    navController.navigate(
                        Screen.Plant.routeWithPhrase(product.title)
                    )
                })
        }
    }
}

@Composable
fun Plant(plantId: String) {
    Column(modifier = Modifier.fillMaxSize().then(Modifier.padding(8.dp))) {
        Text(text = plantId)
    }
}

@Composable
fun Explore() {
    Column(modifier = Modifier.fillMaxSize().then(Modifier.padding(8.dp))) {
        Text(text = Screen.Explore.route)
    }
}

@Composable
fun Reminders() {
    Column(modifier = Modifier.fillMaxSize().then(Modifier.padding(8.dp))) {
        Text(text = Screen.Reminders.route)
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
fun Product(
    product: Product,
    onClick: () -> Unit
) {
    Row(modifier = Modifier.clickable(onClick = { onClick() })) {
        Card(
            Modifier.weight(1f).padding(start = 16.dp, end = 8.dp, top = 8.dp, bottom = 8.dp),
            elevation = 4.dp
        ) {
            Column {
                AsyncImage(product.image)
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(text = product.title, fontSize = 14.sp, color = Color.Gray)
                    Text(text = "USD ${product.price}", fontWeight = FontWeight.Bold)
                }
            }
        }
        Card(
            Modifier.weight(1f).padding(start = 8.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
            elevation = 4.dp
        ) {
            Column {
                AsyncImage(product.image)
                Column(modifier = Modifier.padding(8.dp)) {
                    Text(text = product.title, fontSize = 14.sp, color = Color.Gray)
                    Text(text = "USD ${product.price}", fontWeight = FontWeight.Bold)
                }
            }
        }
    }

}

@Composable
fun BottomNavApp(
    bodyContent: @Composable (Screen) -> Unit
) {
    var currentTab by rememberSavedInstanceState(saver = ScreenSaver()) {
        mutableStateOf(
            Screen.Garden
        )
    }

    PupukComposeTheme {
        Surface(color = MaterialTheme.colors.background) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(currentTab.route)
                        }
                    )
                }, bodyContent = {
                    bodyContent(currentTab)
                },
                bottomBar = {
                    BottomNavigation {
                        BottomNavigationItem(
                            icon = { Icon(Icons.Default.AccountCircle) },
                            label = { Text("Garden") },
                            selected = currentTab == Screen.Garden,
                            onClick = { currentTab = Screen.Garden }
                        )
                        BottomNavigationItem(
                            icon = { Icon(Icons.Default.ShoppingCart) },
                            label = { Text("Explore") },
                            selected = currentTab == Screen.Explore,
                            onClick = { currentTab = Screen.Explore }
                        )
                        BottomNavigationItem(
                            icon = { Icon(Icons.Default.List) },
                            label = { Text("Reminders") },
                            selected = currentTab == Screen.Reminders,
                            onClick = { currentTab = Screen.Reminders }
                        )
                        BottomNavigationItem(
                            icon = { Icon(Icons.Default.List) },
                            label = { Text("Profile") },
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

sealed class Screen(val route: String) {
    object Garden : Screen("Garden")
    object Explore : Screen("Explore")
    object Reminders : Screen("Reminders")
    object Profile : Screen("Profile")
    object Plant : Screen("Plant/?plantId={plantId}") {
        fun routeWithPhrase(plantId: String): String = route.replace("{plantId}", plantId)
    }

    fun saveState(): Bundle {
        return bundleOf(KEY_SCREEN to route)
    }

    companion object {
        fun restoreState(bundle: Bundle): Screen {
            return when (bundle.getString(KEY_SCREEN, Profile.route)) {
                Garden.route -> Garden
                Plant.route -> Plant
                Explore.route -> Explore
                Reminders.route -> Reminders
                Profile.route -> Profile
                else -> Garden
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