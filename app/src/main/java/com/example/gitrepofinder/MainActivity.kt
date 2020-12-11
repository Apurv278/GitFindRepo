package com.example.gitrepofinder

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sEdt = findViewById<EditText>(R.id.edit_txt)

        val btn = findViewById<Button>(R.id.button)
        btn.setOnClickListener {
            val intent = Intent(this, SearchActivity::class.java)
            intent.putExtra("searchItem", sEdt.text.toString())
            startActivity(intent)
        }
    }
}