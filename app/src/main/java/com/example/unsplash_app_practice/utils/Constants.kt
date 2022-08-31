package com.example.unsplash_app_practice.utils

object Constants {
    const val TAG: String = "로그"
}

enum class SEARCH_TYPE {
    PHOTO,
    USER
}
enum class RESPONSE_STATE{
    OKAY,
    FAIL,
    NO_CONTENT
}
object API{
    const val BASE_URL : String = "https://api.unsplash.com/"
    const val CLIENT_ID : String = "kbVgM7oHm87cjCoFwEkL0nkb41YqmbLqBeeG_kCqEJ8"
    const val SEARCH_PHOTO : String = "search/photos"
    const val SEARCH_USERS : String = "search/users"
}