package com.example.musicapplication.ui.favorites.database

import androidx.room.*

@Dao
interface ArtistDAO {
    @Insert
    fun insertUser(artist: Artist)

    @Update
    fun updateUser(artist: Artist)

    @Delete
    fun removeUser(artist: Artist)

    @Query("SELECT * FROM Artist")
    fun listUsers(): List<Artist>
}