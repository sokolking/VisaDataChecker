package com.sk.visadatachecker.requests

import com.google.gson.annotations.SerializedName

class CapchaRequest(
    @SerializedName("key") var key: String? = "cfb9d9eb3ca39338a79c412082065df6",
    @SerializedName("method") var method: String? = "base64",
    @SerializedName("body") var body: String? = null,
    @SerializedName("regsense") var regsense: Int? = 1,
    @SerializedName("min_len") var minLen: Int? = 4,
    @SerializedName("language") var language: Int? = 2,
    @SerializedName("json") var json: Int? = 1
)
