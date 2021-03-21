package com.example.webservices2

import com.google.gson.annotations.SerializedName
import java.net.URL

data class DataModel(
    @SerializedName("name")
    val postName: String,

    @SerializedName("message")
    val postMsg: String,

    @SerializedName("profileImage")
    val postImageURL : String
    //val postImageURL: URL
)
