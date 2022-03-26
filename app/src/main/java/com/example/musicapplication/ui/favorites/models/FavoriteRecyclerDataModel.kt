package com.example.musicapplication.ui.favorites.models

import com.example.musicapplication.ui.networks.Album
import com.example.musicapplication.ui.networks.Artist

sealed class FavoriteRecyclerDataModel {
    data class ArtistDataModel(
        val artists: List<Artist>?
    ) : FavoriteRecyclerDataModel()

    data class ArtistFav(
        val strArtist: String?,
        val strArtistThumb: String?,
        val idArtist: String?,
    ) : FavoriteRecyclerDataModel()

    data class AlbumDataModel(
        val album: List<Album>?
    ) : FavoriteRecyclerDataModel()

    data class AlbumFav(
        val strAlbum: String?,
        val strArtist: String?,
        val strAlbumThumb: String?,
        val idAlbum: String?,
    ) : FavoriteRecyclerDataModel()
}