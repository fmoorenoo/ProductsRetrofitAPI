package ies.haria.testcallapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import androidx.compose.ui.platform.LocalContext
import ies.haria.testcallapi.data.db.ProductDatabase
import ies.haria.testcallapi.data.ProductDBViewModel
import ies.haria.testcallapi.data.ProductViewModel
import ies.haria.testcallapi.ui.theme.TestCallApiTheme

enum class ScreenList {
    ProductList,
    FavoriteList,
    SearchScreen
}

@Suppress("UNCHECKED_CAST")
class MainActivity : ComponentActivity() {
    val db by lazy {
        Room.databaseBuilder(
            context = applicationContext,
            klass = ProductDatabase::class.java,
            name = "product.db"
        ).build()
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TestCallApiTheme(dynamicColor = false) {
                val navController: NavHostController = rememberNavController()
                Scaffold(
                    bottomBar = {
                        BottomAppBar(
                            actions = {
                                BottomBarItems(navController)
                            }
                        )
                    }
                ) { innerPadding ->
                    val databaseViewModel: ProductDBViewModel by viewModels<ProductDBViewModel>(
                        factoryProducer = {
                            object : ViewModelProvider.Factory {
                                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                                    return ProductDBViewModel(db.dao) as T
                                }
                            }
                        }
                    )
                    val productViewModel: ProductViewModel = viewModel()
                    val context = LocalContext.current
                    NavHost(
                        navController = navController,
                        startDestination = ScreenList.ProductList.name,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        composable(route = ScreenList.ProductList.name) {
                            ProductListScreen(productViewModel, databaseViewModel, context)
                        }
                        composable(route = ScreenList.FavoriteList.name) {
                            FavoriteListScreen(databaseViewModel, context)
                        }
                        composable(route = ScreenList.SearchScreen.name) {
                            SearchScreen(productViewModel, databaseViewModel, context)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomBarItems(navController: NavController) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(start = 30.dp, end = 30.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = { navController.navigate(ScreenList.ProductList.name) },
        ) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                imageVector = Icons.Filled.Home,
                contentDescription = "Lista Productos"
            )
        }
        IconButton(
            modifier = Modifier.padding(start = 30.dp),
            onClick = { navController.navigate(ScreenList.FavoriteList.name) },
        ) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                imageVector = Icons.Filled.Favorite,
                contentDescription = "Favoritos Productos"
            )
        }
        IconButton(
            modifier = Modifier.padding(start = 30.dp),
            onClick = { navController.navigate(ScreenList.SearchScreen.name) },
        ) {
            Icon(
                modifier = Modifier.fillMaxSize(),
                imageVector = Icons.Filled.Search,
                contentDescription = "Búsqueda de Productos"
            )
        }
    }
}