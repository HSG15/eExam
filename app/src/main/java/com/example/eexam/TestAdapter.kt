package com.example.eexam

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class TestAdapter(var context: Context, var datas: ArrayList<TestModel>) :
    RecyclerView.Adapter<TestAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.testquesitem, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val dataItem = datas[position]
        holder.questionTextView.text = dataItem.question
        holder.optionA.text = dataItem.optionA
        holder.optionB.text = dataItem.optionB
        holder.optionC.text = dataItem.optionC
        holder.optionD.text = dataItem.optionD
        holder.questionNo.text = "Question no-${position + 1} / ${datas.size}"

        holder.optionA.setOnClickListener {
            storeAnswer("a", position, dataItem)
        }

        holder.optionB.setOnClickListener {
            storeAnswer("b", position, dataItem)
        }

        holder.optionC.setOnClickListener {
            storeAnswer("c", position, dataItem)
        }

        holder.optionD.setOnClickListener {
            storeAnswer("d", position, dataItem)
            Log.d("newDataNew", "onBindViewHolder: d")
        }


    }


    override fun getItemCount(): Int {
        return datas.size
    }


    override fun getItemViewType(position: Int): Int {
        return position
    }


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var questionTextView: TextView = itemView.findViewById(R.id.questions)
        var optionA: RadioButton = itemView.findViewById(R.id.option_a)
        var optionB: RadioButton = itemView.findViewById(R.id.option_b)
        var optionC: RadioButton = itemView.findViewById(R.id.option_c)
        var optionD: RadioButton = itemView.findViewById(R.id.option_d)
        var questionNo: TextView = itemView.findViewById(R.id.noOfQuestion)
    }

    private fun storeAnswer(
        answer: String,
        position: Int,
        dataItem: TestModel
    ) {
        UtilMethod.answerList[position].userOption = answer
        Log.d("newDataNew", "onCreate: ${UtilMethod.answerList[position]}   $answer")

    }


}