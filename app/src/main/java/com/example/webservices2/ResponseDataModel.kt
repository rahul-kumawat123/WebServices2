package com.example.webservices2

import com.google.gson.annotations.SerializedName

data class ResponseDataModel(
        @SerializedName("posts")
        val posts: ArrayList<DataModel>
)