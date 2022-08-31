package com.example.unsplash_app_practice.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.unsplash_app_practice.App
import com.example.unsplash_app_practice.R
import com.example.unsplash_app_practice.model.Photo

class PhotoGridRecyclerViewAdapter : RecyclerView.Adapter<PhotoItemViewHolder>(){

    private var photoList = ArrayList<Photo>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoItemViewHolder {
        val phtoItemViewHolder = PhotoItemViewHolder(LayoutInflater
            .from(parent.context)
            .inflate(R.layout.layout_photo_item, parent, false))
        return phtoItemViewHolder
    }

    override fun onBindViewHolder(holder: PhotoItemViewHolder, position: Int) {
        holder.bindWithView(this.photoList[position])
    }

    override fun getItemCount(): Int {
        return this.photoList.size
    }

    //외부에서 어댑터에 넣을 데이터를 넣어줌
    fun submitList(photoList:ArrayList<Photo>){
        this.photoList = photoList
    }

}