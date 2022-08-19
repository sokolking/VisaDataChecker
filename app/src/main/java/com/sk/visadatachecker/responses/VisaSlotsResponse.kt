package com.sk.visadatachecker.responses

import com.google.gson.annotations.SerializedName

class VisaSlotsResponse {

    @SerializedName("idLokalizacji")
    var idLokalizacji: Int? = null

    @SerializedName("idPlacowki")
    var idPlacowki: Int? = null

    @SerializedName("rodzajUslugi")
    var rodzajUslugi: Int? = null

    @SerializedName("tabelaDni")
    var tabelaDni: List<Any>? = null

    @SerializedName("token")
    var token: String? = null
}