package com.example.littlelemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.littlelemon.database.MenuItemEntity
import com.example.littlelemon.viewmodel.MenuViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: MenuViewModel = viewModel()) {
    val menuItems by viewModel.menuItems.collectAsState(initial = emptyList())

    LaunchedEffect(Unit) {
        viewModel.refreshMenu() // Fetch new data on screen load
    }

    Column {
        CenterAlignedTopAppBar(
            title = { Text("Little Lemon Menu") },
            modifier = Modifier.fillMaxWidth()
        )

        LazyColumn {
            items(menuItems) { item ->
                MenuItemCard(item)
            }
        }
    }
}

@Composable
fun MenuItemCard(item: MenuItemEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            Image(
                painter = rememberAsyncImagePainter(item.image),
                contentDescription = item.title,
                modifier = Modifier.size(80.dp)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                Text(item.title, style = MaterialTheme.typography.titleMedium)
                Text(item.description, style = MaterialTheme.typography.bodyMedium)
                Text("Price: $${item.price}", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}