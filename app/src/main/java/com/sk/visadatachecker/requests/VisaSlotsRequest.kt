package com.sk.visadatachecker.requests

import com.google.gson.annotations.SerializedName

class VisaSlotsRequest(
    @SerializedName("token") var token: String? = null
)
