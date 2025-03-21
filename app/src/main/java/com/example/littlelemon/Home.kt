package com.example.littlelemon

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.littlelemon.ui.theme.LittleLemonTheme

@OptIn(ExperimentalCoilApi::class)
@SuppressLint("MutableCollectionMutableState")
@Composable
fun HomeScreen(navController: NavHostController, allItems: List<MenuItemRoom>) {
    Column(modifier = Modifier.fillMaxHeight()) {
        var search by remember {
            mutableStateOf("")
        }

        // Initialize categories list with standard categories
        val categories = listOf("Starters", "Mains", "Desserts", "Drinks")

        // Create a set of unique categories from menu items
        val uniqueCategories = allItems.map { it.category.replaceFirstChar { it.uppercase() } }.toSet().toList()

        // Combine standard categories with unique ones from data
        val items = (categories + uniqueCategories).distinct()

        var filterItems by remember {
            mutableStateOf(allItems)
        }

        var filter by remember {
            mutableStateOf("")
        }

        // Header with logo and profile
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(75.dp)
                .padding(start = 12.dp, end = 12.dp),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.End
        ) {
            Image(
                modifier = Modifier
                    .height(75.dp)
                    .fillMaxWidth(0.5f),
                painter = painterResource(id = R.drawable.logo),
                contentScale = ContentScale.Fit,
                contentDescription = "Logo"
            )
            Image(
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "Profile",
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .fillMaxWidth(0.6f)
                    .height(75.dp)
                    .padding(10.dp)
                    .wrapContentSize(Alignment.CenterEnd)
                    .clickable { navController.navigate(Profile.route) }
            )
        }

        // Hero section
        Box(
            modifier = Modifier
                .height(320.dp)
                .fillMaxWidth(1f)
                .background(color = Color(0xFF495E57))
        ) {
            Column(modifier = Modifier.padding(top = 10.dp, start = 10.dp)) {
                Text(
                    text = "Little Lemon",
                    fontSize = 48.sp,
                    color = Color(0xfff4ce14),
                    fontWeight = FontWeight.Bold
                )
                Row {
                    Column(
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier
                            .padding(end = 10.dp)
                            .fillMaxWidth(0.55f)
                    ) {
                        Text(
                            text = "Chicago",
                            modifier = Modifier.padding(bottom = 10.dp),
                            fontSize = 24.sp,
                            color = Color(0xffffffff)
                        )
                        Text(
                            text = "We are a family-owned Mediterranean restaurant, focused on traditional recipes served with a modern twist",
                            fontSize = 16.sp,
                            color = Color(0xffffffff)
                        )
                    }
                    Image(
                        modifier = Modifier
                            .padding(10.dp)
                            .fillMaxWidth(1f)
                            .height(150.dp)
                            .clip(RoundedCornerShape(10.dp)),
                        painter = painterResource(id = R.drawable.hero_image),
                        contentDescription = "Hero Image",
                        contentScale = ContentScale.FillWidth
                    )
                }

                // Search field
                OutlinedTextField(
                    modifier = Modifier
                        .padding(top = 10.dp, start = 20.dp, end = 20.dp)
                        .fillMaxWidth()
                        .height(50.dp),
                    value = search,
                    onValueChange = {
                        search = it
                        filterItems = if (filter.isEmpty()) {
                            // If no category filter, just filter by search text
                            allItems.filter { item ->
                                item.title.contains(search, ignoreCase = true)
                            }
                        } else {
                            // Filter by both search text and category
                            allItems.filter { item ->
                                item.title.contains(search, ignoreCase = true) &&
                                        item.category.equals(filter, ignoreCase = true)
                            }
                        }
                    },
                    placeholder = { Text("Enter search phrase", modifier = Modifier.padding(0.dp), fontSize = 16.sp) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search"
                        )
                    },
                    textStyle = TextStyle(fontSize = 16.sp),
                    shape = RoundedCornerShape(10.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = Color(0xffedefee),
                        unfocusedContainerColor = Color(0xffedefee)
                    )
                )
            }
        }

        // Category section header
        Text(
            "ORDER FOR DELIVERY!",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 30.dp, start = 10.dp, bottom = 5.dp),
            fontSize = 20.sp
        )

        // Category filters
        Row(
            modifier = Modifier
                .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 20.dp)
                .fillMaxWidth(1f)
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            for (item in items)
                Button(
                    onClick = {
                        if (filter == item) {
                            // Clear filter if already selected
                            filter = ""
                            filterItems = allItems.filter { menuItem ->
                                menuItem.title.contains(search, ignoreCase = true)
                            }
                        } else {
                            // Apply category filter
                            filter = item
                            filterItems = allItems.filter { menuItem ->
                                menuItem.category.equals(item, ignoreCase = true) &&
                                        menuItem.title.contains(search, ignoreCase = true)
                            }
                        }
                    },
                    contentPadding = PaddingValues(10.dp),
                    modifier = Modifier
                        .wrapContentSize(Alignment.Center)
                        .padding(end = 12.dp),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (filter != item) {
                            Color(0xffedefee)
                        } else {
                            Color(0xFF495E57)
                        }
                    )
                ) {
                    Text(
                        item,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = if (filter != item) {
                            Color(0xff495e57)
                        } else {
                            Color(0xffffffff)
                        }
                    )
                }
        }

        HorizontalDivider(
            thickness = 1.dp,
            color = Color(0xffedefee),
            modifier = Modifier.padding(horizontal = 10.dp)
        )

        // Menu items list
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            for (item in filterItems) {
                Box(
                    modifier = Modifier
                        .height(170.dp)
                        .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                        .fillMaxWidth()
                ) {
                    Column {
                        Text(item.title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(110.dp)
                                .padding(bottom = 15.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth(0.7f)
                                    .fillMaxHeight(1f)
                            ) {
                                Text(
                                    modifier = Modifier
                                        .height(60.dp)
                                        .padding(end = 5.dp, bottom = 5.dp),
                                    text = item.description,
                                    overflow = TextOverflow.Ellipsis,
                                    fontWeight = FontWeight.W400,
                                    fontSize = 16.sp,
                                )
                                Text(
                                    text = "$${item.price}",
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Medium,
                                    color = Color(0xff495e57),
                                    modifier = Modifier.padding(5.dp)
                                )
                            }
                            // Use a placeholder if image URL is invalid
                            Image(
                                modifier = Modifier
                                    .fillMaxWidth(1f)
                                    .padding(start = 10.dp),
                                painter = rememberImagePainter(item.image),
                                contentDescription = "Menu item image",
                                contentScale = ContentScale.FillWidth
                            )
                        }
                        HorizontalDivider(thickness = 1.dp, color = Color(0xffedefee))
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    LittleLemonTheme {
        HomeScreen(rememberNavController(), emptyList())
    }
}