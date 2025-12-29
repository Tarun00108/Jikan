package com.example.jikan.ui.theme

import android.webkit.WebView
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.jikan.data.AnimeEntity
import com.example.jikan.util.Resource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeListScreen(
    viewModel: AnimeViewModel,
    navController: NavController
) {
    val state by viewModel.animeList.collectAsState()

    Scaffold(topBar = { TopAppBar(title = { Text("Jikan Top Anime") }) }) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when (val result = state) {
                is Resource.Loading -> {
                    if (result.data.isNullOrEmpty()) {
                        CircularProgressIndicator(Modifier.align(Alignment.Center))
                    } else {
                        AnimeList(result.data, viewModel.canShowImages, navController)
                    }
                }
                is Resource.Error -> {
                    Column {
                        Text(
                            text = result.message ?: "Error",
                            color = Color.Red,
                            modifier = Modifier.padding(8.dp)
                        )
                        result.data?.let { AnimeList(it, viewModel.canShowImages, navController) }
                    }
                }
                is Resource.Success -> {
                    AnimeList(result.data ?: emptyList(), viewModel.canShowImages, navController)
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun AnimeList(
    animes: List<AnimeEntity>,
    showImages: Boolean,
    navController: NavController
) {
    LazyColumn {
        items(animes) { anime ->
            Card(
                modifier = Modifier.fillMaxWidth().padding(8.dp)
                    .clickable { navController.navigate("detail/${anime.id}") },
                elevation = CardDefaults.cardElevation(4.dp)
            ) {
                Row(modifier = Modifier.padding(16.dp)) {
                    if (showImages) {
                        GlideImage(
                            model = anime.imageUrl,
                            contentDescription = null,
                            modifier = Modifier.size(80.dp)
                        )
                    } else {
                        Box(
                            modifier = Modifier.size(80.dp).background(Color.Gray),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(anime.title.take(1), color = Color.White)
                        }
                    }
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(text = anime.title, style = MaterialTheme.typography.titleMedium)
                        Text(text = "Episodes: ${anime.episodes}")
                        Text(text = "Rating: ${anime.score}")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun AnimeDetailScreen(
    viewModel: AnimeViewModel,
    animeId: String?
) {
    val anime by viewModel.selectedAnime.collectAsState()

    if (anime != null) {
        Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState())) {

            if (anime!!.trailerUrl != null) {
                AndroidView(factory = { context ->
                    WebView(context).apply {
                        settings.javaScriptEnabled = true
                        loadUrl(anime!!.trailerUrl!!)
                    }
                }, modifier = Modifier.fillMaxWidth().height(250.dp))
            } else {
                GlideImage(
                    model = anime!!.imageUrl,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth().height(250.dp)
                )
            }

            Column(modifier = Modifier.padding(16.dp)) {
                Text(text = anime!!.title, style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(8.dp))

                Text(text = "Score: ${anime!!.score} | Episodes: ${anime!!.episodes}", style = MaterialTheme.typography.bodyLarge)
                Text(text = "Age Rating: ${anime!!.rating}", style = MaterialTheme.typography.bodyMedium)
                Text(text = "Genres: ${anime!!.genres}", style = MaterialTheme.typography.bodyMedium, color = Color.DarkGray)

                Spacer(modifier = Modifier.height(16.dp))
                Text(text = "Synopsis", style = MaterialTheme.typography.titleLarge)
                Text(text = anime!!.synopsis)

            }
        }
    }
}