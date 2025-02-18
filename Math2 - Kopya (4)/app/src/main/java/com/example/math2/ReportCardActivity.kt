package com.example.math2

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity

class ReportCardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_card)

        val listView: ListView = findViewById(R.id.listView)
        val dbHelper = DatabaseHelper(this)

        // **Verileri veritabanından al**
        val itemList = dbHelper.getAllData()

        // **Adapter oluştur ve ListView'e bağla**
        val adapter = ReportCardAdapter(this, itemList)
        listView.adapter = adapter
    }
}
