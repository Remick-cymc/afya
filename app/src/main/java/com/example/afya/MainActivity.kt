package com.example.afya

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.afya.adapters.labTestsAdapter
import com.example.afya.helpers.ApiHelper
import com.example.afya.models.labTests
import com.google.gson.GsonBuilder
import org.json.JSONArray
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

//    declare globals
    lateinit var progress: ProgressBar
    lateinit var swiper: SwipeRefreshLayout
    lateinit var recyclerview:RecyclerView
    lateinit var labTestsAdapter: labTestsAdapter
    lateinit var itemList: List<labTests>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        progress = findViewById(R.id.progress)
        recyclerview = findViewById(R.id.recycler)
        swiper = findViewById(R.id.swiperrefresh)
        val search = findViewById<EditText>(R.id.search)
        search.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                
            }

            override fun onTextChanged(texttyped: CharSequence?, start: Int, before: Int, count: Int) {
                filter(texttyped.toString())
            }

            override fun afterTextChanged(s: Editable?) {
                
            }

        })

//        link the adapter
        labTestsAdapter = labTestsAdapter(applicationContext)
//        set recycler adapter
        recyclerview.layoutManager = LinearLayoutManager(applicationContext)
        recyclerview.setHasFixedSize(true)


//        function to fetch labtets
        getLabTest()

//        use swiper refresh to refresh our data
        swiper.setOnRefreshListener {
            getLabTest()
        }
    }

    private fun filter(text: String) {
        // creating a new array list to filter our data.
        val filteredlist: ArrayList<labTests> = ArrayList()

        // running a for loop to compare elements.
        for (item in itemList) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.test_name.lowercase().contains(text.lowercase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            //Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show()
            labTestsAdapter.filterList(filteredlist)
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            labTestsAdapter.filterList(filteredlist)
        }

    }

    private fun getLabTest() {
        val api = "https://tukeiremick.pythonanywhere.com/api/viewlab_test"


        val helper = ApiHelper(applicationContext)
        helper.get(api, object:ApiHelper.CallBack{
            override fun onSuccess(result: JSONArray?) {
//                take above result to adapter
//                convert the above result from  json array
                val gson = GsonBuilder().create()
                itemList = gson.fromJson(result.toString(), Array<labTests>::class.java).toList()

//                finally our adapter has data
                labTestsAdapter.setListItems(itemList)

//                for the sake of recyclerview, the items are looping
                recyclerview.adapter = labTestsAdapter
//                if the user sees the labtest, the progressbar should be gone
                progress.visibility = View.GONE
//                set refreshing to false when records appear
                swiper.isRefreshing = false
            }

            override fun onSuccess(result: JSONObject?) {

            }

            override fun onFailure(result: String?) {
                Toast.makeText(applicationContext, "Error" +result.toString(), Toast.LENGTH_SHORT).show()
                progress.visibility = View.GONE
            }

        })
    }
}