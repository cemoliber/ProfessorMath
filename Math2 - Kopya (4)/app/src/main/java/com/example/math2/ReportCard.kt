package com.example.math2

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class ReportCard : AppCompatActivity() {
    private lateinit var databaseHelper: DatabaseHelper
    private lateinit var listView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_card)

        databaseHelper = DatabaseHelper(this)
        listView = findViewById(R.id.listView)

        loadData()
    }


    private fun loadData() {
        val reportCardList = databaseHelper.getAllData() // ReportCardItem listesi al
        val adapter = ReportCardAdapter(this, reportCardList) // Özel adapter kullan
        listView.adapter = adapter // Adapter'ı ListView'a ata
    }


}