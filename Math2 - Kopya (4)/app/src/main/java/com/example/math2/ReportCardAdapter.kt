package com.example.math2

import android.content.Context
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.content.ContextCompat

class ReportCardAdapter(private val context: Context, private val itemList: List<ReportCardItem>) : BaseAdapter() {

    override fun getCount(): Int = itemList.size

    override fun getItem(position: Int): Any = itemList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)

        val item = itemList[position]

        val noteCard = view.findViewById<TextView>(R.id.noteCard)
        val trueCountCard = view.findViewById<TextView>(R.id.trueCountCard)
        val wrongCountCard = view.findViewById<TextView>(R.id.wrongCountCard)
        val levelCard = view.findViewById<TextView>(R.id.levelCard)
        val operationCard = view.findViewById<TextView>(R.id.operationCard)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)

        noteCard.text = "Not: ${item.note}"
        trueCountCard.text = "Doğru Sayısı: ${item.trueCount}"
        wrongCountCard.text = "Yanlış Sayısı: ${item.wrongCount}"
        levelCard.text = "Seviye: ${item.level}"
        operationCard.text = "İşlem: ${item.operation}"

        progressBar.progress = item.note
        return view
    }

}
