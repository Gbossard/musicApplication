package com.example.musicapplication.ui.rankings.models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapplication.ui.networks.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ArtistViewModel() : ViewModel() {

    private val artistFlow = MutableStateFlow(
        ArtistState(
            currentState = ArtistCurrentState.initial,
        )
    )

    fun listen(): Flow<ArtistState> {
        //Log.d("Flow", artistFlow.asStateFlow().toString())
        return artistFlow.asStateFlow()
    }

    fun getArtist(id: String, artist: String) {
        viewModelScope.launch {
            artistFlow.emit(
                ArtistState(
                    currentState = ArtistCurrentState.loading,
                )
            )

            val resArtistData = NetworkManager.getArtist(id)
            val resAlbums = NetworkManager.getAlbumsFromArtist(id)
            val resTitles = NetworkManager.getTitlesFromArtist(artist)

            try {
                if (resArtistData.artists.isNotEmpty()) {
                    artistFlow.emit(
                        ArtistState(
                            currentState = ArtistCurrentState.success,
                            artistData = resArtistData,
                            artistAlbums = resAlbums,
                            artistTitles = resTitles
                        )
                    )
                } else {
                    artistFlow.emit(
                        ArtistState(
                            currentState = ArtistCurrentState.error,
                            artistData = resArtistData,
                            artistAlbums = resAlbums,
                            artistTitles = resTitles
                        )
                    )
                }
            } catch (e: Exception) {
                artistFlow.emit(
                    ArtistState(
                        currentState = ArtistCurrentState.error,
                        artistData = resArtistData,
                        artistAlbums = resAlbums,
                        artistTitles = resTitles
                    )
                )

                Log.d("ERROR", e.toString())
            }
        }
    }
}

data class ArtistState(
    val currentState: ArtistCurrentState,
    val artistData: ArtistData? = null,
    val artistAlbums: ArtistAlbums? = null,
    val artistTitles: ArtistTitles? = null
)

enum class ArtistCurrentState {
    initial, loading, success, error
}