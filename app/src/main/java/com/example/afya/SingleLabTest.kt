package com.example.afya

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.afya.helpers.SQLiteCartHelper
import com.google.android.material.textview.MaterialTextView

class SingleLabTest : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_single_lab_test)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
//        fetch 3 material texttview
        val testname = findViewById<MaterialTextView>(R.id.testname)
        val testdescription = findViewById<MaterialTextView>(R.id.testdescription)
        val testcost = findViewById<MaterialTextView>(R.id.testcost)


//        get the information about labtests from intent extra
        val test_name = intent?.extras?.getString("test_name")
        val test_description = intent?.extras?.getString("test_description")
        val test_cost = intent?.extras?.getString("test_cost")
        val test_id = intent?.extras?.getString("test_id")

//        set textviews_values
        testname.text = test_name
        testdescription.text = test_description
        testcost.text = "KES" + test_cost

//        fetch add to cart button
        val addtocart = findViewById<Button>(R.id.addtocartbutton)
        addtocart.setOnClickListener {

            val helper = SQLiteCartHelper(applicationContext)
            helper.InsertData(test_id!!,test_name!!,test_description!!,test_cost!!)
        }

    }
}