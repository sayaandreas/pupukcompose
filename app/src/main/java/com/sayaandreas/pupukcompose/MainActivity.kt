package com.sayaandreas.pupukcompose

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
            Routes(mainViewModel)
        }
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
    selectedProduct: (product: Product) -> Unit
) {
    Row(modifier = Modifier.clickable(onClick = { selectedProduct(product) })) {
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
fun HomeScreen(mainViewModel: MainViewModel, selectedProduct: (product: Product) -> Unit) {
    val items: List<Product> by mainViewModel.productList.observeAsState(listOf())
    LazyColumn {
        items(items) {
            Product(it, selectedProduct)
        }
    }
}

@Composable
fun DetailScreen(mainViewModel: MainViewModel, id: Int) {
    val product = mainViewModel.getProductDetail(id)
    product?.let { Text(text = it.title) }
}

sealed class Screen(val title: String) {
    object HomeScreen : Screen("HomeScreen")
    object DetailScreen : Screen("DetailScreen")
}

@Composable
fun Routes(mainViewModel: MainViewModel) {
    val navController = rememberNavController()
    PupukComposeTheme {
        Surface(color = MaterialTheme.colors.background) {
            NavHost(
                navController = navController,
                startDestination = Screen.HomeScreen.title
            ) {
                composable(Screen.HomeScreen.title) {
                    HomeScreen(mainViewModel,
                        selectedProduct = {
                            navController.navigate(Screen.DetailScreen.title + "/${it.id}")
                        }
                    )
                }
                composable(
                    Screen.DetailScreen.title + "/{id}",
                    arguments = listOf(navArgument("id") { type = NavType.IntType })
                ) { backStackEntry ->
                    backStackEntry.arguments?.getInt("id")?.let {
                        DetailScreen(
                            mainViewModel,
                            it
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    PupukComposeTheme {
        AsyncImage("https://fakestoreapi.com/img/71pWzhdJNwL._AC_UL640_QL65_ML3_.jpg")
    }
}