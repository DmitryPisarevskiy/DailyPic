package com.example.dailypic.model.marsModel

import com.google.gson.annotations.SerializedName

data class Photo (
    @SerializedName("id") var id : Int,
    @SerializedName("sol") var sol : Int,
    @SerializedName("camera") var camera : Camera,
    @SerializedName("img_src") var imgSrc : String,
    @SerializedName("earth_date") var earthDate : String,
    @SerializedName("rover") var rover : Rover,
)