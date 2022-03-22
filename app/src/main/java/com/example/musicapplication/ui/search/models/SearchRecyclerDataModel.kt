package com.example.musicapplication.ui.search.models

sealed class SearchRecyclerDataModel {
    data class ArtistDataModel(
        val artists: List<Artist>?
    ) : SearchRecyclerDataModel()

    data class Artist(
        val strArtist: String?,
        val strArtistThumb: String?,
        val idArtist: String?,
    ) : SearchRecyclerDataModel()

    data class AlbumDataModel(
        val album: List<Album>?
    ) : SearchRecyclerDataModel()

    data class Album(
        val strAlbum: String?,
        val strArtist: String?,
        val strAlbumThumb: String?,
        val idAlbum: String?,
    ) : SearchRecyclerDataModel()
}