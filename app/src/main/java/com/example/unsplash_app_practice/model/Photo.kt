package com.example.unsplash_app_practice.model

import java.io.Serializable

data class Photo(var thumnail:String?,
                  var author: String?,
                  var createdAt:String?,
                  var likesCount: Int?):Serializable