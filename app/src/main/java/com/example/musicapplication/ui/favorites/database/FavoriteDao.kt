package com.example.musicapplication.ui.favorites.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Insert
    suspend fun insertArtist(artist: Artist)

    @Query("SELECT * FROM artist_table")
    fun getAllArtists(): Flow<List<Artist>>

    @Update
    suspend fun updateArtist(artist: Artist)

    @Delete
    suspend fun deleteArtist(artist: Artist)

//    @Insert
//    suspend fun insertAlbum(album: Album)
//
//    @Query("SELECT * FROM album_table")
//    fun getAllAlbums(): Flow<List<Album>>
//
//    @Update
//    suspend fun updateAlbum(album: Album)
//
//    @Delete
//    suspend fun deleteAlbum(album: Album)
}