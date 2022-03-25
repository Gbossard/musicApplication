package com.example.musicapplication.ui.favorites.adapters

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapplication.R
import com.example.musicapplication.ui.favorites.database.Artist
import com.example.musicapplication.ui.favorites.models.FavoriteViewModel
import com.example.musicapplication.ui.rankings.AlbumDetailsActivity
import com.example.musicapplication.ui.rankings.ArtistDetailsActivity


class FavoritesAdapter(private val clickListener: (Artist) -> Unit) : RecyclerView.Adapter<FavoriteViewHolder>() {
    private val artistsList = ArrayList<Artist>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ListItemBinding = DataBindingUtil.inflate(layoutInflater, R.layout.search_artists_list, parent, false)
        return favoriteViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return artistsList.size
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(artistsList[position], clickListener)

        holder.strArtist.setOnClickListener {
            val intent = Intent(context, ArtistDetailsActivity::class.java)
            intent.putExtra("artistId", albumsViewModel.idArtist)
            intent.putExtra("artistName", albumsViewModel.strArtist)
            Log.d("INTENT", intent.extras.toString())
            context?.startActivity(intent)
        }
    }

    fun setList(artists: List<Artist>) {
        artistsList.clear()
        artistsList.addAll(artists)
    }
}

class FavoriteViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(artist: Artist, clickListener: (Artist) -> Unit) {
        binding.strArtist.text = artist.strArtist
        binding.strArtistThumb.text = artist.strArtistThumb
        binding.listItemLayout.setOnClickListener {
            val intent = Intent(context, ArtistDetailsActivity::class.java)
            intent.putExtra("artistId", ArtistViewModel.idAlbum)
            Log.d("INTENT", intent.extras.toString())
            context?.startActivity(intent)
        }
    }
}