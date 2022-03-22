package com.example.musicapplication.ui.search.models

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicapplication.ui.networks.NetworkManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchViewModel() : ViewModel() {

    private val searchFlow = MutableStateFlow(
        SearchState(
            currentState = SearchCurrentState.initial,
        )
    )

    fun listen(): Flow<SearchState> {
        //Log.d("Flow", searchFlow.asStateFlow().toString())
        return searchFlow.asStateFlow()
    }

    fun searchArtist(artist: String) {
        viewModelScope.launch {
            searchFlow.emit(
                SearchState(
                    currentState = SearchCurrentState.loading,
                )
            )

            val resArtistData = NetworkManager.searchArtist(artist)
            val resAlbumData = NetworkManager.searchAlbum(artist)


            try {
                if (resArtistData.artists?.isNotEmpty() == true) {
                    searchFlow.emit(
                        SearchState(
                            currentState = SearchCurrentState.success,
                            artistData = resArtistData,
                            albumData = resAlbumData,
                        )
                    )
                } else {
                    searchFlow.emit(
                        SearchState(
                            currentState = SearchCurrentState.error,
                            artistData = resArtistData,
                            albumData = resAlbumData,
                        )
                    )
                }
            } catch (e: Exception) {
                searchFlow.emit(
                    SearchState(
                        currentState = SearchCurrentState.error,
                        artistData = resArtistData,
                        albumData = resAlbumData,
                    )
                )

                Log.d("ERROR", e.toString())
            }
        }
    }
}

data class SearchState(
    val currentState: SearchCurrentState,
    val artistData: SearchRecyclerDataModel.ArtistDataModel? = null,
    val albumData: SearchRecyclerDataModel.AlbumDataModel? = null,
)

enum class SearchCurrentState {
    initial, loading, success, error
}