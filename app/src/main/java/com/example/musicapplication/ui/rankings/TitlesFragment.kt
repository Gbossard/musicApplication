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
import com.example.musicapplication.databinding.FragmentTitlesBinding
import com.example.musicapplication.ui.networks.Title
import com.example.musicapplication.ui.rankings.adapters.TrendingTitlesListAdapter
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


/**
 * A simple [Fragment] subclass.
 * Use the [TitlesFragment.newInstance] factory method to
 * create an instance of this fragment.
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
                Log.d("JSON", it.toString())
                val titles = it.response
                if (titles != null) {
                    for(i in titles.trending){
                        Log.d("DATA ITEM", i.toString())
                        data.add(i)
                    }

                    //Reverse order of list
                    data.reverse()

                    Log.d("DATA", data.toString())

                    bindingList.recyclerList.apply {

                        bindingList.recyclerList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL ,false)

                        //bindingList.recyclerList.setHasFixedSize(true)

                        // Setting the Adapter with the recyclerview
                        bindingList.recyclerList.adapter = TrendingTitlesListAdapter(data)

                        Log.d("STEP", data.toString())
                    }
                }
            }
        }

        return bindingList.root
    }


}
