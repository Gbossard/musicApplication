package com.example.musicapplication.ui.favorites

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicapplication.databinding.FragmentFavoritesBinding
import com.example.musicapplication.ui.favorites.adapters.FavoritesAdapter
import com.example.musicapplication.ui.favorites.database.Artist
import com.example.musicapplication.ui.favorites.database.FavoriteDao
import com.example.musicapplication.ui.favorites.database.FavoriteDatabase
import com.example.musicapplication.ui.favorites.database.FavoriteRepository
import com.example.musicapplication.ui.favorites.models.FavoriteViewModel
import com.example.musicapplication.ui.favorites.models.FavoriteViewModelFactory
import com.example.musicapplication.ui.rankings.ArtistDetailsActivity
import com.example.musicapplication.ui.rankings.models.ArtistViewModel

class FavoritesFragment : Fragment() {
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private lateinit var artistViewModel: ArtistViewModel
    private lateinit var adapter: FavoritesAdapter

    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //On réaffiche la toolbar (que l'on a retirée dans le thème NOACTIONBAR) :
        (activity as AppCompatActivity).setSupportActionBar(_binding?.tabToolbar)

        //Récupération des artistes favoris :
        val dao = FavoriteDatabase.getInstance(requireContext()).favoriteDao()
        val repository = FavoriteRepository(dao)
        val factory = FavoriteViewModelFactory(repository)
        favoriteViewModel = ViewModelProvider(this, factory).get(FavoriteViewModel::class.java)
        //binding.favoriteViewModel = favoriteViewModel
        //binding.lifecycleOwner = this

        return root
    }

    /*private fun initRecyclerView(){
        binding.recyclerArtists.layoutManager = LinearLayoutManager(this)
        adapter = FavoritesAdapter({selectedItem: Artist ->listItemClicked(selectedItem)})
        binding.recyclerArtists.adapter = adapter
        displayArtistsList()
    }*/

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}