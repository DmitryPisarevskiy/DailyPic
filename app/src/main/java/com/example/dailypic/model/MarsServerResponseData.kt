package com.example.dailypic.model

import com.example.dailypic.model.marsModel.Photo
import com.google.gson.annotations.SerializedName

data class MarsServerResponseData(
    @SerializedName("photos") val photos: MutableList<Photo>
)

