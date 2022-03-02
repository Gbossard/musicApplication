package com.example.musicapplication.ui.rankings.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapplication.R
import com.example.musicapplication.ui.networks.Album
import com.example.musicapplication.ui.networks.Title
import com.example.musicapplication.ui.rankings.AlbumDetailsActivity
import com.example.musicapplication.ui.rankings.ArtistDetailsActivity
import com.squareup.picasso.Picasso

class ArtistAlbumsAdapter(private val mList: List<Album>) : RecyclerView.Adapter<ArtistAlbumsAdapter.ViewHolder>() {

    private var context: Context? = null

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_artist_details_album, parent, false)

        context = parent.context

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var albumsViewModel = mList[position]

        // sets the image to the imageview from our itemHolder class
        Picasso.with(context).load(albumsViewModel.strAlbumThumb).into(holder.picture)

        // sets the text to the textview from our itemHolder class
        holder.year.text = albumsViewModel.intYearReleased
        holder.album.text = albumsViewModel.strAlbum

        holder.itemView.setOnClickListener {
            val intent = Intent(context, AlbumDetailsActivity::class.java)
            intent.putExtra("albumId", albumsViewModel.idAlbum)
            Log.d("INTENT", intent.extras.toString())
            context?.startActivity(intent)
        }

    }

    // return the number of the items in the list
    override fun getItemCount(): Int = mList.size

    // Holds the views for adding it to image and text
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var picture: ImageView = itemView.findViewById(R.id.picture)
        var album: TextView = itemView.findViewById(R.id.album)
        var year: TextView = itemView.findViewById(R.id.year)

    }


}