package com.capstone.jadi.presentation.register

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.capstone.jadi.R.string
import com.capstone.jadi.data.remote.ApiResponse
import com.capstone.jadi.data.remote.auth.AuthBody
import com.capstone.jadi.databinding.ActivityRegisterBinding
import com.capstone.jadi.presentation.login.LoginActivity
import com.capstone.jadi.utils.UiConstValue
import com.capstone.jadi.utils.ext.isEmailValid
import com.capstone.jadi.utils.ext.showOKDialog
import com.capstone.jadi.utils.ext.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterActivity : AppCompatActivity() {

    private val registerViewModel: RegisterViewModel by viewModels()

    private var _activityRegisterBinding: ActivityRegisterBinding? = null
    private val binding get() = _activityRegisterBinding!!

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, RegisterActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _activityRegisterBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(_activityRegisterBinding?.root)

        initAction()
    }

    private fun initAction() {
        binding.btnRegister.setOnClickListener {
            val userName = binding.edtName.text.toString()
            val userEmail = binding.edtEmail.text.toString()
            val userPassword = binding.edtPassword.text.toString()

            Handler(Looper.getMainLooper()).postDelayed({
                when {
                    userName.isBlank() -> binding.edtName.error = getString(string.error_empty_name)
                    userEmail.isBlank() -> binding.edtEmail.error = getString(string.error_empty_email)
                    !userEmail.isEmailValid() -> binding.edtEmail.error = getString(string.error_invalid_email)
                    userPassword.isBlank() -> binding.edtPassword.error = getString(string.error_empty_password)
                    else -> {
                        val request = AuthBody(
                            userName, userEmail, userPassword
                        )
                        registerUser(request)
                    }
                }
            }, UiConstValue.ACTION_DELAYED_TIME)
        }
        binding.tvToLogin.setOnClickListener {
            LoginActivity.start(this)
        }
    }

    private fun registerUser(newUser: AuthBody) {
        registerViewModel.registerUser(newUser).observe(this) { response ->
            when(response) {
                is ApiResponse.Loading -> {
                   //showLoading(true)
                    LoginActivity.start(this)
                    finish()
                    showToast(getString(string.message_register_success))
                }
                is ApiResponse.Success -> {
                    try {
                        showLoading(false)
                    } finally {
                        LoginActivity.start(this)
                        finish()
                        showToast(getString(string.message_register_success))
                    }
                }
                is ApiResponse.Error -> {
                    showLoading(false)
                    showOKDialog(getString(string.title_dialog_error), response.errorMessage)
                }
                else -> {
                    //showToast(getString(string.message_unknown_state))
                    LoginActivity.start(this)
                    finish()
                    showToast(getString(string.message_register_success))
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.bgDim.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.edtName.isClickable = !isLoading
        binding.edtName.isEnabled = !isLoading
        binding.edtEmail.isClickable = !isLoading
        binding.edtEmail.isEnabled = !isLoading
        binding.edtPassword.isClickable = !isLoading
        binding.edtPassword.isEnabled = !isLoading
        binding.btnRegister.isClickable = !isLoading
    }

}