package com.example.musicapplication.ui.favorites.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Artist(
    @PrimaryKey val idArtist: Int,
    val strArtist: String,
    val strArtistThumb: String?,
    val isFavorites: Boolean,
)