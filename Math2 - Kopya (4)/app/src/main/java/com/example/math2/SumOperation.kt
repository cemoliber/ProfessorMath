package com.example.math2

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.RenderNode
import android.graphics.drawable.ColorDrawable
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Im
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.google.android.gms.ads.*
import java.util.*
import kotlin.random.Random

var score = 0

var trueCountL1 = 0
var wrongCountL1 = 0
var trueCountL2 = 0
var wrongCountL2 = 0
var trueCountL3 = 0
var wrongCountL3 = 0
var questions = 0

class SumOperation : AppCompatActivity() {

    private companion object{
        private const val TAG = "BANNER_AD_TAG"
    }
    private var adView: AdView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sum_operation)

        //dialog box
        val dialog = Dialog(this@SumOperation)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_dialog_box)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        //dialog box buttons
        val btnMenu = dialog.findViewById<Button>(R.id.btnMenu)
        val btnAgain = dialog.findViewById<Button>(R.id.btnAgain)

        //dialog box textViews
        val tvMessage1 = dialog.findViewById<TextView>(R.id.tvMessage)
        val tvMessage2 = dialog.findViewById<TextView>(R.id.tvMessage2)
        val tvMessage3 = dialog.findViewById<TextView>(R.id.tvMessage3)

        //String values for dialog box
        var scoreText = ""
        var wrongCountL1Text = ""
        var pointL1Text = ""

        var wrongCountL2Text = ""
        var pointL2Text = ""

        var wrongCountL3Text = ""
        var pointL3Text = ""

        //initialize
        MobileAds.initialize(this@SumOperation) {
            Log.d(TAG,"onInitializationCompleted: ")
        }

        MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder().setTestDeviceIds(
            listOf("TEST_DEVICE1","TEST_DEVICE2")
        ).build())

        //init banner ad
        adView = findViewById(R.id.bannerAd2)
        //Ad Request
        val adRequest = AdRequest.Builder().build()
        //load ad
        adView?.loadAd(adRequest)

        //setup ad callbacklisteners
        adView?.adListener = object : AdListener(){

            override fun onAdClosed() {
                super.onAdClosed()
                Log.d(TAG,"onAdClosed: ")
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                super.onAdFailedToLoad(adError)
                Log.e(TAG,"onAdFailedToLoad: ${adError.message}")
            }

            override fun onAdOpened() {
                super.onAdOpened()
                Log.d(TAG,"onAdOpened: ")
            }

            override fun onAdLoaded() {
                super.onAdLoaded()
                Log.d(TAG,"onAdLoaded: ")
            }

            override fun onAdClicked() {
                super.onAdClicked()
                Log.d(TAG,"onAdClicked: ")
            }

            override fun onAdImpression() {
                super.onAdImpression()
                Log.d(TAG,"onAdImpression: ")
            }

        }

        //to get spinner value
        val selectedValue = intent.getStringExtra("setSpinnerData")

        //sound effects
        var correct = MediaPlayer.create(this@SumOperation,R.raw.correct2)
        var wrongSound = MediaPlayer.create(this@SumOperation,R.raw.incorrect)

        //score
        val sendScore = findViewById<TextView>(R.id.scoreText)
        sendScore.text = "Skor: "+ score


        if(selectedValue.equals("Seviye 1")){
            //creates random numbers
            val randomNum1 = Random.nextInt(0,10)
            val randomNum2 = Random.nextInt(0,10)
            val defaultAnswer = randomNum1 + randomNum2


            //to shows questions
            val questionText = findViewById<TextView>(R.id.question)
            questionText.text = ""
            questionText.text = randomNum1.toString() + " + " + randomNum2.toString()

            //to checking answer when button clicked
            val controlAnswer = findViewById<Button>(R.id.controlButton)
            controlAnswer.setOnClickListener{
                val getAnswer = findViewById<EditText>(R.id.answer)
                //val showResult = findViewById<TextView>(R.id.showResult)

                val text = getAnswer.text.toString()

                if(text == ""){
                    //showResult.text = "CEVAP GİRİLMEDİ"
                    noAnswerImg()
                    newQuestions()
                }else{
                    val gettedAnswer = text.toInt()
                    if(gettedAnswer == defaultAnswer){
                        changeColorCorrect()
                        correct.start()
                        //congratulatoryMessage()
                        clearGetAnswerBox()
                        score++
                        questions++
                        trueCountL1++
                        sendScore.text = "Skor: "+ score
                        clearGetAnswerBox()
                        newQuestions()
                    }else if(gettedAnswer != defaultAnswer){
                        changeColorIncorrect()
                        wrongSound.start()
                        questions++
                        wrongCountL1++
                        sendScore.text = "Skor: "+ score
                        if(wrongCountL1 == 5){
                            dialog.show()
                            scoreText = score.toString()
                            wrongCountL1Text = wrongCountL1.toString()
                            tvMessage1.setText("Doğru Sayısı: "+scoreText)
                            tvMessage2.setText("Yanlış Sayısı: "+wrongCountL1Text)
                            val pointL1 = (trueCountL1 * 100) / questions
                            pointL1Text = pointL1.toString()
                            tvMessage3.setText("Puan: "+pointL1Text)

                            btnMenu.setOnClickListener {
                                score = 0
                                questions = 0
                                trueCountL1 = 0
                                wrongCountL1 = 0
                                val intent = Intent(this@SumOperation, Operations::class.java)
                                startActivity(intent)
                            }

                            btnAgain.setOnClickListener {
                                score = 0
                                sendScore.text = "Skor: "+ score
                                questions = 0
                                trueCountL1 = 0
                                wrongCountL1 = 0
                                dialog.dismiss()
                            }
                        }else{
                            newQuestions()
                        }
                    }
                }
            }

            val refreshButton = findViewById<Button>(R.id.refreshButton)
            refreshButton.setOnClickListener{
                //val showResult = findViewById<TextView>(R.id.showResult)
                //showResult.text = ""
                clearGetAnswerBox()
                newQuestions()
            }
        }else if(selectedValue.equals("Seviye 2")){
            val randomNum1 = Random.nextInt(0,30)
            val randomNum2 = Random.nextInt(0,30)
            val defaultAnswer = randomNum1 + randomNum2

            //score
            val sendScore = findViewById<TextView>(R.id.scoreText)

            //to shows questions
            val questionText = findViewById<TextView>(R.id.question)
            questionText.text = ""
            questionText.text = randomNum1.toString() + " + " + randomNum2.toString()

            //to checking answer when button clicked
            val controlAnswer = findViewById<Button>(R.id.controlButton)
            controlAnswer.setOnClickListener {
                val getAnswer = findViewById<EditText>(R.id.answer)
                //val showResult = findViewById<TextView>(R.id.showResult)

                val text = getAnswer.text.toString()

                if (text == "") {
                    //showResult.text = "CEVAP GİRİLMEDİ"
                    noAnswerImg()
                    newQuestions()
                } else {
                    val gettedAnswer = text.toInt()
                    if(gettedAnswer == defaultAnswer){
                        changeColorCorrect()
                        correct.start()
                        //congratulatoryMessage()
                        clearGetAnswerBox()
                        score += 2
                        questions++
                        trueCountL2++
                        sendScore.text = "Skor: "+ score
                        newQuestions()
                    }else if(gettedAnswer != defaultAnswer){
                        changeColorIncorrect()
                        wrongSound.start()
                        questions++
                        wrongCountL1++
                        sendScore.text = "Skor: "+ score
                        if(wrongCountL2 == 5){
                            dialog.show()
                            scoreText = score.toString()
                            wrongCountL2Text = wrongCountL2.toString()
                            tvMessage1.setText("Doğru Sayısı: "+scoreText)
                            tvMessage2.setText("Yanlış Sayısı: "+wrongCountL2Text)
                            val pointL2 = (trueCountL2 * 100) / questions
                            pointL2Text = pointL2.toString()
                            tvMessage3.setText("Puan: "+pointL2Text)

                            btnMenu.setOnClickListener {
                                score = 0
                                questions = 0
                                trueCountL2 = 0
                                wrongCountL2 = 0
                                val intent = Intent(this@SumOperation, Operations::class.java)
                                startActivity(intent)
                            }

                            btnAgain.setOnClickListener {
                                score = 0
                                sendScore.text = "Skor: "+ score
                                questions = 0
                                trueCountL2 = 0
                                wrongCountL2 = 0
                                dialog.dismiss()
                            }
                        }else{
                            newQuestions()
                        }
                    }
                }
            }

            val refreshButton = findViewById<Button>(R.id.refreshButton)
            refreshButton.setOnClickListener{
                //val showResult = findViewById<TextView>(R.id.showResult)
                //showResult.text = ""
                clearGetAnswerBox()
                newQuestions()
            }
        }else if(selectedValue.equals("Seviye 3")){
            val randomNum1 = Random.nextInt(0,50)
            val randomNum2 = Random.nextInt(0,50)
            val defaultAnswer = randomNum1 + randomNum2

            //score
            val sendScore = findViewById<TextView>(R.id.scoreText)

            //to shows questions
            val questionText = findViewById<TextView>(R.id.question)
            questionText.text = ""
            questionText.text = randomNum1.toString() + " + " + randomNum2.toString()

            //to checking answer when button clicked
            val controlAnswer = findViewById<Button>(R.id.controlButton)
            controlAnswer.setOnClickListener{
                val getAnswer = findViewById<EditText>(R.id.answer)
                //val showResult = findViewById<TextView>(R.id.showResult)

                val text = getAnswer.text.toString()

                if(text == ""){
                    //showResult.text = "CEVAP GİRİLMEDİ"
                    noAnswerImg()
                    newQuestions()
                }else{
                    val gettedAnswer = text.toInt()
                    if(gettedAnswer == defaultAnswer){
                        changeColorCorrect()
                        correct.start()
                        //congratulatoryMessage()
                        clearGetAnswerBox()
                        score += 3
                        questions++
                        trueCountL3++
                        sendScore.text = "Skor: "+ score
                        newQuestions()
                    }else if(gettedAnswer != defaultAnswer){
                        changeColorIncorrect()
                        wrongSound.start()
                        questions++
                        wrongCountL3++
                        sendScore.text = "Skor: "+ score
                        if(wrongCountL3 == 5){
                            dialog.show()
                            scoreText = score.toString()
                            wrongCountL3Text = wrongCountL3.toString()
                            tvMessage1.setText("Doğru Sayısı: "+scoreText)
                            tvMessage2.setText("Yanlış Sayısı: "+wrongCountL3Text)
                            val pointL3 = (trueCountL3 * 100) / questions
                            pointL3Text = pointL3.toString()
                            tvMessage3.setText("Puan: "+pointL3Text)

                            btnMenu.setOnClickListener {
                                score = 0
                                questions = 0
                                trueCountL3 = 0
                                wrongCountL3 = 0
                                val intent = Intent(this@SumOperation, Operations::class.java)
                                startActivity(intent)
                            }
                            btnAgain.setOnClickListener {
                                score = 0
                                sendScore.text = "Skor: "+ score
                                questions = 0
                                trueCountL3 = 0
                                wrongCountL3 = 0
                                dialog.dismiss()
                            }
                        }else{
                            newQuestions()
                        }
                    }
                }
            }

            val refreshButton = findViewById<Button>(R.id.refreshButton)
            refreshButton.setOnClickListener{
                clearGetAnswerBox()
                newQuestions()
            }
        }
    }

    private fun newQuestions() {

        //dialog box
        val dialog = Dialog(this@SumOperation)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.custom_dialog_box)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))


        //dialog box buttons
        val btnMenu = dialog.findViewById<Button>(R.id.btnMenu)
        val btnAgain = dialog.findViewById<Button>(R.id.btnAgain)

        //dialog box textViews
        val tvMessage1 = dialog.findViewById<TextView>(R.id.tvMessage)
        val tvMessage2 = dialog.findViewById<TextView>(R.id.tvMessage2)
        val tvMessage3 = dialog.findViewById<TextView>(R.id.tvMessage3)

        //String values for dialog box
        var scoreText = ""
        var wrongCountL1Text = ""
        var pointL1Text = ""

        var wrongCountL2Text = ""
        var pointL2Text = ""

        var wrongCountL3Text = ""
        var pointL3Text = ""

        val selectedValue = intent.getStringExtra("setSpinnerData")
        val correct = MediaPlayer.create(this@SumOperation,R.raw.correct2)
        val wrongSound = MediaPlayer.create(this@SumOperation,R.raw.incorrect)

        if(selectedValue.equals("Seviye 1")){
            val randomNum1 = Random.nextInt(0,10)
            val randomNum2 = Random.nextInt(0,10)
            val defaultAnswer = randomNum1 + randomNum2

            val questionText = findViewById<TextView>(R.id.question)
            questionText.text = ""
            questionText.text = randomNum1.toString() + " + " + randomNum2.toString()

            val controlAnswer = findViewById<Button>(R.id.controlButton)
            controlAnswer.setOnClickListener{
                val getAnswer = findViewById<EditText>(R.id.answer)
                //val showResult = findViewById<TextView>(R.id.showResult)
                val sendScore = findViewById<TextView>(R.id.scoreText)

                val text = getAnswer.text.toString()
                clearGetAnswerBox()

                if(text ==""){
                    //showResult.text = "CEVAP GİRİLMEDİ"
                    noAnswerImg()
                    newQuestions()
                }else{
                    val gettedAnswer = text.toInt()
                    if(gettedAnswer == defaultAnswer){
                        changeColorCorrect()
                        correct.start()
                        //congratulatoryMessage()
                        score++
                        questions++
                        trueCountL1++
                        sendScore.text = "Skor: "+ score
                        newQuestions()
                    }else if(gettedAnswer != defaultAnswer){
                        changeColorIncorrect()
                        wrongSound.start()
                        questions++
                        wrongCountL1++
                        sendScore.text = "Skor: "+ score
                        if(wrongCountL1 == 5){
                            dialog.show()
                            scoreText = score.toString()
                            wrongCountL1Text = wrongCountL1.toString()
                            tvMessage1.setText("Doğru Sayısı: "+scoreText)
                            tvMessage2.setText("Yanlış Sayısı: "+wrongCountL1Text)
                            val pointL1 = (trueCountL1 * 100) / questions
                            pointL1Text = pointL1.toString()
                            tvMessage3.setText("Puan: "+pointL1Text)

                            btnMenu.setOnClickListener {
                                score = 0
                                questions = 0
                                trueCountL1 = 0
                                wrongCountL1 = 0
                                val intent = Intent(this@SumOperation, Operations::class.java)
                                startActivity(intent)
                            }

                            btnAgain.setOnClickListener {
                                score = 0
                                sendScore.text = "Skor: "+ score
                                questions = 0
                                trueCountL1 = 0
                                wrongCountL1 = 0
                                dialog.dismiss()
                            }
                        }else{
                            newQuestions()
                        }
                    }
                }
            }
        }else if(selectedValue.equals("Seviye 2")){
            val randomNum1 = Random.nextInt(0,30)
            val randomNum2 = Random.nextInt(0,30)
            val defaultAnswer = randomNum1 + randomNum2

            val questionText = findViewById<TextView>(R.id.question)
            questionText.text = ""
            questionText.text = randomNum1.toString() + " + " + randomNum2.toString()

            val controlAnswer = findViewById<Button>(R.id.controlButton)
            controlAnswer.setOnClickListener{
                val getAnswer = findViewById<EditText>(R.id.answer)
                //val showResult = findViewById<TextView>(R.id.showResult)
                val sendScore = findViewById<TextView>(R.id.scoreText)

                val text = getAnswer.text.toString()
                clearGetAnswerBox()

                if(text == ""){
                    noAnswerImg()
                    newQuestions()
                }else{
                    val gettedAnswer = text.toInt()
                    if(gettedAnswer == defaultAnswer){
                        changeColorCorrect()
                        correct.start()
                        //congratulatoryMessage()
                        clearGetAnswerBox()
                        score += 2
                        questions++
                        trueCountL2++
                        sendScore.text = "Skor: "+ score
                        newQuestions()
                    }else if(gettedAnswer != defaultAnswer){
                        changeColorIncorrect()
                        wrongSound.start()
                        questions++
                        wrongCountL2++
                        sendScore.text = "Skor: "+ score
                        if(wrongCountL2 == 5){
                            dialog.show()
                            scoreText = score.toString()
                            wrongCountL2Text = wrongCountL2.toString()
                            tvMessage1.setText("Doğru Sayısı: "+scoreText)
                            tvMessage2.setText("Yanlış Sayısı: "+ wrongCountL2Text)
                            val pointL2 = (trueCountL2 * 100) / questions
                            pointL2Text = pointL2.toString()
                            tvMessage3.setText("Puan: "+pointL2Text)

                            btnMenu.setOnClickListener {
                                score = 0
                                questions = 0
                                trueCountL2 = 0
                                wrongCountL2 = 0
                                val intent = Intent(this@SumOperation, Operations::class.java)
                                startActivity(intent)
                            }

                            btnAgain.setOnClickListener {
                                score = 0
                                sendScore.text = "Skor: "+ score
                                questions = 0
                                trueCountL2 = 0
                                wrongCountL2 = 0
                                dialog.dismiss()
                            }
                        }else{
                            newQuestions()
                        }
                    }
                }
            }
        }else if(selectedValue.equals("Seviye 3")){
            val randomNum1 = Random.nextInt(0,50)
            val randomNum2 = Random.nextInt(0,50)
            val defaultAnswer = randomNum1 + randomNum2

            val questionText = findViewById<TextView>(R.id.question)
            questionText.text = ""
            questionText.text = randomNum1.toString() + " + " + randomNum2.toString()

            val controlAnswer = findViewById<Button>(R.id.controlButton)
            controlAnswer.setOnClickListener {
                val getAnswer = findViewById<EditText>(R.id.answer)
                //val showResult = findViewById<TextView>(R.id.showResult)
                val sendScore = findViewById<TextView>(R.id.scoreText)

                val text = getAnswer.text.toString()
                clearGetAnswerBox()

                if(text == ""){
                    //showResult.text = "CEVAP GİRİLMEDİ"
                    noAnswerImg()
                    newQuestions()
                }else{
                    val gettedAnswer = text.toInt()
                    if(gettedAnswer == defaultAnswer){
                        changeColorCorrect()
                        correct.start()
                        //congratulatoryMessage()
                        clearGetAnswerBox()
                        score += 3
                        questions++
                        trueCountL3++
                        sendScore.text = "Skor: "+ score
                        newQuestions()
                    }else if(gettedAnswer != defaultAnswer){
                        changeColorIncorrect()
                        wrongSound.start()
                        questions++
                        wrongCountL3++
                        sendScore.text = "Skor: "+ score
                        if(wrongCountL3 == 5){
                            dialog.show()
                            scoreText = score.toString()
                            wrongCountL3Text = wrongCountL3.toString()
                            tvMessage1.setText("Doğru Sayısı: "+scoreText)
                            tvMessage2.setText("Yanlış Sayısı: "+ wrongCountL3Text)
                            val pointL3 = (trueCountL3 * 100) / questions
                            pointL3Text = pointL3.toString()
                            tvMessage3.setText("Puan: "+pointL3Text)

                            btnMenu.setOnClickListener {
                                score = 0
                                questions = 0
                                trueCountL3 = 0
                                wrongCountL3 = 0
                                val intent = Intent(this@SumOperation, Operations::class.java)
                                startActivity(intent)
                            }

                            btnAgain.setOnClickListener {
                                score = 0
                                sendScore.text = "Skor: "+ score
                                questions = 0
                                trueCountL3 = 0
                                wrongCountL3 = 0
                                dialog.dismiss()
                            }
                        }else{
                            newQuestions()
                        }
                    }
                }
            }
        }
    }
