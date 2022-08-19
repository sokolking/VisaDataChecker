package com.sk.visadatachecker.responses

import com.google.gson.annotations.SerializedName

class VisaCapchaResponse {

    @SerializedName("id")
    var id: String? = null

    @SerializedName("iloscZnakow")
    var iloscZnakow: Int? = null

    @SerializedName("image")
    var image: String? = null
}