package com.example.jikan.data.repository

import com.example.jikan.data.AnimeEntity
import com.example.jikan.data.local.AnimeDao
import com.example.jikan.data.remote.JikanApi
import com.example.jikan.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class AnimeRepository @Inject constructor(private val api: JikanApi, private val dao: AnimeDao) {
    fun getTopAnime(): Flow<Resource<List<AnimeEntity>>> = flow {
        emit(Resource.Loading())

        val localData = dao.getAllAnime().first()

        if (localData.isNotEmpty()) {
            emit(Resource.Success(localData))
        }

        try {
            val response = api.getTopAnime()

            val entities = response.data.map { dto ->
                val genreString = dto.genres?.joinToString(", ") { it.name } ?: "Unknown"

                AnimeEntity(
                    id = dto.malId,
                    title = dto.title,
                    episodes = dto.episodes ?: 0,
                    score = dto.score ?: 0.0,
                    rating = dto.rating ?: "N/A",
                    genres = genreString,
                    imageUrl = dto.images.jpg.imageUrl,
                    synopsis = dto.synopsis ?: "No synopsis available",
                    trailerUrl = dto.trailer?.embedUrl
                )
            }

            dao.insertAll(entities)

            emit(Resource.Success(entities))

        } catch (e: IOException) {
            emit(Resource.Error("Network error. Showing cached data.", data = localData.ifEmpty { null }))
        } catch (e: Exception) {
            emit(Resource.Error("Unknown error occurred", data = localData.ifEmpty { null }))
        }
    }

    suspend fun getAnimeDetail(id: Int): Resource<AnimeEntity> {
        return try {
            val response = api.getAnimeDetails(id)
            val dto = response.data

            val genreString = dto.genres?.joinToString(", ") { it.name } ?: "Unknown"

            Resource.Success(
                AnimeEntity(
                    id = dto.malId,
                    title = dto.title,
                    episodes = dto.episodes ?: 0,
                    score = dto.score ?: 0.0,
                    rating = dto.rating ?: "N/A", // Added
                    genres = genreString,         // Added
                    imageUrl = dto.images.jpg.imageUrl,
                    synopsis = dto.synopsis ?: "",
                    trailerUrl = dto.trailer?.embedUrl
                )
            )
        } catch(e: Exception) {
            val local = dao.getAnimeById(id)
            if(local != null) Resource.Success(local)
            else Resource.Error("Could not load details")
        }
    }
}