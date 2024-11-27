package com.example.afya

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textview.MaterialTextView

class screen1 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_screen1)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//        fech the material text view and floating action button
        val skiptext = findViewById<MaterialTextView>(R.id.skip)
        val floatingbtn = findViewById<FloatingActionButton>(R.id.fab)

//        explicit intent to go to main activity when user click skip
        skiptext.setOnClickListener{
            val skipintent =  Intent(applicationContext, SignupActivity::class.java)
            startActivity(skipintent)
        }

        floatingbtn.setOnClickListener{
            val intent = Intent(applicationContext, screen2::class.java)
            startActivity(intent)
        }

    }
}