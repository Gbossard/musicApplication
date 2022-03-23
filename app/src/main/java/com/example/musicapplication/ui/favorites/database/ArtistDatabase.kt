package com.example.musicapplication.ui.favorites.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Artist::class], version = 1)
abstract class ArtistDatabase : RoomDatabase() {
    abstract fun dao(): ArtistDAO
}

class DatabaseManager(context: Context) {
    private val db = Room.databaseBuilder(
        context,
        ArtistDatabase::class.java,
        "db.sqlite"
    ).build()
}