package com.example.littlelemon.database

import kotlinx.serialization.Serializable

@Serializable
data class MenuResponse(
    val menu: List<MenuItemEntity>
)