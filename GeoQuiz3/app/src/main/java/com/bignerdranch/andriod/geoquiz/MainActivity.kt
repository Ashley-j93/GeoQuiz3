package com.bignerdranch.andriod.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

private const val TAG = "MainActivity" //added


class MainActivity : AppCompatActivity() {
    private lateinit var trueButton : Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var questionTextView: TextView //alt enter for adding resource/import info

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_mideast, false),
        Question(R.string.question_africa, false),
        Question(R.string.question_americas, true),
        Question(R.string.question_asia, true))

    private var currentIndex = 0
    private var correct_c = 0
    private var incorrect_c = 0
    private var click = false //this ensures that the user does not try to answer multiple times

    override fun onCreate(savedInstanceState: Bundle?) {



        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_button) //new
        questionTextView = findViewById(R.id.question_text_view) //new

        trueButton.setOnClickListener{ view: View ->
            if( click == false) {
                click = true
                checkAnswer(true)
            }
        }

        falseButton.setOnClickListener{ view: View ->
            if( click == false) {
                click = true
                checkAnswer(true)
            }
        }

        nextButton.setOnClickListener {
            currentIndex = (currentIndex +1) % questionBank.size
            updateQuestion()
        }
        updateQuestion()
    }//override


    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy() called")
    }

    private fun updateQuestion(){
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)
        click = false // a new question has appeared, this allows user to answer a new question
    }

    private fun checkAnswer(userAnswer: Boolean){
        val correctAnswer = questionBank[currentIndex].answer
        val messageResId = if (userAnswer == correctAnswer){
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        if (userAnswer == correctAnswer){
            correct_c +=  1
            val correct_count_display: TextView = findViewById(R.id.correct_count)
            val disp = "correct count # " + correct_c.toString() // cannot add strings together within setText
            correct_count_display.text = disp
        } else {
            incorrect_c += 1
            val incorrect_count_display: TextView = findViewById(R.id.incorrect_count)
            val disp = "incorrect count # " + incorrect_c.toString() // cannot add strings together within setText
            incorrect_count_display.text = disp
        }

        Toast.makeText(this,messageResId, Toast.LENGTH_SHORT)
            .show()
    }// fun checkAnswer
}// class main activity