package com.sk.visadatachecker.repositories

import com.sk.visadatachecker.requests.VisaCapchaRequest
import com.sk.visadatachecker.requests.VisaSlotsRequest
import com.sk.visadatachecker.requests.VisaSprawdzRequest
import com.sk.visadatachecker.responses.VisaCapchaResponse
import com.sk.visadatachecker.services.builders.VisaBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class VisaRepository(private val callback: VisaCallback) {

    fun getCapcha() {
        CompositeDisposable().add(
            VisaBuilder.buildService().getCapcha(VisaCapchaRequest())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { response -> callback.onVisaCapchaResponse(response) },
                    { t -> callback.onFailure(t) })
        )
    }

    fun getSprawdz(code: String, token: String) {
        CompositeDisposable().add(
            VisaBuilder.buildService().getSprawdz(VisaSprawdzRequest(code, token))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { response ->
                        if (response.token.isNullOrEmpty()) {
                            callback.onVisaCapchaParseRetry()
                        } else {
                            callback.onVisaTokenResponse(response?.token ?: "")
                        }
                    },
                    { t -> callback.onFailure(t) })
        )
    }


    fun getSlots(token: String, placeId: String) {
        CompositeDisposable().add(
            VisaBuilder.buildService().getSlots(VisaSlotsRequest(token), placeId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { response -> callback.onPlacesParsed(response?.tabelaDni?.size ?: 0) },
                    { t -> callback.onFailure(t) })
        )
    }

}

interface VisaCallback {
    fun onVisaCapchaResponse(response: VisaCapchaResponse)
    fun onVisaCapchaParseRetry()
    fun onVisaTokenResponse(token: String)
    fun onPlacesParsed(placesCount: Int)
    fun onFailure(t: Throwable)
}