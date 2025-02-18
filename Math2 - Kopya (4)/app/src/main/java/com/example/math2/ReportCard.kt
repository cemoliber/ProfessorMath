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
        val dataList = databaseHelper.getAllData()
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, dataList.map { it.joinToString(" | ") })
        listView.adapter = adapter
    }
}