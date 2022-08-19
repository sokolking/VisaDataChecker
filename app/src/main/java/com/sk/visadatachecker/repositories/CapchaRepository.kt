package com.sk.visadatachecker.repositories

import com.sk.visadatachecker.requests.CapchaRequest
import com.sk.visadatachecker.services.builders.CapchaBuilder
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class CapchaRepository(private val callback: CapchaCallback) {

    fun postCapcha(capcha: String) {
        CompositeDisposable().add(
            CapchaBuilder.buildService().postCapcha(CapchaRequest(body = capcha))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { response -> getCapchaResult(response.request ?: "") },
                    { t -> callback.onFailure(t) })
        )
    }

    fun getCapchaResult(capchaId: String) {
        CompositeDisposable().add(
            CapchaBuilder.buildService().getCapchaResult(id = capchaId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                    { response ->
                        if (response.request == "CAPCHA_NOT_READY") {
                            callback.onCapchaRestart(capchaId)
                        } else {
                            callback.onCapchaResponse(response.request ?: "")
                        }
                    },
                    { t -> callback.onFailure(t) })
        )
    }

}

interface CapchaCallback {
    fun onCapchaResponse(code: String)
    fun onCapchaRestart(capchaId: String)
    fun onFailure(t: Throwable)
}