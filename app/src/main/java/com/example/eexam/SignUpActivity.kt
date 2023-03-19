package com.example.eexam

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {

    lateinit var signUpButton: Button
    lateinit var edtName: TextInputEditText
    lateinit var edtEmail: TextInputEditText
    lateinit var edtPassword: TextInputEditText
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var dRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        firebaseAuth = FirebaseAuth.getInstance()

        signUpButton = findViewById(R.id.btn_signUp)
        edtName = findViewById(R.id.edt_name_text)
        edtEmail = findViewById(R.id.edt_email_text)
        edtPassword = findViewById(R.id.edt_password_text)

        signUpButton.setOnClickListener {
            val editName = edtName.text.toString().trim()
            val editEmail = edtEmail.text.toString().trim()
            val editPassword = edtPassword.text.toString().trim()

            if (TextUtils.isEmpty(editName)) {
                edtName.error = "required"
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(editEmail)) {
                edtEmail.error = "required"
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(editPassword)) {
                edtPassword.error = "required"
                return@setOnClickListener
            }

            signUp(editName, editEmail, editPassword)

        }
    }

    private fun signUp(editName: String, editEmail: String, editPassword: String) {
        firebaseAuth.createUserWithEmailAndPassword(editEmail, editPassword)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    addUserToDatabase(editEmail, editName, firebaseAuth.currentUser?.uid!!)
                    val intent = Intent(this, MainActivity::class.java)
                    finish()
                    startActivity(intent)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addUserToDatabase(editEmail: String, editName: String, uid: String) {
        dRef = FirebaseDatabase.getInstance().reference
        dRef.child("user").child(uid).setValue(User(editEmail, editName, uid))
    }
}