/*
    private fun congratulatoryMessage() {
        val showResult = findViewById<TextView>(R.id.showResult)
        showResult.setTextColor(Color.GREEN)
        showResult.text = "Doğru Bildin Evlat"
    }

    private fun tryAgainMessage() {
        val showResult = findViewById<TextView>(R.id.showResult)
        showResult.setTextColor(Color.MAGENTA)
        showResult.text = "Tekrar Dene Evlat"
    }*/

    private fun clearGetAnswerBox() {
        val getAnswer = findViewById<EditText>(R.id.answer)
        getAnswer.setText("")
    }

    private fun changeColorIncorrect() {

        //image
        val sadeImg = findViewById<ImageView>(R.id.sadImg)

        //timer
        val timer = Timer()
        val cardView = findViewById<CardView>(R.id.scoreCardView)

        cardView.setCardBackgroundColor(resources.getColor(android.R.color.holo_red_dark))
        sadeImg.visibility = View.VISIBLE

        timer.schedule(object : TimerTask(){
            override fun run() {
                cardView.setCardBackgroundColor(resources.getColor(android.R.color.system_accent2_900))
                sadeImg.visibility = View.INVISIBLE
            }
        },1200)
    }


    private fun noAnswerImg(){

        //image
        val notansweredImg = findViewById<ImageView>(R.id.noAnswerImg)
        notansweredImg.visibility = View.VISIBLE

        //timer
        val timer = Timer()
        timer.schedule(object : TimerTask(){
            override fun run() {
                notansweredImg.visibility = View.INVISIBLE
            }
        },1200)
    }

    private fun changeColorCorrect() {

        //image
        val coolImg = findViewById<ImageView>(R.id.coolImg)

        //timer
        val timer = Timer()

        val cardView = findViewById<CardView>(R.id.scoreCardView)
        cardView.setCardBackgroundColor(resources.getColor(android.R.color.holo_green_dark))
        coolImg.visibility = View.VISIBLE
        timer.schedule(object : TimerTask(){
            override fun run() {
                cardView.setCardBackgroundColor(resources.getColor(android.R.color.system_accent2_900))
                coolImg.visibility = View.INVISIBLE
            }
        },1200)
    }

    override fun onPause() {
        adView?.pause()
        super.onPause()
        Log.d(TAG,"onPause: ")
    }

    override fun onResume() {
        adView?.resume()
        super.onResume()
        Log.d(TAG,"onResume: ")
    }

    override fun onDestroy() {
        adView?.destroy()
        super.onDestroy()
        Log.d(TAG,"onDestroy: ")
    }
}

