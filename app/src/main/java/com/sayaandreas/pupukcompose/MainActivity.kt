package com.sayaandreas.pupukcompose

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.preferredHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.unit.dp
import androidx.ui.tooling.preview.Preview
import com.sayaandreas.pupukcompose.model.Product
import com.sayaandreas.pupukcompose.ui.PupukComposeTheme
import com.sayaandreas.pupukcompose.utils.loadPicture

class MainActivity : AppCompatActivity() {
    private val mainViewModel by viewModels<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PupukComposeTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    ProductList(
                        mainViewModel
                    )
                }
            }
        }
    }
}

@Composable
fun AsyncImage(url: String) {
    val image = loadPicture(url, defaultImage = R.drawable.ic_launcher_background).value
    image?.let { img ->
        Image(
            bitmap = img.asImageBitmap(),
            modifier = Modifier.fillMaxWidth().preferredHeight(300.dp),
            contentScale = ContentScale.Crop
        )
    }
}

@Composable
fun Product(
    product: Product
) {
    AsyncImage(product.image)
    Text(text = product.title)
}

@Composable
fun ProductList(mainViewModel: MainViewModel) {
    val items: List<Product> by mainViewModel.productList.observeAsState(listOf())
    LazyColumn {
        items(items) {
            Product(it)
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