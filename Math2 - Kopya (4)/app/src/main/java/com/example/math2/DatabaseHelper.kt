package com.example.math2

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    companion object {
        private const val DATABASE_NAME = "ReportDB.db"
        private const val TABLE_NAME = "reports"
        private const val COL_ID = "id"
        private const val COL_DATA1 = "data1"
        private const val COL_DATA2 = "data2"
        private const val COL_DATA3 = "data3"
        private const val COL_DATA4 = "data4"
        private const val COL_DATA5 = "data5"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = "CREATE TABLE $TABLE_NAME ($COL_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COL_DATA1 INTEGER, $COL_DATA2 INTEGER, $COL_DATA3 INTEGER, $COL_DATA4 INTEGER, $COL_DATA5 TEXT)"
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertData(data1: Int, data2: Int, data3: Int, data4: Int, data5: String): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COL_DATA1, data1)
            put(COL_DATA2, data2)
            put(COL_DATA3, data3)
            put(COL_DATA4, data4)
            put(COL_DATA5, data5)
        }
        val result = db.insert(TABLE_NAME, null, contentValues)
        db.close()
        return result != -1L
    }

    fun getAllData(): List<List<Any>> {
        val dataList = mutableListOf<List<Any>>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        if (cursor.moveToFirst()) {
            do {
                val row = listOf(
                    cursor.getInt(1),
                    cursor.getInt(2),
                    cursor.getInt(3),
                    cursor.getInt(4),
                    cursor.getString(5)
                )
                dataList.add(row)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return dataList
    }
}