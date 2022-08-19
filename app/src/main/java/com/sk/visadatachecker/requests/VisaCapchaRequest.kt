package com.sk.visadatachecker.requests

import com.google.gson.annotations.SerializedName

class VisaCapchaRequest(
    @SerializedName("imageWidth") var imageWidth: Int? = 600,
    @SerializedName("imageHeight") var imageHeight: Int? = 300
)
