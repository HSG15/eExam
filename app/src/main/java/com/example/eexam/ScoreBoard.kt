package com.example.eexam

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.eexam.databinding.ActivityScoreBoardBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ScoreBoard : AppCompatActivity() {

    lateinit var firebaseInstance: FirebaseDatabase
    lateinit var binding:ActivityScoreBoardBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
     binding=DataBindingUtil.setContentView(this,R.layout.activity_score_board)
        val score=intent.getIntExtra("mark",0)
        val subject=intent.getStringExtra("sub")


        binding.score.text= "Your Score is ${score.toString()}"


        binding.exit.setOnClickListener {
            finish()
        }

        firebaseInstance = FirebaseDatabase.getInstance()
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        if (subject != null) {
            firebaseInstance.reference.child("mark").child(uid.toString()).child(subject).setValue(score.toString().trim())
        }
    }
}