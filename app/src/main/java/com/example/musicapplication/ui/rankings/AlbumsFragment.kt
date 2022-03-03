package com.example.musicapplication.ui.rankings

import TrendingTitlesViewModel
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicapplication.databinding.FragmentAlbumsBinding
import com.example.musicapplication.ui.networks.Album
import com.example.musicapplication.ui.networks.Title
import com.example.musicapplication.ui.rankings.adapters.TrendingAlbumsListAdapter
import com.example.musicapplication.ui.rankings.adapters.TrendingTitlesListAdapter
import com.example.musicapplication.ui.rankings.models.TrendingAlbumsViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

/**
 * ONGLET ALBUMS :
 * - liste des albums les plus populaires actuellement (TOP 6)
 */
class AlbumsFragment : Fragment() {

    private var data = mutableListOf<Album>()
    private val viewModel: TrendingAlbumsViewModel by viewModels()

    @InternalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val bindingList = FragmentAlbumsBinding.inflate(inflater, container, false)

        //AFFICHAGE DE LA LISTE DES ALBUMS :

        viewModel.getTrendingAlbums()

        lifecycleScope.launch {
            viewModel.listen().collect() {
                val albums = it.response
                if (albums != null) {
                    for(i in albums.trending){
                        data.add(i)
                    }
                    //On inverse l'ordre :
                    data.reverse()
                    bindingList.recyclerList.apply {
                        bindingList.recyclerList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL ,false)
                        bindingList.recyclerList.adapter = TrendingAlbumsListAdapter(data)

                    }
                }
            }
        }

        return bindingList.root
    }

}