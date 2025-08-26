package com.example.moodmuseplus.auth

import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.moodmuseplus.R

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)

        val etName = findViewById<EditText>(R.id.et_name)
        val etEmail = findViewById<EditText>(R.id.et_email)
        val etPassword = findViewById<EditText>(R.id.et_password)
        val etConfirm = findViewById<EditText>(R.id.et_confirm)
        val cbTerms = findViewById<CheckBox>(R.id.cb_terms)
        val btnCreate = findViewById<Button>(R.id.btn_create_account)
        val tvHaveAccount = findViewById<TextView>(R.id.tv_have_account)

        btnCreate.setOnClickListener {
            val name = etName.text?.toString()?.trim().orEmpty()
            val email = etEmail.text?.toString()?.trim().orEmpty()
            val pass = etPassword.text?.toString().orEmpty()
            val confirm = etConfirm.text?.toString().orEmpty()

            when {
                name.isBlank() -> etName.error = getString(R.string.error_name_required)
                !Patterns.EMAIL_ADDRESS.matcher(email).matches() ->
                    etEmail.error = getString(R.string.error_email_invalid)
                pass.length < 6 -> etPassword.error = getString(R.string.error_password_short)
                pass != confirm -> etConfirm.error = getString(R.string.error_password_mismatch)
                !cbTerms.isChecked -> Toast.makeText(this, getString(R.string.error_terms_required), Toast.LENGTH_SHORT).show()
                else -> {
                    Toast.makeText(this, getString(R.string.signup_success_demo), Toast.LENGTH_SHORT).show()
                    finish() // return to Login; swap to Home later if you prefer
                }
            }
        }

        tvHaveAccount.setOnClickListener { finish() } // back to Login
    }
}
