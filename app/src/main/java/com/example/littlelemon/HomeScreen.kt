package com.example.littlelemon

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.littlelemon.database.MenuItemEntity
import com.example.littlelemon.ui.theme.LemonYellow
import com.example.littlelemon.ui.theme.OliveGreen
import com.example.littlelemon.viewmodel.MenuViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, viewModel: MenuViewModel) {
    val menuItems by viewModel.menuItems.collectAsState(initial = emptyList())
    var searchQuery by remember { mutableStateOf("") }

    LaunchedEffect(Unit) {
        viewModel.refreshMenu() // Fetch new data on screen load
    }

    Column {
        // Top Bar with Logo and Profile Icon
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(40.dp)) // For alignment

            // Logo in center
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Little Lemon Logo",
                modifier = Modifier.height(50.dp)
            )

            // Profile icon that navigates to profile screen
            IconButton(
                onClick = { navController.navigate(Profile.route) },
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
            ) {
                // This would be a profile image - using a placeholder
                Image(
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "Profile",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }

        // Hero Section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(OliveGreen)
                .padding(16.dp)
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                // Text Column
                Column(modifier = Modifier.weight(0.6f)) {
                    Text(
                        text = "Little Lemon",
                        color = LemonYellow,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Text(
                        text = "Chicago",
                        color = Color.White,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "We are a family owned Mediterranean restaurant, focused on traditional recipes served with a modern twist.",
                        color = Color.White,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(bottom = 12.dp)
                    )

                    // Search Bar
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        placeholder = { Text("Enter search phrase") },
                        leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                            .background(Color.White, RoundedCornerShape(8.dp)),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            containerColor = Color.White,
                            focusedBorderColor = OliveGreen,
                            unfocusedBorderColor = Color.LightGray
                        ),
                        singleLine = true
                    )
                }

                // Image Column (food picture)
                Box(
                    modifier = Modifier
                        .weight(0.4f)
                        .padding(start = 12.dp)
                        .height(150.dp)
                        .clip(RoundedCornerShape(8.dp))
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.hero_image),
                        contentDescription = "Food Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }

        // ORDER FOR DELIVERY Text
        Text(
            text = "ORDER FOR DELIVERY!",
            modifier = Modifier.padding(16.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )

        // Category Filters
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CategoryChip("Starters")
            CategoryChip("Mains")
            CategoryChip("Desserts")
            CategoryChip("Drinks")
        }

        // Menu Items
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            items(filterMenuItems(menuItems, searchQuery)) { item ->
                MenuItemCard(item)
                Divider(modifier = Modifier.padding(vertical = 8.dp))
            }
        }
    }
}

@Composable
fun CategoryChip(category: String) {
    Surface(
        modifier = Modifier.height(32.dp),
        shape = RoundedCornerShape(16.dp),
        color = Color.LightGray
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) {
            Text(
                text = category,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun MenuItemCard(item: MenuItemEntity) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        // Item Description
        Column(
            modifier = Modifier
                .weight(0.7f)
                .padding(end = 8.dp)
        ) {
            Text(
                text = item.title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            Text(
                text = item.description,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                fontSize = 14.sp,
                color = Color.DarkGray,
                modifier = Modifier.padding(vertical = 4.dp)
            )

            Text(
                text = "$${String.format("%.2f", item.price)}",
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            )
        }

        // Item Image
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(4.dp))
        ) {
            Image(
                painter = rememberAsyncImagePainter(item.image),
                contentDescription = item.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}

// Function to filter menu items based on search query
fun filterMenuItems(items: List<MenuItemEntity>, query: String): List<MenuItemEntity> {
    if (query.isEmpty()) return items
    return items.filter {
        it.title.contains(query, ignoreCase = true) ||
                it.description.contains(query, ignoreCase = true)
    }
}