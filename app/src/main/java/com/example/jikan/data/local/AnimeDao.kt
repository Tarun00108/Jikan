package com.example.jikan.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.jikan.data.AnimeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeDao {
    @Query("SELECT * FROM anime_cache")
    fun getAllAnime(): Flow<List<AnimeEntity>>

    @Query("SELECT * FROM anime_cache WHERE id = :id")
    suspend fun getAnimeById(id: Int): AnimeEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(animeList: List<AnimeEntity>)
}