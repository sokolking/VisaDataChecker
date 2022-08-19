package com.sk.visadatachecker.requests

import com.google.gson.annotations.SerializedName

class VisaSprawdzRequest(
    @SerializedName("kod") var kod: String? = null,
    @SerializedName("token") var token: String? = null
)
