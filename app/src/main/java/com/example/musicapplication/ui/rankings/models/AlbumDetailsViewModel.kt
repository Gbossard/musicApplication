package com.example.musicapplication.ui.rankings.models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapplication.ui.networks.AlbumData
import com.example.musicapplication.ui.networks.AlbumTitles
import com.example.musicapplication.ui.networks.NetworkManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AlbumDetailsViewModel() : ViewModel() {

    private val albumFlow = MutableStateFlow(
        AlbumState(
            currentState = AlbumCurrentState.initial,
        )
    )

    fun listen(): Flow<AlbumState> {
        //Log.d("Flow", albumFlow.asStateFlow().toString())
        return albumFlow.asStateFlow()
    }

    fun getAlbum(id: String) {
        viewModelScope.launch {
            albumFlow.emit(
                AlbumState(
                    currentState = AlbumCurrentState.loading,
                )
            )
            // Liste des titres présentes dans l'album :
            val resTracklist = NetworkManager.getAlbumTracklist(id)
            // Infos de l'album :
            val resAlbumData = NetworkManager.getAlbum(id)

            try {
                if (resTracklist.track.isNotEmpty()) {
                    albumFlow.emit(
                        AlbumState(
                            currentState = AlbumCurrentState.success,
                            tracklist = resTracklist,
                            albumData = resAlbumData
                        )
                    )
                } else {
                    albumFlow.emit(
                        AlbumState(
                            currentState = AlbumCurrentState.error,
                            tracklist = resTracklist,
                            albumData = resAlbumData
                        )
                    )
                }
            } catch (e: Exception) {
                albumFlow.emit(
                    AlbumState(
                        currentState = AlbumCurrentState.error,
                        tracklist = resTracklist,
                        albumData = resAlbumData
                    )
                )

                Log.d("ERROR", e.toString())
            }
        }
    }
}

data class AlbumState(
    val currentState: AlbumCurrentState,
    val tracklist: AlbumTitles? = null,
    val albumData: AlbumData? = null
)

enum class AlbumCurrentState {
    initial, loading, success, error
}