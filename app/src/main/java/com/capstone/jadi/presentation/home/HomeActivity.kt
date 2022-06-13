package com.capstone.jadi.presentation.home

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.capstone.jadi.R
import com.capstone.jadi.databinding.ActivityHomeBinding
import com.capstone.jadi.ml.camera.ScanDiseaseActivity
import com.capstone.jadi.presentation.fragment.AkunFragment
import com.capstone.jadi.presentation.fragment.HomeFragment
import com.capstone.jadi.utils.SessionManager
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private var actityHomeBinding: ActivityHomeBinding? = null
    private val binding get() = actityHomeBinding!!

    private lateinit var pref: SessionManager

    companion object {

        fun start(context: Context) {
            val intent = Intent(context, HomeActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        actityHomeBinding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(actityHomeBinding?.root)

        pref = SessionManager(this)

        loadFragment(HomeFragment())

        binding.buttomNav.setOnNavigationItemSelectedListener(this)

        initAction()
    }

    private fun initAction() {
        binding.btnCamera.setOnClickListener {
            ScanDiseaseActivity.start(this)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_home -> loadFragment(HomeFragment())
            R.id.nav_akun -> loadFragment(AkunFragment())
        }
        return true
    }

    private fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_layout,fragment)
        transaction.commit()
    }



}