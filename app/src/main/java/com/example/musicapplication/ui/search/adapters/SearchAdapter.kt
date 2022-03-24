package com.example.musicapplication.ui.search.adapters

import android.content.Context
import android.content.Intent
import android.text.method.TextKeyListener.clear
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapplication.R
import com.example.musicapplication.ui.networks.Album
import com.example.musicapplication.ui.networks.Artist
import com.example.musicapplication.ui.rankings.AlbumDetailsActivity
import com.example.musicapplication.ui.rankings.ArtistDetailsActivity
import com.example.musicapplication.ui.rankings.adapters.ArtistAlbumsAdapter
import com.example.musicapplication.ui.search.models.SearchRecyclerDataModel
import com.squareup.picasso.Picasso
import java.util.Collections.addAll

class SearchAdapter() : RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    private var context: Context? = null
    private val mList = mutableListOf<SearchRecyclerDataModel>()

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val layout = when (viewType) {
            TYPE_ARTIST -> R.layout.search_artists_list
            TYPE_ALBUM -> R.layout.search_albums_list
            else -> throw IllegalArgumentException("Invalid type")
        }
        Log.d("layout", viewType.toString())
        val view = LayoutInflater.from(parent.context)
            .inflate(layout, parent, false)

        context = parent.context

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mList[position])
    }

    // return the number of the items in the list
    override fun getItemCount(): Int = mList.size

    override fun getItemViewType(position: Int): Int {
        return when (mList[position]) {
            is SearchRecyclerDataModel.Artist -> TYPE_ARTIST
            is SearchRecyclerDataModel.Album -> TYPE_ALBUM
            else -> TYPE_ARTIST
        }
    }

    // Holds the views for adding it to image and text
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private fun bindArtist(item: SearchRecyclerDataModel.Artist) {
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

        private fun bindAlbum(item: SearchRecyclerDataModel.Album) {
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

        fun bind(dataModel: SearchRecyclerDataModel) {
            when (dataModel) {
                is SearchRecyclerDataModel.Artist -> bindArtist(dataModel)
                is SearchRecyclerDataModel.Album -> bindAlbum(dataModel)
                else -> throw IllegalArgumentException("Invalid type")
            }
        }

    }

    fun setData(data: List<SearchRecyclerDataModel>) {
        mList.apply {
            clear()
            addAll(data)
        }
    }

    companion object {
        private const val TYPE_ARTIST = 0
        private const val TYPE_ALBUM = 1
    }
}