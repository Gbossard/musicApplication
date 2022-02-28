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

    //Get trending albums
    @GET("trending.php")
    fun getTrendingAlbumsAsync(@Query("country") country: String, @Query("type") type: String, @Query("format") format: String): Deferred<TrendingAlbums>

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

    suspend fun getAlbums(): TrendingAlbums {
        return api.getTrendingAlbumsAsync("us", "itunes", "albums").await()
    }
}

data class Title (
    //val idTrend: String?,
    val intChartPlace: String?,
    //val idArtist: String?,
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



data class Album (
    //val idTrend: String?,
    val intChartPlace: String?,
    //val idArtist: String?,
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

data class TrendingAlbums (
    val trending: List<Album>
)