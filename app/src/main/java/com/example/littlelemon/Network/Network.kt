package com.example.littlelemon.network

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class MenuNetwork(
    @SerialName("menu") val menu: List<MenuItemNetwork>
)

@Serializable
data class MenuItemNetwork(
    val id: Int,
    val title: String,
    val description: String,
    val price: Double,
    val image: String,
    val category: String
)

object ApiService {
    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
            })
        }
    }

    private const val MENU_URL =
        "https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/menu.json"

    suspend fun fetchMenu(): List<MenuItemNetwork> {
        return try {
            client.get(MENU_URL) {
                contentType(ContentType.Application.Json)
            }.body<MenuNetwork>().menu
        } catch (e: Exception) {
            println("Error fetching menu: ${e.message}")
            emptyList()
        }
    }
}