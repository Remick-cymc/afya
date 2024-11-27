package com.example.afya

import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.afya.adapters.LabTestsCartAdapter
import com.example.afya.helpers.SQLiteCartHelper
import com.google.android.material.textview.MaterialTextView

class MyCart : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_my_cart)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//        fetch
        val mytotal = findViewById<MaterialTextView>(R.id.total)
        val helper = SQLiteCartHelper(applicationContext)
//        we want to use a function called total coast()
        mytotal.text = "Total :" + helper.totalCoast()
        val recyclerview = findViewById<RecyclerView>(R.id.recycler)

        val button = findViewById<Button>(R.id.checkout)

        recyclerview.layoutManager = LinearLayoutManager(applicationContext)
        recyclerview.setHasFixedSize(true)

        val adapter = LabTestsCartAdapter(applicationContext)
        adapter.setListItems(helper.getAllItems())
        recyclerview.adapter=adapter //link adapter to recyclerview

    }
}