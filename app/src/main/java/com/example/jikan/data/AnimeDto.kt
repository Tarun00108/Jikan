package com.example.jikan.data

import com.google.gson.annotations.SerializedName

data class TopAnimeResponse(
    val data: List<AnimeDto>
)

data class AnimeDetailResponse(
    val data: AnimeDto
)

data class AnimeDto(
    @SerializedName("mal_id") val malId: Int,
    val title: String,
    val episodes: Int?,
    val score: Double?,
    val synopsis: String?,
    val rating: String?, // Added: e.g., "PG-13"
    val genres: List<GenreDto>?, // Added: List of genres
    @SerializedName("images") val images: Images,
    @SerializedName("trailer") val trailer: Trailer?
)

data class GenreDto(val name: String)

data class Images(val jpg: Jpg)
data class Jpg(@SerializedName("image_url") val imageUrl: String)
data class Trailer(@SerializedName("embed_url") val embedUrl: String?)