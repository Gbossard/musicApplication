package com.example.musicapplication.ui.rankings.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.musicapplication.R
import com.example.musicapplication.ui.networks.Title

class ArtistTitlesAdapter(private val mList: List<Title>) : RecyclerView.Adapter<ArtistTitlesAdapter.ViewHolder>() {

    private var context: Context? = null

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_artist_details_titles, parent, false)

        context = parent.context

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var titlesViewModel = mList[position]

        // sets the text to the textview from our itemHolder class
        holder.title.text = titlesViewModel.strTrack
        holder.count.text = (position + 1).toString()

        /*holder.itemView.setOnClickListener {
            val intent = Intent(context, ArtistDetailsActivity::class.java)
            intent.putExtra("artist", titlesViewModel.idArtist)
            intent.putExtra("album", titlesViewModel.idAlbum)
            Log.d("INTENT", intent.extras.toString())
            context?.startActivity(intent)
        }*/

    }

    // return the number of the items in the list
    override fun getItemCount(): Int = mList.size

    // Holds the views for adding it to image and text
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var title: TextView = itemView.findViewById(R.id.title)
        var count: TextView = itemView.findViewById(R.id.count)

    }


}