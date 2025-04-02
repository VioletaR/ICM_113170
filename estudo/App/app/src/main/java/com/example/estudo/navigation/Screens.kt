package com.example.estudo.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Send
import androidx.compose.material.icons.rounded.Clear
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material.icons.rounded.Person
import androidx.compose.ui.graphics.vector.ImageVector


enum class Screens(val title: String, val icon: ImageVector) {
    Login("Login", Icons.Rounded.Clear),
    ChatBot("ChatBot", Icons.AutoMirrored.Rounded.Send),
    Map("Map", Icons.Rounded.LocationOn),
    Health("Health", Icons.Rounded.Favorite),
    Profile("Profile", Icons.Rounded.Person);
}
