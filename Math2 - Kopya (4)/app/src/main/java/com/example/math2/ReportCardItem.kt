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

data class AnalysisDatas(
    val note: Int,
    val totalQuestion: Int,
    val trueCount: Int,
    val wrongCount: Int
)