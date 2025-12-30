package com.example.jikan.data.remote

import retrofit2.http.GET
import retrofit2.http.Path

interface JikanApi {
    @GET("top/anime")
    suspend fun getTopAnime(): TopAnimeResponse

    @GET("anime/{id}")
    suspend fun getAnimeDetails(@Path("id") id: Int): AnimeDetailResponse

    @GET("anime/{id}/characters")
    suspend fun getAnimeCharacters(@Path("id") id: Int): CharacterResponse
}