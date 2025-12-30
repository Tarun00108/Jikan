package com.example.jikan.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "anime_table")
data class AnimeEntity(
    @PrimaryKey val id: Int,
    val title: String,
    val episodes: Int,
    val score: Double,
    val rating: String,
    val genres: String,
    val cast: String,
    val imageUrl: String,
    val synopsis: String,
    val trailerUrl: String?
)
