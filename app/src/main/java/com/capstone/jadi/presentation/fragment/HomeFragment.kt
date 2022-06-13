package com.capstone.jadi.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.capstone.jadi.R
import com.capstone.jadi.ml.camera.ScanDiseaseActivity
import com.capstone.jadi.ml.upload.ImageClassifierActivity
import com.capstone.jadi.presentation.history.HistoryActivity
import com.capstone.jadi.utils.SessionManager

class HomeFragment : Fragment() {

    private lateinit var pref: SessionManager

    lateinit var btnScan : Button
    lateinit var btnUpload : Button
    lateinit var btnHistory : Button
    lateinit var btnAkun : Button
    lateinit var textUsername : TextView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pref = SessionManager(requireActivity())

        btnScan = view.findViewById(R.id.btn_scan)
        btnUpload = view.findViewById(R.id.btn_gambar)
        btnHistory = view.findViewById(R.id.btn_riwayat)
        btnAkun = view.findViewById(R.id.btn_setting)
        textUsername = view.findViewById(R.id.tv_username)

        initUI()
        initAction()
    }

    private fun initUI() {
        textUsername.text = getString(R.string.label_greeting_user, pref.getUserName)
    }

    private fun initAction() {
        btnUpload.setOnClickListener {
            val intent = Intent(activity, ImageClassifierActivity::class.java)
            startActivity(intent)
        }

        btnHistory.setOnClickListener {
            val intent2 = Intent(activity, HistoryActivity::class.java)
            startActivity(intent2)
        }

        btnAkun.setOnClickListener {
            startActivity(Intent(Settings.ACTION_LOCALE_SETTINGS))
        }

        btnScan.setOnClickListener {
            val intent2 = Intent(activity, ScanDiseaseActivity::class.java)
            startActivity(intent2)

        }
    }



}