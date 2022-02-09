package com.example.coroutine_and_flow.data.network.responses

import com.google.gson.annotations.SerializedName

class Country(

    @SerializedName("Code")
    val code: Int,

    @SerializedName("Name")
    val name: String,

    @SerializedName("LName")
    val lName: String,

    @SerializedName("Time_Zone")
    val timeZone: Double
)