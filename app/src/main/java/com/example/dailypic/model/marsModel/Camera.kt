package com.example.dailypic.model.marsModel

import com.google.gson.annotations.SerializedName

data class Camera (
    @SerializedName("id") var id : Int,
    @SerializedName("name") var name : String,
    @SerializedName("rover_id") var roverId : Int,
    @SerializedName("full_name") var fullName : String,
)