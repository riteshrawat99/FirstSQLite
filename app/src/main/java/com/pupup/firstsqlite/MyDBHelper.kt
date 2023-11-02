package com.pupup.firstsqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MyDBHelper(context: Context) : SQLiteOpenHelper(context,"USERDB",null,1) {

    override fun onCreate(db : SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE USERS(USERID INTEGER PRIMARY KEY AUTOINCREMENT,UNAME TEXT,PWD TEXT)")

        db?.execSQL("INSERT INTO USERS(UNAME,PWD) VALUES('ritesh@gmail.com','ritesh')")
        db?.execSQL("INSERT INTO USERS(UNAME,PWD) VALUES('nothing@gmail.com','nothing')")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
            
    }

    // make companion object and assign value like table name userid
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = ""
        private const val TABLE_NAME = "USERS"
        private const val COLUMN_ID = "USERID"
    }
    // make delete function to delete record from sqlite database 
    fun deleteData(id: Int): Int {
        val db = writableDatabase
        return db.delete(TABLE_NAME, "$COLUMN_ID=?", arrayOf(id.toString()))
    }

    fun updateData(userId: Int, newEmail: String, newPassword: String): Int {
        val db = writableDatabase
        val values = ContentValues()
        values.put("UNAME", newEmail)
        values.put("PWD", newPassword)
        return db.update("USERS", values, "USERID = ?", arrayOf(userId.toString()))
    }

}