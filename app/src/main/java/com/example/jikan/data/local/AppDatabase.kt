package com.example.jikan.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.jikan.data.AnimeEntity

@Database(entities = [AnimeEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun animeDao(): AnimeDao
}