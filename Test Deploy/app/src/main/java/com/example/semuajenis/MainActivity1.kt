package com.example.semuajenis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button

class MainActivity1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main1)

        findViewById<Button>(R.id.backPain).setOnClickListener(){
            intent = Intent(this, MainActivity::class.java)
            intent.putExtra("id","backPain")
            startActivity(intent)
        }
        findViewById<Button>(R.id.anxiety).setOnClickListener(){
            intent = Intent(this, MainActivity::class.java)
            intent.putExtra("id","anxiety")
            startActivity(intent)
        }
        findViewById<Button>(R.id.flexibility).setOnClickListener(){
            intent = Intent(this, MainActivity::class.java)
            intent.putExtra("id","flexibility")
            startActivity(intent)
        }
        findViewById<Button>(R.id.neckPain).setOnClickListener(){
            intent = Intent(this, MainActivity::class.java)
            intent.putExtra("id","neckPain")
            startActivity(intent)
        }
    }

}