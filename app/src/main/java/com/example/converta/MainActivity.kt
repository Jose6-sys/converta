package com.example.converta

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.converta.auth.AuthManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class MainActivity : AppCompatActivity() {

    private lateinit var emailField: EditText
    private lateinit var passwordField: EditText
    private lateinit var loginButton: Button
    private lateinit var registerLink: TextView
    private lateinit var googleSignInButton: Button
    private lateinit var progressDialog: ProgressDialog

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    private val GOOGLE_SIGN_IN_REQUEST = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        emailField = findViewById(R.id.emailField)
        passwordField = findViewById(R.id.passwordField)
        loginButton = findViewById(R.id.loginButton)
        registerLink = findViewById(R.id.registerLink)
        googleSignInButton = findViewById(R.id.googleSignInButton)

        progressDialog = ProgressDialog(this).apply {
            setMessage("Logging in, please wait...")
            setCancelable(false)
        }

        auth = FirebaseAuth.getInstance()

        // Configure Google Sign-In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // Email/Password login
        loginButton.setOnClickListener {
            val email = emailField.text.toString().trim()
            val password = passwordField.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show()
            } else {
                progressDialog.show()
                AuthManager.login(email, password) { success: Boolean, error: String? ->
                    progressDialog.dismiss()
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

        // Google Sign-In button click
        googleSignInButton.setOnClickListener {
            val signInIntent = googleSignInClient.signInIntent
            startActivityForResult(signInIntent, GOOGLE_SIGN_IN_REQUEST)
        }

        registerLink.setOnClickListener {
            startActivity(Intent(this, Register::class.java))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == GOOGLE_SIGN_IN_REQUEST) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            if (task.isSuccessful) {
                val account = task.result
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                progressDialog.show()
                auth.signInWithCredential(credential).addOnCompleteListener { signInTask ->
                    progressDialog.dismiss()
                    if (signInTask.isSuccessful) {
                        Toast.makeText(this, "Signed in as ${account.email}", Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this, Converter::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "Google sign-in failed.", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Google sign-in cancelled.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
