package com.example.eexam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.eexam.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    var userName = ""
    var uid = ""
    lateinit var binding: ActivityMainBinding
    lateinit var firebaseInstance: FirebaseDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        firebaseInstance = FirebaseDatabase.getInstance()

        binding.logOut.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            Toast.makeText(this,"You have been logged out !",Toast.LENGTH_SHORT).show()
            startActivity(Intent(this,LogInActivity::class.java))
            finish()
        }

        firebaseInstance.reference.child("user").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {


                binding.name.text =
                    "Name : " + snapshot.child(uid!!).child("editName").value.toString()
                binding.id.text = "UID : " + snapshot.child(uid).child("uid").value.toString()
                userName = snapshot.child(uid).child("editName").value.toString()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("error", "onCancelled: error")
            }
        })


    }

    fun giveExam(view: View) {
        when (view.id) {
            R.id.math -> {
                val intent = Intent(this, DetailExam::class.java)
                intent.putExtra("sub", "Math")
                intent.putExtra("name", userName)
                startActivity(intent)
            }
            R.id.dsa -> {
                val intent = Intent(this, DetailExam::class.java)
                intent.putExtra("sub", "Dsa")
                intent.putExtra("name", userName)
                startActivity(intent)
            }
            R.id.biology -> {
                val intent = Intent(this, DetailExam::class.java)
                intent.putExtra("sub", "Biology")
                intent.putExtra("name", userName)
                startActivity(intent)
            }
            R.id.english -> {
                val intent = Intent(this, DetailExam::class.java)
                intent.putExtra("sub", "English")
                intent.putExtra("name", userName)
                startActivity(intent)
            }
        }
    }

    suspend fun mathProgressBar(progress: Int, progressBar: ProgressBar) {
        for (i in 0 until progress) {
            delay(30)
            runOnUiThread {
                progressBar.progress = i
            }
        }
    }

    suspend fun englishProgressBar(progress: Int, progressBar: ProgressBar) {
        for (i in 0 until progress) {
            delay(30)
            runOnUiThread {
                progressBar.progress = i
            }
        }
    }

    suspend fun dsaProgressBar(progress: Int, progressBar: ProgressBar) {
        for (i in 0 until progress) {
            delay(30)
            runOnUiThread {
                progressBar.progress = i
            }
        }
    }

    suspend fun biologyProgressBar(progress: Int, progressBar: ProgressBar) {
        for (i in 0 until progress) {
            delay(30)
            runOnUiThread {
                progressBar.progress = i
            }

        }
    }

    fun getMark() {
        firebaseInstance.reference.child("mark").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.ProgressLayout.visibility = View.GONE
                binding.dataLayout.visibility = View.VISIBLE
                val math = snapshot.child(uid).child("Math").value.toString()
                val english = snapshot.child(uid).child("English").value.toString()
                val biology = snapshot.child(uid).child("Biology").value.toString()
                val dsa = snapshot.child(uid).child("Dsa").value.toString()

                if (snapshot.child(uid).hasChild("Math")) {
                    CoroutineScope(Dispatchers.IO).launch {
                        mathProgressBar(math.toInt(), binding.mathProgress)
                    }
                }


                if (snapshot.child(uid).hasChild("English")) {
                    CoroutineScope(Dispatchers.IO).launch {
                        englishProgressBar(english.toInt(), binding.englishProgress)
                    }
                }


                if (snapshot.child(uid).hasChild("Biology")) {
                    CoroutineScope(Dispatchers.IO).launch {
                        biologyProgressBar(biology.toInt(), binding.biologyProgress)
                    }
                }


                if (snapshot.child(uid).hasChild("Dsa")) {
                    CoroutineScope(Dispatchers.IO).launch {
                        dsaProgressBar(dsa.toInt(), binding.dsaProgress)

                    }
                }


            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("error", "onCancelled: error")
            }
        })

    }


    override fun onResume() {
        super.onResume()
        uid = FirebaseAuth.getInstance().currentUser?.uid.toString()
        getMark()
    }

}