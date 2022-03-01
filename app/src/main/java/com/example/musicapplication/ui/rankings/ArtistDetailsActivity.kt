package com.example.musicapplication.ui.rankings

import TrendingTitlesViewModel
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import com.example.musicapplication.R
import com.example.musicapplication.databinding.ActivityArtistDetailsBinding
import androidx.annotation.NonNull
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicapplication.databinding.ActivityMainBinding
import com.example.musicapplication.databinding.FragmentTitlesBinding
import com.example.musicapplication.ui.networks.Album
import com.example.musicapplication.ui.networks.ArtistData
import com.example.musicapplication.ui.networks.Title
import com.example.musicapplication.ui.rankings.adapters.ArtistAlbumsAdapter
import com.example.musicapplication.ui.rankings.adapters.ArtistTitlesAdapter
import com.example.musicapplication.ui.rankings.adapters.TrendingTitlesListAdapter
import com.example.musicapplication.ui.rankings.models.ArtistViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


class ArtistDetailsActivity : AppCompatActivity() {

    private val viewModel: ArtistViewModel by viewModels()
    lateinit var binding : ActivityArtistDetailsBinding
    private var dataAlbums = mutableListOf<Album>()
    private var dataTitles = mutableListOf<Title>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_artist_details)
        val artistId: String = intent.getStringExtra("artistId").toString()
        val artistName: String = intent.getStringExtra("artistName").toString()

        //Back button
        binding.backToMain.setOnClickListener{
            onBackPressed()
        }

        viewModel.getArtist(artistId, artistName)

        lifecycleScope.launch {
            viewModel.listen().collect() {

                val artistTable = it.artistData
                val artistAlbumTable = it.artistAlbums
                val artistTitleTable = it.artistTitles

                if (artistTable != null) {
                    var artist = ArtistData(artistTable.artists).artists[0]
                    binding.artist = artist
                    Picasso.with(this@ArtistDetailsActivity).load(artist.strArtistThumb).into(binding.image)

                }

                if (artistAlbumTable != null) {
                    for(i in artistAlbumTable.album){
                        dataAlbums.add(i)
                    }

                    binding.albumList.apply {

                        binding.albumList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL ,false)
                        binding.albumList.setHasFixedSize(true)
                        // Setting the Adapter with the recyclerview
                        binding.albumList.adapter = ArtistAlbumsAdapter(dataAlbums)

                    }
                }

                if (artistTitleTable != null) {
                    for(i in artistTitleTable.track){
                        dataTitles.add(i)
                    }

                    binding.popularTitlesList.apply {

                        binding.popularTitlesList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL ,false)
                        // Setting the Adapter with the recyclerview
                        binding.popularTitlesList.adapter = ArtistTitlesAdapter(dataTitles)

                    }
                }
            }
        }

    }
}