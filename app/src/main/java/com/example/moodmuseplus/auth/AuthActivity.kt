package com.example.moodmuseplus.auth

import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.moodmuseplus.R
import android.content.Intent

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_auth)

        val etEmail = findViewById<EditText>(R.id.et_email)
        val etPassword = findViewById<EditText>(R.id.et_password)
        val btnSignIn = findViewById<Button>(R.id.btn_sign_in)
        val tvCreateAccount = findViewById<TextView>(R.id.tv_create_account)
        val tvForgot = findViewById<TextView>(R.id.tv_forgot)

        btnSignIn.setOnClickListener {
            val email = etEmail.text?.toString()?.trim().orEmpty()
            val pass = etPassword.text?.toString().orEmpty()

            when {
                !Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
                    etEmail.error = getString(R.string.error_email_invalid)
                pass.length < 6 ->
                    etPassword.error = getString(R.string.error_password_short)
                else -> {
                    Toast.makeText(this, getString(R.string.sign_in_success), Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, com.example.moodmuseplus.home.HomeActivity::class.java))
                    finishAffinity()
                }
            }
        }

        tvCreateAccount.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }


        tvForgot.setOnClickListener {
            Toast.makeText(this, getString(R.string.toast_forgot_link), Toast.LENGTH_SHORT).show()
        }
    }
}
