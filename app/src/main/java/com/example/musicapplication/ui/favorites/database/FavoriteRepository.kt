package com.example.musicapplication.ui.favorites.database

class FavoriteRepository(private val dao: FavoriteDao) {
    val artists = dao.getAllArtists()

    suspend fun insert(artist: Artist) {
        return dao.insertArtist(artist)
    }

    suspend fun update(artist: Artist) {
        return dao.updateArtist(artist)
    }

    suspend fun delete(artist: Artist) {
        return dao.deleteArtist(artist)
    }
}