package com.sk.visadatachecker.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentTransaction
import com.sk.visadatachecker.R


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setFragment()
    }

    private fun setFragment() {
        val t: FragmentTransaction = supportFragmentManager.beginTransaction()
        t.replace(R.id.mainContainer, VisaDatesFragment.newInstance())
        t.commit()
    }

}