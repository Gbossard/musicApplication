package com.example.musicapplication.ui.rankings

import TrendingTitlesViewModel
import android.os.Bundle
import android.provider.Contacts
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicapplication.databinding.FragmentTitlesBinding
import com.example.musicapplication.ui.networks.Title
import com.example.musicapplication.ui.rankings.adapters.TrendingTitlesListAdapter
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect


/**
 * ONGLET TITRE :
 * - liste des singles les plus populaires actuellement (TOP 5)
 */
class TitlesFragment : Fragment() {

    private var data = mutableListOf<Title>()
    private val viewModel: TrendingTitlesViewModel by viewModels()

    @InternalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val bindingList = FragmentTitlesBinding.inflate(inflater, container, false)

        //AFFICHAGE DE LA LISTE DES TITRES :

        viewModel.getTrendingTitles()

        lifecycleScope.launch {
            viewModel.listen().collect() {
                val titles = it.response
                if (titles != null) {
                    for(i in titles.trending){
                        data.add(i)
                    }
                    //On inverse l'ordre de la liste :
                    data.reverse()

                    bindingList.recyclerList.apply {
                        bindingList.recyclerList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL ,false)
                        // Remplissage du recycler view :
                        bindingList.recyclerList.adapter = TrendingTitlesListAdapter(data)
                    }
                }
            }
        }

        return bindingList.root
    }

}