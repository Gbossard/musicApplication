package com.example.musicapplication.ui.favorites.adapters

import android.content.Context
import android.content.Intent
import android.text.method.TextKeyListener.clear
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapplication.R
import com.example.musicapplication.databinding.ActivityArtistDetailsBinding
import com.example.musicapplication.databinding.FragmentFavoritesBinding
import com.example.musicapplication.ui.favorites.FavoritesFragment
import com.example.musicapplication.ui.favorites.database.Artist
import com.example.musicapplication.ui.favorites.models.FavoriteRecyclerDataModel
import com.example.musicapplication.ui.favorites.models.FavoriteViewModel
import com.example.musicapplication.ui.rankings.AlbumDetailsActivity
import com.example.musicapplication.ui.rankings.ArtistDetailsActivity
import com.example.musicapplication.ui.rankings.models.ArtistViewModel
import com.example.musicapplication.ui.search.adapters.SearchAdapter
import com.example.musicapplication.ui.search.models.SearchRecyclerDataModel
import com.squareup.picasso.Picasso
import java.util.Collections.addAll


class FavoritesAdapter(private val clickListener: (Artist) -> Unit) : RecyclerView.Adapter<FavoritesAdapter.FavoriteViewHolder>() {
    private val artistsList = mutableListOf<FavoriteRecyclerDataModel>()
    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        /*val layoutInflater = LayoutInflater.from(parent.context)
        //val binding = DataBindingUtil.inflate(layoutInflater, R.layout.activity_favorites, parent, false)
        return favoriteViewHolder(binding)*/
        val layout = when (viewType) {
            FavoritesAdapter.TYPE_ARTIST -> R.layout.favorite_artists_list
            FavoritesAdapter.TYPE_ALBUM -> R.layout.favorite_albums_list
            else -> throw IllegalArgumentException("Invalid type")
        }
        Log.d("layout", viewType.toString())
        val view = LayoutInflater.from(parent.context)
            .inflate(layout, parent, false)

        context = parent.context

        return FavoriteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return artistsList.size
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        //holder.bind(artistsList[position], clickListener)
        holder.bind(artistsList[position])

        /*holder.strArtist.setOnClickListener {
            val intent = Intent(context, ArtistDetailsActivity::class.java)
            intent.putExtra("artistId", albumsViewModel.idArtist)
            intent.putExtra("artistName", albumsViewModel.strArtist)
            Log.d("INTENT", intent.extras.toString())
            context?.startActivity(intent)
        }*/
    }

    /*fun setList(artists: List<Artist>) {
        artistsList.clear()
        artistsList.addAll(artists)
    }*/

    override fun getItemViewType(position: Int): Int {
        return when (artistsList[position]) {
            is FavoriteRecyclerDataModel.ArtistFav -> FavoritesAdapter.TYPE_ARTIST
            is FavoriteRecyclerDataModel.AlbumFav -> FavoritesAdapter.TYPE_ALBUM
            else -> FavoritesAdapter.TYPE_ARTIST
        }
    }


    class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        /*fun bind(artist: Artist, clickListener: (Artist) -> Unit) {
            binding.strArtist.text = artist.strArtist
            binding.strArtistThumb.text = artist.strArtistThumb
            binding.listItemLayout.setOnClickListener {
                val intent = Intent(context, ArtistDetailsActivity::class.java)
                intent.putExtra("artistId", ArtistViewModel.idAlbum)
                Log.d("INTENT", intent.extras.toString())
                context?.startActivity(intent)
            }
        }*/

        private fun bindArtist(item: FavoriteRecyclerDataModel.ArtistFav) {
            var picture: ImageView = itemView.findViewById(R.id.picture)
            var artist: TextView = itemView.findViewById(R.id.artist)

            // sets the image to the imageview from our itemHolder class
            if(item.strArtistThumb != null){
                Picasso.with(itemView.context).load(item.strArtistThumb).into(picture)
            } else {
                Picasso.with(itemView.context).load(R.drawable.ic_avatar).into(picture)
            }
            artist.text = item.strArtist

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, ArtistDetailsActivity::class.java)
                intent.putExtra("artistId", item.idArtist)
                intent.putExtra("artistName", item.strArtist)
                Log.d("INTENT", intent.extras.toString())
                itemView.context?.startActivity(intent)
            }
        }

        private fun bindAlbum(item: FavoriteRecyclerDataModel.AlbumFav) {
            var picture: ImageView = itemView.findViewById(R.id.picture)
            var album: TextView = itemView.findViewById(R.id.album)
            var artist: TextView = itemView.findViewById(R.id.artist)

            // sets the image to the imageview from our itemHolder class
            if(item.strAlbumThumb != null){
                Picasso.with(itemView.context).load(item.strAlbumThumb).into(picture)
            } else {
                Picasso.with(itemView.context).load(R.drawable.ic_album).into(picture)
            }
            artist.text = item.strArtist
            album.text = item.strAlbum

            itemView.setOnClickListener {
                val intent = Intent(itemView.context, AlbumDetailsActivity::class.java)
                intent.putExtra("albumId", item.idAlbum)
                Log.d("INTENT", intent.extras.toString())
                itemView.context?.startActivity(intent)
            }
        }

        fun bind(dataModel: FavoriteRecyclerDataModel) {
            when (dataModel) {
                is FavoriteRecyclerDataModel.ArtistFav -> bindArtist(dataModel)
                is FavoriteRecyclerDataModel.AlbumFav -> bindAlbum(dataModel)
                else -> throw IllegalArgumentException("Invalid type")
            }
        }
    }

        fun setData(data: List<FavoriteRecyclerDataModel>) {
            artistsList.apply {
                clear()
                addAll(data)
            }
        }

        companion object {
            private const val TYPE_ARTIST = 0
            private const val TYPE_ALBUM = 1
        }

}