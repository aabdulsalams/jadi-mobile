package com.capstone.jadi.ml

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.jadi.R
import com.capstone.jadi.databinding.ActivityDetailBinding
import com.capstone.jadi.databinding.ActivityDetailClassifierBinding
import com.capstone.jadi.utils.SessionManager
import com.capstone.jadi.utils.ext.setImageUrl

class DetailClassifier : AppCompatActivity() {

    private var activityDetailClassifierBinding: ActivityDetailClassifierBinding? = null
    private val binding get() = activityDetailClassifierBinding!!

    private lateinit var pref: SessionManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDetailClassifierBinding = ActivityDetailClassifierBinding.inflate(layoutInflater)
        setContentView(activityDetailClassifierBinding?.root)

        pref = SessionManager(this)

        initIntent()
        initUI()
    }

    private fun initUI() {
    }

    private fun initIntent() {
        TODO("Not yet implemented")
    }
}