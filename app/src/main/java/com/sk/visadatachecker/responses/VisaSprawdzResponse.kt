package com.sk.visadatachecker.responses

import com.google.gson.annotations.SerializedName

class VisaSprawdzResponse {

    @SerializedName("ok")
    var ok: Boolean? = null

    @SerializedName("token")
    var token: String? = null
}