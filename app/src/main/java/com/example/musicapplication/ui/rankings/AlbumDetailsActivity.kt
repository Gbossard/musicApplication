package com.example.musicapplication.ui.rankings

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.musicapplication.R
import com.example.musicapplication.databinding.ActivityAlbumDetailsBinding
import com.example.musicapplication.ui.favorites.models.FavoriteViewModel
import com.example.musicapplication.ui.networks.AlbumData
import com.example.musicapplication.ui.networks.Title
import com.example.musicapplication.ui.rankings.adapters.AlbumDetailsAdapter
import com.example.musicapplication.ui.rankings.models.AlbumDetailsViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

/**
 * ECRAN DETAILS DE L'ALBUM :
 * - header : image de l'album + artiste + titre
 * - tracklist
 */
class AlbumDetailsActivity : AppCompatActivity() {

    private val viewModel: AlbumDetailsViewModel by viewModels()
    lateinit var binding : ActivityAlbumDetailsBinding
    private lateinit var favoriteViewModel: FavoriteViewModel
    private var dataTitles = mutableListOf<Title>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_album_details)
        val albumId: String = intent.getStringExtra("albumId").toString()

        //Back button
        binding.backToMain.setOnClickListener{
            onBackPressed()
        }

//        // Favorites
//        binding.addToFavorites.setOnClickListener{
//            doAsync {
//                favoriteViewModel.save()
//            }
//        }

        viewModel.getAlbum(albumId)

        lifecycleScope.launch {
            viewModel.listen().collect() {
                // Tracklist :
                val tracklistTable = it.tracklist
                // Infos de l'album :
                val albumTable = it.albumData

                if (tracklistTable != null) {
                    for(i in tracklistTable.track){
                        dataTitles.add(i)
                    }

                    // Affichages du nombres de titres :
                    binding.tracklistSize = dataTitles.size.toString()

                    binding.titlesList.apply {
                        binding.titlesList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL ,false)
                        // Setting the Adapter with the recyclerview
                        binding.titlesList.adapter = AlbumDetailsAdapter(dataTitles)

                    }
                }

                if (albumTable != null) {
                    var album = AlbumData(albumTable.album).album[0]
                    binding.album = album

                    //Si l'artiste est not??, on affiche les votes :
                    if(album.intScore == null){
                        binding.votes.isVisible = false
                    }

                    //Affichage de l'image header :
                    Picasso.with(this@AlbumDetailsActivity).load(album.strAlbumThumb).into(binding.image)
                    Picasso.with(this@AlbumDetailsActivity).load(album.strAlbumThumb).into(binding.background)
                }

            }
        }

    }
}