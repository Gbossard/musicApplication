package com.example.musicapplication.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicapplication.databinding.FragmentSearchBinding
import com.example.musicapplication.ui.search.adapters.SearchAdapter
import com.example.musicapplication.ui.search.models.SearchRecyclerDataModel
import com.example.musicapplication.ui.search.models.SearchViewModel
import kotlinx.coroutines.launch


class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private val viewModel: SearchViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //On réaffiche la toolbar (que l'on a retirée dans le thème NOACTIONBAR) :
        (activity as AppCompatActivity).setSupportActionBar(_binding?.tabToolbar)

        _binding?.edittext?.addTextChangedListener(object: TextWatcher{
            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int){

                viewModel.searchArtist("$s%")

                if(viewModel != null){
                    lifecycleScope.launch {
                        viewModel.listen().collect() {

                            var dataArtist = mutableListOf<SearchRecyclerDataModel.Artist>()
                            var dataAlbum = mutableListOf<SearchRecyclerDataModel.Album>()

                            // Liste artistes et albums
                            val artistTable = it.artistData
                            val albumTable = it.albumData

                            if (artistTable?.artists != null) {
                                binding.noArtist.isVisible = false
                                binding.recyclerArtists.isVisible = true
                                for(i in artistTable.artists){
                                    dataArtist.add(i)
                                }
                                Log.d("artists", dataArtist.toString())

                                binding.recyclerArtists.apply {
                                    binding.recyclerArtists.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL ,false)
                                    binding.recyclerArtists.setHasFixedSize(true)
                                    val myAdapter = SearchAdapter()
                                    myAdapter.setData(dataArtist)
                                    binding.recyclerArtists.adapter = myAdapter
                                }
                            } else {
                                binding.recyclerArtists.isVisible = false
                                binding.noArtist.isVisible = true
                            }

                            if (albumTable?.album != null) {
                                binding.noAlbum.isVisible = false
                                binding.recyclerAlbums.isVisible = true
                                for(i in albumTable.album){
                                    dataAlbum.add(i)
                                }
                                Log.d("artists", dataAlbum.toString())

                                binding.recyclerAlbums.apply {
                                    binding.recyclerAlbums.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL ,false)
                                    binding.recyclerAlbums.setHasFixedSize(true)
                                    val myAdapter = SearchAdapter()
                                    myAdapter.setData(dataAlbum)
                                    binding.recyclerAlbums.adapter = myAdapter
                                }

                            } else {
                                binding.recyclerAlbums.isVisible = false
                                binding.noAlbum.isVisible = true
                            }

                        }

                    }
                }
            }

        })


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}