package com.example.unsplash_app_practice.recyclerview

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.unsplash_app_practice.App
import com.example.unsplash_app_practice.R
import com.example.unsplash_app_practice.model.Photo

class PhotoItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    private val photoImageView = itemView.findViewById<ImageView>(R.id.photo_img)
    private val photoCreateAtText = itemView.findViewById<TextView>(R.id.create_at_text)
    private val photoLikesCountText = itemView.findViewById<TextView>(R.id.likes_count_text)

    fun bindWithView(photoItem: Photo){
        photoCreateAtText.text = photoItem.createdAt
        photoLikesCountText.text = photoItem.likesCount.toString()
        //이미지 설정
        Glide.with(App.instance)
            .load(photoItem.thumnail)
            .placeholder(R.drawable.ic_baseline_insert_photo_24)
            .into(photoImageView)
    }
}