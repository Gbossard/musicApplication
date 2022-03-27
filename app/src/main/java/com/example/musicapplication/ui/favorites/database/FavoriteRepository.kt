package com.example.musicapplication.ui.favorites.database

class FavoriteRepository(private val dao: FavoriteDao) {
    val artists = dao.getAllArtists()
//    val albums = dao.getAllAlbums()

    // ARTIST
    suspend fun insert(artist: Artist) {
        return dao.insertArtist(artist)
    }

    suspend fun update(artist: Artist) {
        return dao.updateArtist(artist)
    }

    suspend fun delete(artist: Artist) {
        return dao.deleteArtist(artist)
    }

    // ALBUM
//    suspend fun insert(album: Album) {
//        return dao.insertAlbum(album)
//    }
//
//    suspend fun update(album: Album) {
//        return dao.updateAlbum(album)
//    }
//
//    suspend fun delete(album: Album) {
//        return dao.deleteAlbum(album)
//    }
}