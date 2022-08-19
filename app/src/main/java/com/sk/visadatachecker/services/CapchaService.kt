package com.sk.visadatachecker.services

import com.sk.visadatachecker.requests.CapchaRequest
import com.sk.visadatachecker.responses.CapchaResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface CapchaService {
    @POST("in.php")
    fun postCapcha(@Body body: CapchaRequest): Observable<CapchaResponse>

    @GET("res.php")
    fun getCapchaResult(
        @Query("key") key: String? = "cfb9d9eb3ca39338a79c412082065df6",
        @Query("action") action: String? = "get",
        @Query("id") id: String?,
        @Query("json") json: String? = "1",
    ): Observable<CapchaResponse>
}