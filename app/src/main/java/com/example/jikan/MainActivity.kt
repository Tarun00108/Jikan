package com.example.jikan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.jikan.ui.theme.AnimeDetailScreen
import com.example.jikan.ui.theme.AnimeListScreen
import com.example.jikan.ui.theme.AnimeViewModel
import com.example.jikan.ui.theme.JikanTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AnimeAppNavigation()
        }
    }
}

@Composable
fun AnimeAppNavigation() {
    val navController = rememberNavController()
    val viewModel: AnimeViewModel = hiltViewModel()

    NavHost(navController = navController, startDestination = "list") {

        composable("list") {
            AnimeListScreen(viewModel, navController)
        }

        composable("detail/{id}") { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id")
            AnimeDetailScreen(viewModel, id)
        }
    }
}