package com.example.math2

import android.os.Bundle
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Analysis : AppCompatActivity() {

    private lateinit var progressBar1: ProgressBar
    private lateinit var progressBar2: ProgressBar
    private lateinit var progressBar3: ProgressBar
    private lateinit var progressBar4: ProgressBar
    private lateinit var progressBar5: ProgressBar

    private lateinit var progressText1: TextView
    private lateinit var textView1_1: TextView
    private lateinit var textView1_2: TextView
    private lateinit var textView1_3: TextView

    private lateinit var progressText2: TextView
    private lateinit var textView2_1: TextView
    private lateinit var textView2_2: TextView
    private lateinit var textView2_3: TextView

    private lateinit var progressText3: TextView
    private lateinit var textView3_1: TextView
    private lateinit var textView3_2: TextView
    private lateinit var textView3_3: TextView

    private lateinit var progressText4: TextView
    private lateinit var textView4_1: TextView
    private lateinit var textView4_2: TextView
    private lateinit var textView4_3: TextView

    private lateinit var progressText5: TextView
    private lateinit var textView5_1: TextView
    private lateinit var textView5_2: TextView
    private lateinit var textView5_3: TextView

    var totalQuestion = 0
    var totalTrueCount = 0
    var totalWrongCount = 0


    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_analysis)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        progressBar1 = findViewById(R.id.progressBar1)
        progressBar2 = findViewById(R.id.progressBar2)
        progressBar3 = findViewById(R.id.progressBar3)
        progressBar4 = findViewById(R.id.progressBar4)
        progressBar5 = findViewById(R.id.progressBar5)

        progressText1 = findViewById(R.id.progressText1)
        textView1_1 = findViewById(R.id.textView1_1)
        textView1_2 = findViewById(R.id.textView1_2)
        textView1_3 = findViewById(R.id.textView1_3)

        progressText2 = findViewById(R.id.progressText2)
        textView2_1 = findViewById(R.id.textView2_1)
        textView2_2 = findViewById(R.id.textView2_2)
        textView2_3 = findViewById(R.id.textView2_3)

        progressText3 = findViewById(R.id.progressText3)
        textView3_1 = findViewById(R.id.textView3_1)
        textView3_2 = findViewById(R.id.textView3_2)
        textView3_3 = findViewById(R.id.textView3_3)

        progressText4 = findViewById(R.id.progressText4)
        textView4_1 = findViewById(R.id.textView4_1)
        textView4_2 = findViewById(R.id.textView4_2)
        textView4_3 = findViewById(R.id.textView4_3)

        progressText5 = findViewById(R.id.progressText5)
        textView5_1 = findViewById(R.id.textView5_1)
        textView5_2 = findViewById(R.id.textView5_2)
        textView5_3 = findViewById(R.id.textView5_3)

        // DatabaseHelper'ı başlat
        databaseHelper = DatabaseHelper(this)

        // Verileri al ve UI'ı güncelle
        updateUI()



        if (totalQuestion > 0 && totalWrongCount > 0){

            val calculatedProgres = (totalTrueCount * 100) / totalQuestion

            progressBar5.progress = calculatedProgres
            progressText5.text = calculatedProgres.toString()
            textView5_1.text = "Toplam Çözülen Soru: $totalQuestion"
            textView5_2.text = "Toplam Doğru Sayısı: $totalTrueCount"
            textView5_3.text = "Toplam Yanlış Sayısı: $totalWrongCount"

        }else if(totalQuestion > 0 && totalWrongCount == 0){

            progressBar5.progress = 100
            progressText5.text = "100%"
            textView5_1.text = "Toplam Çözülen Soru: $totalQuestion"
            textView5_2.text = "Toplam Doğru Sayısı: $totalTrueCount"
            textView5_3.text = "Toplam Yanlış Sayısı: $totalWrongCount"
        }
    }

    private fun updateUI() {
        try {
            val analyzedDataSum = databaseHelper.getSumAnalyzedData()
            if (!analyzedDataSum.isNullOrEmpty()) {
                val data = analyzedDataSum[0]
                progressText1.text = "${data.note}%"
                textView1_1.text = "Çözülen Soru: ${data.totalQuestion}"
                textView1_2.text = "Doğru Sayısı: ${data.trueCount}"
                textView1_3.text = "Yanlış Sayısı: ${data.wrongCount}"
                progressBar1.progress = data.note

                totalQuestion += data.totalQuestion
                totalTrueCount += data.trueCount
                totalWrongCount += data.wrongCount
            } else {
                // Eğer veri yoksa, default değerlerle UI'ı güncelle
                progressText1.text = "0%"
                textView1_1.text = "Çözülen Soru: 0"
                textView1_2.text = "Doğru Sayısı: 0"
                textView1_3.text = "Yanlış Sayısı: 0"
                progressBar1.progress = 0
            }

            val analyzedDataSub = databaseHelper.getSubAnalyzedData()
            if (!analyzedDataSub.isNullOrEmpty()) {
                val data = analyzedDataSub[0]
                progressText2.text = "${data.note}%"
                textView2_1.text = "Çözülen Soru: ${data.totalQuestion}"
                textView2_2.text = "Doğru Sayısı: ${data.trueCount}"
                textView2_3.text = "Yanlış Sayısı: ${data.wrongCount}"
                progressBar2.progress = data.note

                totalQuestion += data.totalQuestion
                totalTrueCount += data.trueCount
                totalWrongCount += data.wrongCount
            } else {
                progressText2.text = "0%"
                textView2_1.text = "Çözülen Soru: 0"
                textView2_2.text = "Doğru Sayısı: 0"
                textView2_3.text = "Yanlış Sayısı: 0"
                progressBar2.progress = 0
            }

            val analyzedDataMultiply = databaseHelper.getMultiplyAnalyzedData()
            if (!analyzedDataMultiply.isNullOrEmpty()) {
                val data = analyzedDataMultiply[0]
                progressText3.text = "${data.note}%"
                textView3_1.text = "Çözülen Soru: ${data.totalQuestion}"
                textView3_2.text = "Doğru Sayısı: ${data.trueCount}"
                textView3_3.text = "Yanlış Sayısı: ${data.wrongCount}"
                progressBar3.progress = data.note

                totalQuestion += data.totalQuestion
                totalTrueCount += data.trueCount
                totalWrongCount += data.wrongCount
            } else {
                progressText3.text = "0%"
                textView3_1.text = "Çözülen Soru: 0"
                textView3_2.text = "Doğru Sayısı: 0"
                textView3_3.text = "Yanlış Sayısı: 0"
                progressBar3.progress = 0
            }

            val analyzedDataDivision = databaseHelper.getDivisionAnalyzedData()
            if (!analyzedDataDivision.isNullOrEmpty()) {
                val data = analyzedDataDivision[0]
                progressText4.text = "${data.note}%"
                textView4_1.text = "Çözülen Soru: ${data.totalQuestion}"
                textView4_2.text = "Doğru Sayısı: ${data.trueCount}"
                textView4_3.text = "Yanlış Sayısı: ${data.wrongCount}"
                progressBar4.progress = data.note

                totalQuestion += data.totalQuestion
                totalTrueCount += data.trueCount
                totalWrongCount += data.wrongCount
            } else {
                progressText4.text = "0%"
                textView4_1.text = "Çözülen Soru: 0"
                textView4_2.text = "Doğru Sayısı: 0"
                textView4_3.text = "Yanlış Sayısı: 0"
                progressBar4.progress = 0
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }



}
