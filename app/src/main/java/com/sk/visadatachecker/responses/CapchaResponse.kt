package com.sk.visadatachecker.responses

import com.google.gson.annotations.SerializedName

class CapchaResponse {

    @SerializedName("status")
    var status: Int? = null

    @SerializedName("request")
    var request: String? = null
}