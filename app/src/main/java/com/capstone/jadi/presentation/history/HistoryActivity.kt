package com.capstone.jadi.presentation.history

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.jadi.R
import com.capstone.jadi.data.remote.ApiResponse
import com.capstone.jadi.databinding.ActivityHistoryBinding
import com.capstone.jadi.ml.ClassifierViewModel
import com.capstone.jadi.utils.SessionManager
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class HistoryActivity : AppCompatActivity() {

    private val classifierViewModel: ClassifierViewModel by viewModels()

    private var activityHistoryBinding: ActivityHistoryBinding? = null
    private val binding get() = activityHistoryBinding!!

    private lateinit var pref: SessionManager
    private var token: String? = null

    companion object {

        fun start(context: Context) {
            val intent = Intent(context, HistoryActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityHistoryBinding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(activityHistoryBinding?.root)

        pref = SessionManager(this)
        token = pref.getToken

        //initAction()
        initUI()

        isLoading(true)

        getAllHistories("Bearer $token")
    }

    private fun initUI() {
        binding.rvHistory.layoutManager = LinearLayoutManager(this)
        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun getAllHistories(token: String) {
        classifierViewModel.getAllHistories(token).observe(this){ response ->
            when(response){
                is ApiResponse.Loading -> isLoading(true)
                is ApiResponse.Success -> {
                    isLoading(false)
                    val adapter = HistoryAdapter(response.data.listHistory)
                    binding.rvHistory.adapter = adapter
                }
                is ApiResponse.Error -> isLoading(false)
                else -> {
                    Timber.e(getString(R.string.message_unknown_state))
                }
            }
        }
    }

    private fun isLoading(loading: Boolean) {
        if (loading) {
            binding.apply {
                rvHistory.visibility = View.INVISIBLE
            }
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.apply {
                rvHistory.visibility = View.VISIBLE
            }
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onResume() {
        super.onResume()
        getAllHistories("Bearer $token")
    }


}