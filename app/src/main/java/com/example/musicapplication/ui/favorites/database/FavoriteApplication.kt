package com.example.musicapplication.ui.favorites.database

import android.app.Application

class FavoriteApplication : Application() {
    val database by lazy { FavoriteDatabase.getInstance(this) }
    val repository by lazy { FavoriteRepository(database.favoriteDao()) }
}