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

            val resAlbumData = NetworkManager.getAlbum(id)

            try {
                if (resAlbumData.track.isNotEmpty()) {
                    albumFlow.emit(
                        AlbumState(
                            currentState = AlbumCurrentState.success,
                            albumData = resAlbumData
                        )
                    )
                } else {
                    albumFlow.emit(
                        AlbumState(
                            currentState = AlbumCurrentState.error,
                            albumData = resAlbumData
                        )
                    )
                }
            } catch (e: Exception) {
                albumFlow.emit(
                    AlbumState(
                        currentState = AlbumCurrentState.error,
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
    val albumData: AlbumTitles? = null
)

enum class AlbumCurrentState {
    initial, loading, success, error
}