package com.example.eexam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.eexam.databinding.ActivityDetailExamBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class DetailExam : AppCompatActivity() {

    private var name = ""
    private var subject = ""
    lateinit var firebaseInstance: FirebaseDatabase
    var questionModel = arrayListOf<TestModel>()

    lateinit var testAdapter: TestAdapter
    lateinit var binding: ActivityDetailExamBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail_exam)

        subject = intent.getStringExtra("sub").toString()
        name = intent.getStringExtra("name").toString()
        firebaseInstance = FirebaseDatabase.getInstance()
        val uid = FirebaseAuth.getInstance().currentUser?.uid
        val linearLayoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = linearLayoutManager

        binding.Cancel.setOnClickListener { finish() }
        firebaseInstance.reference.child("QuestionBank").child(subject)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    binding.dataLayout.visibility=View.VISIBLE
                    binding.ProgressLayout.visibility=View.GONE


                    for (i in snapshot.children) {
                        val question = i.child("question").value.toString()
                        val optionA = i.child("a").value.toString()
                        val optionB = i.child("b").value.toString()
                        val optionC = i.child("c").value.toString()
                        val optionD = i.child("d").value.toString()
                        val answer = i.child("answer").value.toString()

                        questionModel.add(
                            TestModel(
                                question,
                                optionA,
                                optionB,
                                optionC,
                                optionD,
                                answer
                            )
                        )
                        UtilMethod.answerList.add(AnswerModel(answer,"e"))
                        testAdapter = TestAdapter(this@DetailExam, questionModel)
                        binding.recyclerView.adapter=testAdapter
                        testAdapter.notifyDataSetChanged()
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.d("error", "onCancelled: error")
                }
            })

        binding.Submit.setOnClickListener {
            var k=0
            for (i in UtilMethod.answerList){
                if (i.userOption==i.option){
                    k += 1
                }
            }
            val intent = Intent(this, ScoreBoard::class.java)
            intent.putExtra("mark", k*20)
            intent.putExtra("sub", subject)
            startActivity(intent)
            finish()
        }


    }
    
    override fun onDestroy() {
        super.onDestroy()
        UtilMethod.answerList.clear()
    }
}