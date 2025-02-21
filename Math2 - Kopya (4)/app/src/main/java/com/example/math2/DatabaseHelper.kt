package com.example.math2

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    companion object {
        private const val DATABASE_NAME = "ReportDB.db"
        private const val TABLE_NAME = "reports"
        private const val COL_ID = "id"
        private const val COL_NOTE = "data1"  // Not
        private const val COL_TRUE_COUNT = "data2"  // Doğru sayısı
        private const val COL_WRONG_COUNT = "data3"  // Yanlış sayısı
        private const val COL_LEVEL = "data4"  // Seviye
        private const val COL_OPERATION = "data5"  // İşlem
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
        CREATE TABLE $TABLE_NAME (
            $COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, 
            $COL_NOTE INTEGER, 
            $COL_TRUE_COUNT INTEGER, 
            $COL_WRONG_COUNT INTEGER, 
            $COL_LEVEL INTEGER, 
            $COL_OPERATION TEXT, 
            nowTime TEXT
        )
    """.trimIndent()
        db.execSQL(createTable)
    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // **Yeni veri ekleme fonksiyonu**
    fun insertReportCard(note: Int, trueCount: Int, wrongCount: Int, level: Int, operation: String): Boolean {
        val db = writableDatabase
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()) // Tarih formatı
        val nowTime = dateFormat.format(Date()) // Şu anki zamanı al

        val contentValues = ContentValues().apply {
            put(COL_NOTE, note)
            put(COL_TRUE_COUNT, trueCount)
            put(COL_WRONG_COUNT, wrongCount)
            put(COL_LEVEL, level)
            put(COL_OPERATION, operation)
            put("nowTime", nowTime) // Zamanı kaydediyoruz
        }
        val result = db.insert(TABLE_NAME, null, contentValues)
        db.close()
        return result != -1L
    }


    // **Tüm verileri çekme fonksiyonu**
    fun getAllData(): List<ReportCardItem> {
        val reportList = mutableListOf<ReportCardItem>()
        val db = this.readableDatabase
        // nowTime sütununa göre en yeni kayıtları önce getirmek için DESC ekliyoruz
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME ORDER BY nowTime DESC", null)

        if (cursor.moveToFirst()) {
            do {
                val note = cursor.getInt(cursor.getColumnIndexOrThrow(COL_NOTE))
                val trueCount = cursor.getInt(cursor.getColumnIndexOrThrow(COL_TRUE_COUNT))
                val wrongCount = cursor.getInt(cursor.getColumnIndexOrThrow(COL_WRONG_COUNT))
                val level = cursor.getInt(cursor.getColumnIndexOrThrow(COL_LEVEL))
                val operation = cursor.getString(cursor.getColumnIndexOrThrow(COL_OPERATION))
                val nowTime = cursor.getLong(cursor.getColumnIndexOrThrow("nowTime")) // Zamanı al

                reportList.add(ReportCardItem(note, trueCount, wrongCount, level, operation,
                    nowTime.toString()
                ))
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return reportList
    }

    fun getSumAnalyzedData(): List<AnalysisDatas> {
        val analyzedData = mutableListOf<AnalysisDatas>()
        val db = this.readableDatabase

        // "Toplama" işlemi olan verileri toplayan sorgu
        val sumNoteCursor = db.rawQuery(
            "SELECT SUM($COL_NOTE) AS totalNote FROM $TABLE_NAME WHERE $COL_OPERATION = ?",
            arrayOf("Toplama")
        )
        val trueCountCursor = db.rawQuery(
            "SELECT SUM($COL_TRUE_COUNT) AS totalTrueCount FROM $TABLE_NAME WHERE $COL_OPERATION = ?",
            arrayOf("Toplama")
        )
        val wrongCountCursor = db.rawQuery(
            "SELECT SUM($COL_WRONG_COUNT) AS totalWrongCount FROM $TABLE_NAME WHERE $COL_OPERATION = ?",
            arrayOf("Toplama")
        )

        if (sumNoteCursor.moveToFirst()) {
            val totalNoteIndex = sumNoteCursor.getColumnIndex("totalNote")
            val trueCountIndex = trueCountCursor.getColumnIndex("totalTrueCount")
            val wrongCountIndex = wrongCountCursor.getColumnIndex("totalWrongCount")

            if (totalNoteIndex != -1) {

                var trueCount = 0
                var wrongCount = 0

                if (trueCountCursor.moveToFirst()) {
                    trueCount = trueCountCursor.getInt(trueCountIndex)
                }
                if (wrongCountCursor.moveToFirst()) {
                    wrongCount = wrongCountCursor.getInt(wrongCountIndex)
                }

                val questionCount = trueCount + wrongCount

                val note = (trueCount * 100) / questionCount
                analyzedData.add(AnalysisDatas(note = note, totalQuestion = questionCount, trueCount = trueCount, wrongCount = wrongCount))
            } else {
                Log.e("DatabaseError", "Sütun bulunamadı")
            }
        } else {
            Log.e("DatabaseError", "Sorgu sonuç döndürmedi")
        }

        sumNoteCursor.close()
        trueCountCursor.close()
        wrongCountCursor.close()
        return analyzedData
    }

    fun getSubAnalyzedData(): List<AnalysisDatas> {
        val analyzedData = mutableListOf<AnalysisDatas>()
        val db = this.readableDatabase

        // "Toplama" işlemi olan verileri toplayan sorgu
        val sumNoteCursor = db.rawQuery(
            "SELECT SUM($COL_NOTE) AS totalNote FROM $TABLE_NAME WHERE $COL_OPERATION = ?",
            arrayOf("Çıkarma")
        )
        val trueCountCursor = db.rawQuery(
            "SELECT SUM($COL_TRUE_COUNT) AS totalTrueCount FROM $TABLE_NAME WHERE $COL_OPERATION = ?",
            arrayOf("Çıkarma")
        )
        val wrongCountCursor = db.rawQuery(
            "SELECT SUM($COL_WRONG_COUNT) AS totalWrongCount FROM $TABLE_NAME WHERE $COL_OPERATION = ?",
            arrayOf("Çıkarma")
        )

        if (sumNoteCursor.moveToFirst()) {
            val totalNoteIndex = sumNoteCursor.getColumnIndex("totalNote")
            val trueCountIndex = trueCountCursor.getColumnIndex("totalTrueCount")
            val wrongCountIndex = wrongCountCursor.getColumnIndex("totalWrongCount")

            if (totalNoteIndex != -1) {

                var trueCount = 0
                var wrongCount = 0

                if (trueCountCursor.moveToFirst()) {
                    trueCount = trueCountCursor.getInt(trueCountIndex)
                }
                if (wrongCountCursor.moveToFirst()) {
                    wrongCount = wrongCountCursor.getInt(wrongCountIndex)
                }

                val questionCount = trueCount + wrongCount

                val note = (trueCount * 100) / questionCount

                analyzedData.add(AnalysisDatas(note = note, totalQuestion = questionCount, trueCount = trueCount, wrongCount = wrongCount))
            } else {
                Log.e("DatabaseError", "Sütun bulunamadı")
            }
        } else {
            Log.e("DatabaseError", "Sorgu sonuç döndürmedi")
        }

        sumNoteCursor.close()
        trueCountCursor.close()
        wrongCountCursor.close()
        return analyzedData
    }

    fun getMultiplyAnalyzedData(): List<AnalysisDatas> {
        val analyzedData = mutableListOf<AnalysisDatas>()
        val db = this.readableDatabase

        // "Toplama" işlemi olan verileri toplayan sorgu
        val sumNoteCursor = db.rawQuery(
            "SELECT SUM($COL_NOTE) AS totalNote FROM $TABLE_NAME WHERE $COL_OPERATION = ?",
            arrayOf("Çarpma")
        )
        val trueCountCursor = db.rawQuery(
            "SELECT SUM($COL_TRUE_COUNT) AS totalTrueCount FROM $TABLE_NAME WHERE $COL_OPERATION = ?",
            arrayOf("Çarpma")
        )
        val wrongCountCursor = db.rawQuery(
            "SELECT SUM($COL_WRONG_COUNT) AS totalWrongCount FROM $TABLE_NAME WHERE $COL_OPERATION = ?",
            arrayOf("Çarpma")
        )

        if (sumNoteCursor.moveToFirst()) {
            val totalNoteIndex = sumNoteCursor.getColumnIndex("totalNote")
            val trueCountIndex = trueCountCursor.getColumnIndex("totalTrueCount")
            val wrongCountIndex = wrongCountCursor.getColumnIndex("totalWrongCount")

            if (totalNoteIndex != -1) {

                var trueCount = 0
                var wrongCount = 0

                if (trueCountCursor.moveToFirst()) {
                    trueCount = trueCountCursor.getInt(trueCountIndex)
                }
                if (wrongCountCursor.moveToFirst()) {
                    wrongCount = wrongCountCursor.getInt(wrongCountIndex)
                }

                val questionCount = trueCount + wrongCount

                val note = (trueCount * 100) / questionCount

                analyzedData.add(AnalysisDatas(note = note, totalQuestion = questionCount, trueCount = trueCount, wrongCount = wrongCount))
            } else {
                Log.e("DatabaseError", "Sütun bulunamadı")
            }
        } else {
            Log.e("DatabaseError", "Sorgu sonuç döndürmedi")
        }

        sumNoteCursor.close()
        trueCountCursor.close()
        wrongCountCursor.close()
        return analyzedData
    }

    fun getDivisionAnalyzedData(): List<AnalysisDatas> {
        val analyzedData = mutableListOf<AnalysisDatas>()
        val db = this.readableDatabase

        // "Toplama" işlemi olan verileri toplayan sorgu
        val sumNoteCursor = db.rawQuery(
            "SELECT SUM($COL_NOTE) AS totalNote FROM $TABLE_NAME WHERE $COL_OPERATION = ?",
            arrayOf("Bölme")
        )
        val trueCountCursor = db.rawQuery(
            "SELECT SUM($COL_TRUE_COUNT) AS totalTrueCount FROM $TABLE_NAME WHERE $COL_OPERATION = ?",
            arrayOf("Bölme")
        )
        val wrongCountCursor = db.rawQuery(
            "SELECT SUM($COL_WRONG_COUNT) AS totalWrongCount FROM $TABLE_NAME WHERE $COL_OPERATION = ?",
            arrayOf("Bölme")
        )

        if (sumNoteCursor.moveToFirst()) {
            val totalNoteIndex = sumNoteCursor.getColumnIndex("totalNote")
            val trueCountIndex = trueCountCursor.getColumnIndex("totalTrueCount")
            val wrongCountIndex = wrongCountCursor.getColumnIndex("totalWrongCount")

            if (totalNoteIndex != -1) {

                var trueCount = 0
                var wrongCount = 0

                if (trueCountCursor.moveToFirst()) {
                    trueCount = trueCountCursor.getInt(trueCountIndex)
                }
                if (wrongCountCursor.moveToFirst()) {
                    wrongCount = wrongCountCursor.getInt(wrongCountIndex)
                }

                val questionCount = trueCount + wrongCount

                val note = (trueCount * 100) / questionCount

                analyzedData.add(AnalysisDatas(note = note, totalQuestion = questionCount, trueCount = trueCount, wrongCount = wrongCount))
            } else {
                Log.e("DatabaseError", "Sütun bulunamadı")
            }
        } else {
            Log.e("DatabaseError", "Sorgu sonuç döndürmedi")
        }

        sumNoteCursor.close()
        trueCountCursor.close()
        wrongCountCursor.close()
        return analyzedData
    }





}
