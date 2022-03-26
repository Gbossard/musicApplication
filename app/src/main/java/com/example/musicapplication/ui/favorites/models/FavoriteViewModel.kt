package com.example.musicapplication.ui.favorites.models

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.musicapplication.ui.favorites.database.Artist
import com.example.musicapplication.ui.favorites.database.FavoriteRepository
import kotlinx.coroutines.launch

class FavoriteViewModel(private val repository: FavoriteRepository): ViewModel() {
    val idArtist = MutableLiveData<Int>()
    val strArtist = MutableLiveData<String>()
    val strArtistThumb = MutableLiveData<String>()
    val isFavorite = MutableLiveData<Boolean>()

    fun save() {
        val idArtist = idArtist.value!!
        val strArtist = strArtist.value!!
        val strArtistThumb = strArtistThumb.value!!
        val isFavorite = isFavorite.value!!
        insertArtist(Artist(idArtist, strArtist, strArtistThumb, isFavorite))
        Log.d("SAVE","Artiste ajouté")
    }

    private fun insertArtist(artist: Artist) = viewModelScope.launch {
        repository.insert(artist)
    }

    fun getSavedArtists() {
        liveData {
            repository.artists.collect {
                emit(it)
            }
        }
    }
}