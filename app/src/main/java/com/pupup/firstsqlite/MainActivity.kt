package com.pupup.firstsqlite

import android.annotation.SuppressLint
import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity(),MyAdapter.ourInterface {
    @SuppressLint("Range", "MissingInflatedId")
    lateinit var listItem : ArrayList<User>
    lateinit var myAdapter : MyAdapter
    lateinit var email : EditText
    lateinit var password : EditText
    @SuppressLint("MissingInflatedId", "Range", "NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val helper = MyDBHelper(applicationContext)
        var db = helper.readableDatabase
        var rs = db.rawQuery("SELECT * FROM USERS",null)

        val recyclerView : RecyclerView = findViewById(R.id.recycleView)
        recyclerView.layoutManager = GridLayoutManager(this,1)
        listItem = arrayListOf()
        myAdapter = MyAdapter(this@MainActivity,listItem,this)

        val button : Button = findViewById(R.id.buttonPanel)
         email  = findViewById(R.id.email)
         password  = findViewById(R.id.password)
        button.setOnClickListener {
            val cv = ContentValues()
            cv.put("UNAME",email.text.toString())
            cv.put("PWD",password.text.toString())
            db.insert("USERS", null, cv)
            email.setText("")
            password.setText("")
            email.requestFocus()
            val cursor = db.rawQuery("SELECT * FROM USERS", null)
            if (cursor.moveToFirst()) {
                do {
                    val userId = cursor.getInt(cursor.getColumnIndex("USERID"))
                    val userName = cursor.getString(cursor.getColumnIndex("UNAME"))
                    val password = cursor.getString(cursor.getColumnIndex("PWD"))
                    val data  = User(email = userName.toString(), password = password.toString())
                    listItem.add(data)
                    myAdapter.notifyDataSetChanged()
                } while (cursor.moveToNext())
            }
        }
        val cursor = db.rawQuery("SELECT * FROM USERS", null)
        // get data and set to recycleView
        if (cursor.moveToFirst()) {
            do {
                val userId = cursor.getInt(cursor.getColumnIndex("USERID"))
                val userName = cursor.getString(cursor.getColumnIndex("UNAME"))
                val password = cursor.getString(cursor.getColumnIndex("PWD"))
                val data  = User(email = userName.toString(), password = password.toString(), userId = userId.toLong())
                listItem.add(data)
                myAdapter.notifyDataSetChanged()
            } while (cursor.moveToNext())
        }
        recyclerView.adapter = myAdapter
    }

    // override fun for clickListener it is intereface fun
    override fun clickOnItem(position: Int) {
        // call this delete funcion to and give it to a args like position
        deleteItem(position)
    }
    @SuppressLint("NotifyDataSetChanged", "MissingInflatedId", "Range")
    // make method do delete and updata data from data base useing sqlite database
    private fun deleteItem(position: Int){
        val alertDialog = AlertDialog.Builder(this@MainActivity)
        alertDialog.setPositiveButton("Delete"){ _, _ ->
            // delete the data from sqlite database // starting code
            val deletedUserId = listItem[position].userId
            val dbHelper = MyDBHelper(applicationContext)
            deletedUserId?.toInt()?.let { dbHelper.deleteData(it) }

            listItem.removeAt(position)
            myAdapter.notifyItemRemoved(position)
            myAdapter.notifyDataSetChanged()
            Toast.makeText(this@MainActivity, "Delete Successfully", Toast.LENGTH_SHORT).show()
            // ending code
        }
        alertDialog.setNegativeButton("Update"){ _, _ ->

        }
        val dialog = alertDialog.create()
        dialog.setTitle("Mofify")
        dialog.setMessage("Are you sure!")
        dialog.setIcon(R.drawable.baseline_restore_from_trash_24)
        dialog.show()
    }

}

