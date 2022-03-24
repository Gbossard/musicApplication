package com.example.musicapplication.ui.rankings.adapters

import android.app.Activity
import android.app.PendingIntent.getActivity
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
import com.example.musicapplication.ui.networks.Title
import com.example.musicapplication.ui.rankings.ArtistDetailsActivity
import com.example.musicapplication.ui.rankings.TitlesFragment
import com.squareup.picasso.Picasso

class TrendingTitlesListAdapter(private val mList: List<Title>) : RecyclerView.Adapter<TrendingTitlesListAdapter.ViewHolder>() {

    private var context: Context? = null

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_trending_item, parent, false)

        context = parent.context

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var titlesViewModel = mList[position]

        // sets the image to the imageview from our itemHolder class
        Picasso.with(context).load(titlesViewModel.strTrackThumb).into(holder.picture)

        // sets the text to the textview from our itemHolder class
        holder.intChartPlace.text = (position + 1).toString()
        holder.title.text = titlesViewModel.strTrack
        holder.strArtist.text = titlesViewModel.strArtist

        holder.strArtist.setOnClickListener {
            val intent = Intent(context, ArtistDetailsActivity::class.java)
            intent.putExtra("artistId", titlesViewModel.idArtist)
            intent.putExtra("artistName", titlesViewModel.strArtist)
            Log.d("INTENT", intent.extras.toString())
            context?.startActivity(intent)
        }

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