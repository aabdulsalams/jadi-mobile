package com.capstone.jadi.presentation.detail

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.capstone.jadi.R
import com.capstone.jadi.data.model.History
import com.capstone.jadi.databinding.ActivityDetailBinding
import com.capstone.jadi.utils.ConstVal
import com.capstone.jadi.utils.SessionManager
import com.capstone.jadi.utils.ext.setImageUrl

class DetailActivity : AppCompatActivity(){


    private var activityDetailBinding: ActivityDetailBinding? = null
    private val binding get() = activityDetailBinding!!

    private var token: String? = null
    private lateinit var pref: SessionManager

    private lateinit var history: History

    companion object{
        fun start(context: Context){
            val intent = Intent(context, DetailActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(activityDetailBinding?.root)

        pref = SessionManager(this)
        token = pref.getToken

        initIntent()
        initUI()


    }


    private fun showLoading(b: Boolean) {
        if (b){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }

    private fun initUI() {
        binding.apply {
            imgPreview.setImageUrl(history.photoUrl, true)
            tvPrediction.text = history.disease_name

            if (history.disease_name == "Bacterial Leaf Blight"){
                tvDescription.text = getString(R.string.BLB_desc)
                tvControl.text = getString(R.string.BLB_control)
                tvCegah.text = getString(R.string.BLB_cegah)
            }else if (history.disease_name == "Brown Spot"){
                tvDescription.text = getString(R.string.BS_desc)
                tvControl.text = getString(R.string.BS_control)
                tvCegah.text = getString(R.string.BS_cegah)
            }else{
                tvDescription.text = getString(R.string.LS_desc)
                tvControl.text = getString(R.string.LS_control)
                tvCegah.text = getString(R.string.LS_cegah)
            }

        }

        binding.expandBtn.setOnClickListener{
            if (binding.expandableLayout.visibility == View.GONE){
                TransitionManager.beginDelayedTransition(binding.cardView, AutoTransition())
                binding.expandableLayout.visibility = View.VISIBLE
                binding.expandBtn.setImageResource(R.drawable.ic_expand_less)
            }else{
                TransitionManager.beginDelayedTransition(binding.cardView, AutoTransition())
                binding.expandableLayout.visibility = View.GONE
                binding.expandBtn.setImageResource(R.drawable.ic_expand_more)
            }
        }

        binding.expandBtn2.setOnClickListener{
            if (binding.expandableLayout2.visibility == View.GONE){
                TransitionManager.beginDelayedTransition(binding.cardView2, AutoTransition())
                binding.expandableLayout2.visibility = View.VISIBLE
                binding.expandBtn2.setImageResource(R.drawable.ic_expand_less)
            }else{
                TransitionManager.beginDelayedTransition(binding.cardView2, AutoTransition())
                binding.expandableLayout2.visibility = View.GONE
                binding.expandBtn2.setImageResource(R.drawable.ic_expand_more)
            }
        }

        binding.expandBtn3.setOnClickListener{
            if (binding.expandableLayout3.visibility == View.GONE){
                TransitionManager.beginDelayedTransition(binding.cardView3, AutoTransition())
                binding.expandableLayout3.visibility = View.VISIBLE
                binding.expandBtn3.setImageResource(R.drawable.ic_expand_less)
            }else{
                TransitionManager.beginDelayedTransition(binding.cardView3, AutoTransition())
                binding.expandableLayout3.visibility = View.GONE
                binding.expandBtn3.setImageResource(R.drawable.ic_expand_more)
            }
        }

        binding.btnBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun initIntent() {
        history = intent.getParcelableExtra(ConstVal.BUNDLE_KEY_HISTORY)!!
    }


}