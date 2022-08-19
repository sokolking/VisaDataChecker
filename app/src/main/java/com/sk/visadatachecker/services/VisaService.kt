package com.sk.visadatachecker.services

import com.sk.visadatachecker.requests.VisaCapchaRequest
import com.sk.visadatachecker.requests.VisaSlotsRequest
import com.sk.visadatachecker.requests.VisaSprawdzRequest
import com.sk.visadatachecker.responses.VisaCapchaResponse
import com.sk.visadatachecker.responses.VisaSlotsResponse
import com.sk.visadatachecker.responses.VisaSprawdzResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path

interface VisaService {
    @POST("u-captcha/generuj")
    fun getCapcha(@Body body: VisaCapchaRequest): Observable<VisaCapchaResponse>

    @POST("u-captcha/sprawdz")
    fun getSprawdz(@Body body: VisaSprawdzRequest): Observable<VisaSprawdzResponse>

    @POST("rezerwacja-wizyt-wizowych/terminy/{id}")
    fun getSlots(
        @Body body: VisaSlotsRequest,
        @Path("id") id: String?
    ): Observable<VisaSlotsResponse>
}