package com.example.musicapplication.ui.networks

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface TheAudioDBAPI {
    //Get trending singles
    @GET("trending.php")
    fun getTrendingTitlesAsync(@Query("country") country: String, @Query("type") type: String, @Query("format") format: String): Deferred<TrendingTitles>

    //Get popular titles from an artist
    @GET("track-top10.php")
    fun getTitlesFromArtistAsync(@Query("s") artist: String): Deferred<ArtistTitles>

    //Get trending albums
    @GET("trending.php")
    fun getTrendingAlbumsAsync(@Query("country") country: String, @Query("type") type: String, @Query("format") format: String): Deferred<TrendingAlbums>

    //Get albums from an artist
    @GET("album.php")
    fun getAlbumsFromArtistAsync(@Query("i") artist: String): Deferred<ArtistAlbums>

    //Get artist data
    @GET("artist.php")
    fun getArtistAsync(@Query("i") artist: String): Deferred<ArtistData>

}

object NetworkManager {

    private val api = Retrofit.Builder()
        .baseUrl("https://www.theaudiodb.com/api/v1/json/523532/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()
        .create(TheAudioDBAPI::class.java)

    suspend fun getTitles(): TrendingTitles {
        return api.getTrendingTitlesAsync("us", "itunes", "singles").await()
    }

    suspend fun getTitlesFromArtist(artist: String): ArtistTitles {
        return api.getTitlesFromArtistAsync(artist).await()
    }

    suspend fun getAlbums(): TrendingAlbums {
        return api.getTrendingAlbumsAsync("us", "itunes", "albums").await()
    }

    suspend fun getAlbumsFromArtist(id: String): ArtistAlbums {
        return api.getAlbumsFromArtistAsync(id).await()
    }

    suspend fun getArtist(id: String): ArtistData {
        return api.getArtistAsync(id).await()
    }
}

data class Title (
    //val idTrend: String?,
    val intChartPlace: String?,
    val idArtist: String?,
    //val idAlbum: String?,
    //val idTrack: String?,
    //val strArtistMBID: String?,
    //val strAlbumMBID: String?,
    //val strTrackMBID: String?,
    val strArtist: String?,
    val strAlbum: String?,
    val strTrack: String?,
    val strArtistThumb: String?,
    val strAlbumThumb: String?,
    val strTrackThumb: String?,
    val strCountry: String?,
    //val strType: String?,
    //val intWeek: String?,
    //val dateAdded: String?,
)

data class TrendingTitles (
    val trending: List<Title>
)

data class ArtistTitles (
    val track: List<Title>
)

data class Album (
    //val idTrend: String?,
    val intChartPlace: String?,
    val idArtist: String?,
    val idAlbum: String?,
    //val idTrack: String?,
    //val strArtistMBID: String?,
    //val strAlbumMBID: String?,
    //val strTrackMBID: String?,
    val intYearReleased: String?,
    val strArtist: String?,
    val strAlbum: String?,
    val strTrack: String?,
    val strArtistThumb: String?,
    val strAlbumThumb: String?,
    val strTrackThumb: String?,
    val strCountry: String?,
    //val strType: String?,
    //val intWeek: String?,
    //val dateAdded: String?,
)

data class TrendingAlbums (
    val trending: List<Album>
)

data class ArtistAlbums (
    val album: List<Album>
)

data class Artist(
    val idArtist: String,
    val idLabel: String,
    val intBornYear: String,
    val intCharted: String,
    val intDiedYear: Any,
    val intFormedYear: String,
    val intMembers: String,
    val strArtist: String,
    val strArtistAlternate: String,
    val strArtistBanner: String,
    val strArtistClearart: String,
    val strArtistCutout: String,
    val strArtistFanart: String,
    val strArtistFanart2: String,
    val strArtistFanart3: String,
    val strArtistFanart4: String,
    val strArtistLogo: String,
    val strArtistStripped: Any,
    val strArtistThumb: String,
    val strArtistWideThumb: String,
    val strBiographyCN: String,
    val strBiographyDE: String,
    val strBiographyEN: String,
    val strBiographyES: String,
    val strBiographyFR: String,
    val strBiographyHU: String,
    val strBiographyIL: String,
    val strBiographyIT: String,
    val strBiographyJP: String,
    val strBiographyNL: String,
    val strBiographyNO: String,
    val strBiographyPL: String,
    val strBiographyPT: String,
    val strBiographyRU: String,
    val strBiographySE: String,
    val strCountry: String,
    val strCountryCode: String,
    val strDisbanded: Any,
    val strFacebook: String,
    val strGender: String,
    val strGenre: String,
    val strISNIcode: Any,
    val strLabel: String,
    val strLastFMChart: String,
    val strLocked: String,
    val strMood: String,
    val strMusicBrainzID: String,
    val strStyle: String,
    val strTwitter: String,
    val strWebsite: String
)

data class ArtistData (
    val artists: List<Artist>
)