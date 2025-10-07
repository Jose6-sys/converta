package com.example.converta

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.converta.auth.AuthManager

class MainActivity : AppCompatActivity() {

    private lateinit var emailField: EditText
    private lateinit var passwordField: EditText
    private lateinit var loginButton: Button
    private lateinit var registerLink: TextView
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        emailField = findViewById(R.id.emailField)
        passwordField = findViewById(R.id.passwordField)
        loginButton = findViewById(R.id.loginButton)
        registerLink = findViewById(R.id.registerLink)

        // Initialize loading dialog
        progressDialog = ProgressDialog(this).apply {
            setMessage("Logging in, please wait...")
            setCancelable(false)
        }

        loginButton.setOnClickListener {
            val email = emailField.text.toString().trim()
            val password = passwordField.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show()
            } else {
                progressDialog.show() // Show loading dialog

                AuthManager.login(email, password) { success: Boolean, error: String? ->
                    progressDialog.dismiss() // Hide after Firebase responds

                    if (success) {
                        Toast.makeText(this, "Login successful!", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, Converter::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Login failed: $error", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }

        registerLink.setOnClickListener {
            startActivity(Intent(this, Register::class.java))
        }
    }
}
