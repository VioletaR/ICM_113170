package com.example.estudo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.estudo.navigation.NavBar
import com.example.estudo.navigation.Screens
import com.example.estudo.screens.LoginScreen
import com.example.estudo.ui.theme.EstudoTheme

class MainActivity : ComponentActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EstudoTheme {
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = { NavBar(
                        navController=navController,
                    ) }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screens.Login.name,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        composable(Screens.Login.name) {
                            LoginScreen(navController)
                        }
                    }
                }
            }
        }
    }
}
