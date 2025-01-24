package ies.haria.testcallapi

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ies.haria.testcallapi.data.ProductDBViewModel
import ies.haria.testcallapi.data.ProductViewModel

@Composable
fun SearchScreen(
    productViewModel: ProductViewModel,
    databaseViewModel: ProductDBViewModel,
    context: Context
) {
    val isLoading: Boolean by productViewModel.isLoading.observeAsState(initial = false)
    var searchString by remember { mutableStateOf("") }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            TextField(
                value = searchString,
                label = { Text("Buscar producto") },
                onValueChange = { searchString = it },
                trailingIcon = { Icon(Icons.Filled.Search, contentDescription = "Icono búsqueda") }
            )
            Button(
                onClick = { productViewModel.searchProducts(searchString) },
                modifier = Modifier.padding(start = 5.dp)
            ) {
                Text("Buscar")
            }
        }
        if (isLoading) {
            LoadingScreen()
        } else {
            ProductListView(
                "Productos encontrados",
                productViewModel.productSearchList.value!!,
                databaseViewModel,
                context
            )
        }
    }
}