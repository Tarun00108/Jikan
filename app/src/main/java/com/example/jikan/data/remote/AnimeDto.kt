package com.example.jikan.data.remote

import com.example.jikan.data.AnimeDto
import com.example.jikan.data.Images
import com.example.jikan.data.Jpg
import com.example.jikan.data.Trailer
import com.google.gson.annotations.SerializedName

data class TopAnimeResponse(val data: List<AnimeDto>)
data class AnimeDetailResponse(val data: AnimeDto)

data class AnimeDto(
    @SerializedName("mal_id") val malId: Int,
    val title: String,
    val episodes: Int?,
    val score: Double?,
    val synopsis: String?,
    @SerializedName("images") val images: Images,
    @SerializedName("trailer") val trailer: Trailer?
)

data class Images(val jpg: Jpg)
data class Jpg(@SerializedName("image_url") val imageUrl: String)
data class Trailer(@SerializedName("embed_url") val embedUrl: String?)