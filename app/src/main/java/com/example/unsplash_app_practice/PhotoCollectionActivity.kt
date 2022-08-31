package com.example.unsplash_app_practice

import android.os.Bundle
import android.util.Log
import android.widget.GridLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.unsplash_app_practice.databinding.ActivityPhothCollectionBinding
import com.example.unsplash_app_practice.model.Photo
import com.example.unsplash_app_practice.recyclerview.PhotoGridRecyclerViewAdapter
import com.example.unsplash_app_practice.utils.Constants.TAG

class PhotoCollectionActivity : AppCompatActivity() {

    private lateinit var phothCollectionBinding: ActivityPhothCollectionBinding
    var photoList = ArrayList<Photo>()
    private lateinit var photoGridRecyclerViewAdapter: PhotoGridRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        phothCollectionBinding = ActivityPhothCollectionBinding.inflate(layoutInflater)
        setContentView(phothCollectionBinding.root)

        Log.d(TAG, "photoCollect onCreate")

        val bundle = intent.getBundleExtra("array_bundle")
        val searchTerm = intent.getStringExtra("search_term")

        photoList = bundle?.getSerializable("photo_array_list") as ArrayList<Photo>

        phothCollectionBinding.topAppBar.title = searchTerm

        this.photoGridRecyclerViewAdapter = PhotoGridRecyclerViewAdapter()
        this.photoGridRecyclerViewAdapter.submitList(photoList)
        phothCollectionBinding.myPhotoRecyclerView.layoutManager = GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false)
        phothCollectionBinding.myPhotoRecyclerView.adapter = this.photoGridRecyclerViewAdapter

    }
}