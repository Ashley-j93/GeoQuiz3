package com.bignerdranch.andriod.geoquiz

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Surface
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders

private const val TAG = "MainActivity" //added


class MainActivity : AppCompatActivity() {
    private lateinit var trueButton : Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var questionTextView: TextView //alt enter for adding resource/import info

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }

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

        declareOrientation(this)//added
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
            quizViewModel.moveToNext()
            updateQuestion()
        }
        updateQuestion()
        updateCorrectDisplay()
        updateIncorrectDisplay()

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
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
        click = false // a new question has appeared, this allows user to answer a new question
    }

    private fun checkAnswer(userAnswer: Boolean){
        val correctAnswer = quizViewModel.currentQuestionAnswer
        val messageResId = if (userAnswer == correctAnswer){
            R.string.correct_toast
        } else {
            R.string.incorrect_toast
        }
        if (userAnswer == correctAnswer){
            quizViewModel.updateCorrect()
            updateCorrectDisplay()
        } else {
            quizViewModel.updateIncorrect()
            updateIncorrectDisplay()
        }

        Toast.makeText(this,messageResId, Toast.LENGTH_SHORT)
            .show()
    }// fun checkAnswer

    private fun updateCorrectDisplay(){
        val correct_count_display: TextView = findViewById(R.id.correct_count)
        correct_count_display.text = "correct count # " + quizViewModel.correct.toString() // cannot add strings together within setText
    }

    private fun updateIncorrectDisplay(){
        val incorrect_count_display: TextView = findViewById(R.id.incorrect_count)
        incorrect_count_display.text = "incorrect count # " + quizViewModel.incorrect.toString() // cannot add strings together within setText
    }

    private fun declareOrientation(context: Context) {
        val orientation = (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay.getOrientation()
        val messageResId = if ((orientation == Surface.ROTATION_0) or (orientation == Surface.ROTATION_180)) {
            R.string.portrait_orientation
        } else {
            R.string.landscape_orientation
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }

}// class main activity