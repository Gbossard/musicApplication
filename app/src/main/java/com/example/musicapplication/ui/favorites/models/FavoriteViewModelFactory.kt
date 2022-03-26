package com.example.musicapplication.ui.favorites.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.musicapplication.ui.favorites.database.FavoriteRepository

class FavoriteViewModelFactory(private val repository: FavoriteRepository) : ViewModelProvider.Factory {
    @Target(*[AnnotationTarget.CLASS]) annotation class JvmDefaultWithoutCompatibility
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(FavoriteViewModel::class.java)){
            return FavoriteViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown View Model class")
    }
}
