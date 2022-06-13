package com.capstone.jadi.presentation.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.capstone.jadi.R
import com.capstone.jadi.presentation.login.LoginActivity
import com.capstone.jadi.utils.SessionManager

class AkunFragment : Fragment() {

    private lateinit var pref: SessionManager

    lateinit var btnLogout: Button
    lateinit var btnUpdate: Button
    lateinit var textUsername: TextView
    lateinit var textEmail: TextView

    companion object {

        fun start(context: Context) {
            val intent = Intent(context, AkunFragment::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_akun, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pref = SessionManager(requireActivity())

        btnLogout= view.findViewById(R.id.btnLogout)
        btnUpdate= view.findViewById(R.id.btnUpdate)
        textUsername = view.findViewById(R.id.tvUserName)
        textEmail= view.findViewById(R.id.tvUserEmail)

        initUI()
        initAction()
    }

    private fun initUI() {

        textUsername.text = pref.getUserName
        textEmail.text = pref.getEmail
    }

    private fun initAction() {
        btnLogout.setOnClickListener {
            openLogoutDialog()
        }
    }

    private fun openLogoutDialog() {
        val alertDialog = AlertDialog.Builder(requireActivity())
        alertDialog.setTitle(getString(R.string.message_logout_confirm))
            ?.setPositiveButton(getString(R.string.action_yes)) { _, _ ->
                pref.clearPreferences()
                val intent = Intent(activity, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                startActivity(intent)

            }
            ?.setNegativeButton(getString(R.string.action_cancel), null)
        val alert = alertDialog.create()
        alert.show()
    }

}