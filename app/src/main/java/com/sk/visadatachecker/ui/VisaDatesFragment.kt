package com.sk.visadatachecker.ui

import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Bundle
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.sk.visadatachecker.R
import com.sk.visadatachecker.repositories.CapchaCallback
import com.sk.visadatachecker.repositories.CapchaRepository
import com.sk.visadatachecker.repositories.VisaCallback
import com.sk.visadatachecker.repositories.VisaRepository
import com.sk.visadatachecker.responses.VisaCapchaResponse
import kotlinx.android.synthetic.main.fragment_visa_dates.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.concurrent.fixedRateTimer

class VisaDatesFragment : Fragment(), VisaCallback, CapchaCallback {

    private val coroutineScope = CoroutineScope(Dispatchers.Default)

    private val visaRepository by lazy { VisaRepository(this) }
    private val capchaRepository by lazy { CapchaRepository(this) }

    private var VISA_TOKEN = ""
    private var DELAY = 1000L * 60 * 60

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(R.layout.fragment_visa_dates, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fixedRateTimer("timer", false, 0L, DELAY) { onStartFlow() }
        capcha.setOnClickListener { onStartFlow() }
    }

    private fun onStartFlow() {
        status.setTextColor(Color.WHITE)
        setStatus(R.string.status_get_capcha)
        imageStatus.isVisible = false
        progress.isVisible = true
        visaRepository.getCapcha()
    }

    private fun setStatus(statusText: Int) = status.setText(statusText)

    private fun getPlaceId(): String {
        if (rb1.isChecked) return "1415"
        if (rb2.isChecked) return "1432"
        if (rb3.isChecked) return "1433"
        if (rb4.isChecked) return "1435"
        if (rb5.isChecked) return "1436"
        return ""
    }

    override fun onVisaCapchaResponse(response: VisaCapchaResponse) {
        val decodedString: ByteArray = Base64.decode(response.image, Base64.DEFAULT)
        val decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        progress.isVisible = false
        setStatus(R.string.status_got_capcha)
        capcha.setImageBitmap(decodedByte)
        setStatus(R.string.status_analyze_capcha)
        VISA_TOKEN = response.id ?: ""
        capchaRepository.postCapcha(response.image ?: "")
        capchaProgress.isVisible = true
    }

    override fun onVisaCapchaParseRetry() {
        Toast.makeText(context, R.string.status_failed_parse_capcha, Toast.LENGTH_SHORT).show()
        onStartFlow()
    }

    override fun onVisaTokenResponse(token: String) {
        setStatus(R.string.status_get_slots)
        visaRepository.getSlots(token, getPlaceId())
    }

    override fun onPlacesParsed(placesCount: Int) {
        status.text = getString(R.string.status_got_slots, placesCount.toString())
        val color = if (placesCount > 0) Color.GREEN else Color.RED
        imageStatus.isVisible = true
        capchaProgress.isVisible = false
        imageStatus.setBackgroundColor(color)
        status.setTextColor(color)
    }

    override fun onCapchaResponse(code: String) {
        status.text = getString(R.string.status_capcha_parsed, code)
        visaRepository.getSprawdz(code, VISA_TOKEN)
    }

    override fun onCapchaRestart(capchaId: String) {
        setStatus(R.string.status_retry_analyze_capcha)
        coroutineScope.launch {
            delay(15000L)
            capchaRepository.getCapchaResult(capchaId)
        }
    }

    override fun onFailure(t: Throwable) {
        setStatus(R.string.status_error)
        Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
    }

    companion object {

        fun newInstance() = VisaDatesFragment()
    }

}