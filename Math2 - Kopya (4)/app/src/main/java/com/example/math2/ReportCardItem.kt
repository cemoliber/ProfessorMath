package com.example.math2

import android.graphics.Color

data class ReportCardItem(
    val note: Int,
    val trueCount: Int,
    val wrongCount: Int,
    val level: Int,
    val operation: String,
    val nowTime: String,
)

fun getColorForNote(note: Int): Int {
    return when {
        note in 85..100 -> Color.GREEN // 85-100 arası yeşil
        note in 75..84 -> Color.BLUE // 75-84 arası mavi
        note in 65..74 -> Color.MAGENTA // 65-74 arası macenta
        note in 50..64 -> Color.YELLOW // 50-64 arası sarı
        else -> Color.RED // 0-49 arası kırmızı
    }
}