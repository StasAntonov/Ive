package com.example.ive.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ive.databinding.RecyclerVerticalPhotoBinding
import com.example.ive.network.model.PhotoGallery
import com.example.ive.ui.listeners.OnclickListeners
import com.squareup.picasso.Picasso

class PhotoUserGalleryAdapter(
    val listeners: OnclickListeners<PhotoGallery>
) : RecyclerView.Adapter<PhotoUserGalleryAdapter.ViewHolder>() {

    private val listPhoto = mutableListOf<PhotoGallery>()

    class ViewHolder(
        val binding: RecyclerVerticalPhotoBinding
    ) : RecyclerView.ViewHolder(binding.root){

        fun bind(item: PhotoGallery, listener: OnclickListeners<PhotoGallery>) {
            Picasso.get()
                .load(item.cover_photo.urls?.regular)
                .into(binding.aiImage)
            binding.aiImage.setOnClickListener { listener.onClick(item,1) }
        }

    }

    fun submitList(list: List<PhotoGallery>) {
        listPhoto.addAll(list)
        notifyDataSetChanged()
    }

    fun removeList(){
        listPhoto.clear()
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = RecyclerVerticalPhotoBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount() = listPhoto.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listPhoto[position],listeners)
    }

}