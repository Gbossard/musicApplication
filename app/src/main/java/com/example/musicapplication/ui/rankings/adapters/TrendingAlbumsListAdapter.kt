package com.example.musicapplication.ui.rankings.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapplication.R
import com.example.musicapplication.ui.networks.Album
import com.squareup.picasso.Picasso

class TrendingAlbumsListAdapter(private val mList: List<Album>) : RecyclerView.Adapter<TrendingAlbumsListAdapter.ViewHolder>() {

    private var context: Context? = null

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        context = parent.context

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var albumsViewModel = mList[position]

        // sets the image to the imageview from our itemHolder class
        Picasso.with(context).load(albumsViewModel.strAlbumThumb).into(holder.picture)

        // sets the text to the textview from our itemHolder class
        holder.intChartPlace.text = albumsViewModel.intChartPlace
        holder.title.text = albumsViewModel.strAlbum
        holder.strArtist.text = albumsViewModel.strArtist

    }

    // return the number of the items in the list
    override fun getItemCount(): Int = mList.size

    // Holds the views for adding it to image and text
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var picture: ImageView = itemView.findViewById(R.id.picture)
        var intChartPlace: TextView = itemView.findViewById(R.id.intChartPlace)
        var title: TextView = itemView.findViewById(R.id.title)
        var strArtist: TextView = itemView.findViewById(R.id.strArtist)

    }


}