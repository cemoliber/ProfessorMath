package com.example.math2

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ReportCard : AppCompatActivity() {
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_card)

        databaseHelper = DatabaseHelper(this)
        listView = findViewById(R.id.listView)

        val turnBack = findViewById<Button>(R.id.btnBack)

        turnBack.setOnClickListener{
            val intent = Intent(this,Operations::class.java)
            startActivity(intent)
        }

        loadData()
    }


    private fun loadData() {
        val reportCardList = databaseHelper.getAllData() ?: emptyList() // Eğer null ise boş liste döndür

        if (reportCardList.isEmpty()) {
            showLongToast("Henüz Soru Çözmedin", 6)
        } else {
            val adapter = ReportCardAdapter(this, reportCardList)
            listView.adapter = adapter
        }
    }

    fun showLongToast(message: String, durationInSeconds: Int) {
        val toast = Toast.makeText(this, message, Toast.LENGTH_SHORT)
        val handler = android.os.Handler(mainLooper)
        val repeatCount = durationInSeconds * 2

        for (i in 0 until repeatCount) {
            handler.postDelayed({ toast.show() }, (i * 2000).toLong())
        }
    }


}