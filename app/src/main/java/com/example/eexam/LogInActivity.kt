package com.example.eexam

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class LogInActivity : AppCompatActivity() {

    lateinit var loginButton: Button
    lateinit var signUpButton: Button
    lateinit var edtEmail: TextInputEditText
    lateinit var edtPassword: TextInputEditText
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        firebaseAuth = FirebaseAuth.getInstance()

        if (firebaseAuth.currentUser != null) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        loginButton = findViewById(R.id.btn_login)
        signUpButton = findViewById(R.id.btn_signUp)
        edtEmail = findViewById(R.id.edt_email_text)
        edtPassword = findViewById(R.id.edt_password_text)

        signUpButton.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }


        loginButton.setOnClickListener {
            val editEmail = edtEmail.text.toString().trim()
            val editPassword = edtPassword.text.toString().trim()

            if (TextUtils.isEmpty(editEmail)) {
                edtEmail.error = "required"
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(editPassword)) {
                edtPassword.error = "required"
                return@setOnClickListener
            }

            firebaseAuth.signInWithEmailAndPassword(editEmail, editPassword).addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(this, "Signed In", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                } else {
                    Toast.makeText(this, it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}