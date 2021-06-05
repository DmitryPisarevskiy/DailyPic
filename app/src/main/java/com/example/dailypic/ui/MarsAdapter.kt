package com.example.dailypic.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dailypic.R
import com.example.dailypic.model.marsModel.Photo

class MarsAdapter(private val photos: List<Photo>) :
    RecyclerView.Adapter<MarsAdapter.MarsViewHolder>() {

    class MarsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var header: TextView
        lateinit var description: TextView

        init {
            header = itemView.findViewById(R.id.tv_description_header)
            description = itemView.findViewById(R.id.tv_description)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarsViewHolder {
        return MarsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.rv_mars_photos_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MarsViewHolder, position: Int) {
        holder.description.text = photos[0].earthDate
        holder.header.text = photos[0].camera.fullName
    }

    override fun getItemCount(): Int {
        return photos.size
    }
}