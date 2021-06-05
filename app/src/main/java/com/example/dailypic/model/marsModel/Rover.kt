package com.example.dailypic.model.marsModel

import com.google.gson.annotations.SerializedName

data class Rover (
    @SerializedName("id") var id : Int,
    @SerializedName("name") var name : String,
    @SerializedName("landing_date") var landingDate : String,
    @SerializedName("launch_date") var launchDate : String,
    @SerializedName("status") var status : String,
)