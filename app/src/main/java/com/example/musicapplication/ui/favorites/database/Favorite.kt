package com.example.musicapplication.ui.favorites.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "artist_table")
data class Artist(
    @PrimaryKey(autoGenerate = true) val idArtist: Int,
    val strArtist: String,
    val strArtistThumb: String,
    val isFavorites: Boolean
)

//@Entity(tableName = "album_table")
//data class Album(
//    @PrimaryKey(autoGenerate = true) val idAlbum: Int,
//    val strArtist: String,
//    val strAlbum: String,
//    val strAlbumThumb: String,
//    val isFavorites: Boolean
//)