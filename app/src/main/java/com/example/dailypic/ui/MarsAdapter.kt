package com.example.dailypic.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import com.example.dailypic.R
import com.example.dailypic.model.marsModel.Photo

class MarsAdapter(private val photos: MutableList<Photo>, val listener: RVClickListener) :
    RecyclerView.Adapter<MarsAdapter.MarsViewHolder>() {

    class MarsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val header: TextView = itemView.findViewById(R.id.tv_description_header)
        val description: TextView = itemView.findViewById(R.id.tv_description)
        val picture: ImageView = itemView.findViewById(R.id.img_mars)
        val delete: ImageView = itemView.findViewById(R.id.img_mars_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarsViewHolder {
        return MarsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.rv_mars_photos_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MarsViewHolder, position: Int) {
        holder.description.text = photos[position].earthDate
        holder.header.text = photos[position].camera.fullName
        holder.picture.load(photos[position].imgSrc) {
            error(R.drawable.ic_load_error_vector)
            placeholder(R.drawable.ic_no_photo_foreground)
        }
        holder.picture.setOnClickListener { listener.onItemClick(photos[position]) }
        holder.delete.setOnClickListener {
            photos.removeAt(position)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return photos.size
    }
}