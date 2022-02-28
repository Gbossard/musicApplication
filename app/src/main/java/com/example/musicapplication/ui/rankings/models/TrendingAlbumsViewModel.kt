package com.example.musicapplication.ui.rankings.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapplication.ui.networks.NetworkManager
import com.example.musicapplication.ui.networks.TrendingAlbums
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TrendingAlbumsViewModel : ViewModel() {

    private val albumFlow = MutableStateFlow(
        TrendingAlbumsState(
            currentState = TrendingAlbumsCurrentState.initial,
        )
    )

    fun listen(): Flow<TrendingAlbumsState> {
        //Log.d("Flow", albumFlow.asStateFlow().toString())
        return albumFlow.asStateFlow()
    }

    fun getTrendingAlbums() {
        viewModelScope.launch {
            albumFlow.emit(
                TrendingAlbumsState(
                    currentState = TrendingAlbumsCurrentState.loading,
                )
            )

            val res = NetworkManager.getAlbums()
            //Log.d("RES", res.albums.toString())

            try {
                if (res.trending.isNotEmpty()) {
                    albumFlow.emit(
                        TrendingAlbumsState(
                            currentState = TrendingAlbumsCurrentState.success,
                            response = res
                        )
                    )
                } else {
                    albumFlow.emit(
                        TrendingAlbumsState(
                            currentState = TrendingAlbumsCurrentState.error,
                            response = res
                        )
                    )
                }
            } catch (e: Exception) {
                albumFlow.emit(
                    TrendingAlbumsState(
                        currentState = TrendingAlbumsCurrentState.error,
                        response = res
                    )
                )
            }
        }
    }
}

data class TrendingAlbumsState(
    val currentState: TrendingAlbumsCurrentState,
    val response: TrendingAlbums? = null
)

enum class TrendingAlbumsCurrentState {
    initial, loading, success, error
}