package com.example.afya

import android.app.DatePickerDialog
import android.os.Bundle
import android.provider.CallLog.Locations
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.afya.helpers.ApiHelper
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.gson.GsonBuilder
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class SignupActivity : AppCompatActivity() {

//    we need a button and an edittext for datepicker
    private lateinit var buttondatepicker: Button
    private  lateinit var edittextdate: EditText
//    we need a spinner, a textview
    private lateinit var spinner:Spinner
    private lateinit var selectedItemText:TextView

//    we need location model
//    private lateinit var locations: List<Locations>
private lateinit var locations:List<com.example.afya.models.Locations>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signup)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        spinner = findViewById(R.id.spinner)
        selectedItemText = findViewById(R.id.selecteditemtext)
//        we need to fetch the locations from the api
        val api = "https://tukeiremick.pythonanywhere.com/api/viewLocations"
        val helper = ApiHelper(applicationContext)
        helper.get(api,object: ApiHelper.CallBack{
            override fun onSuccess(result: JSONArray?) {
                //JSON Array Is Returned - Locations
                // Convert JSONArray to ArrayList<Locations>
                val gson = GsonBuilder().create()
//                locations = gson.fromJson(result.toString(),Array<Locations>::class.java).toList()
                locations = gson.fromJson(result.toString(),Array<com.example.afya.models.Locations>::class.java).toList()
                val locationNames = locations.map { it.location }

                val adapter = ArrayAdapter(applicationContext,
                    android.R.layout.simple_spinner_item, locationNames)
                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                // Set the adapter to the spinner
                spinner.adapter = adapter
            }
            override fun onSuccess(result: JSONObject?) {

            }

            override fun onFailure(result: String?) {

            }

        })

        buttondatepicker = findViewById(R.id.buttondatepicker)
        edittextdate = findViewById(R.id.edittextdate)

//        we will set on click listener to the button date picker
//        when a user click it, it should open a date picker dialog
        buttondatepicker.setOnClickListener{
            showDatePickerDialog()
        }


//        fetch all the edittext by id
        val surname = findViewById<TextInputEditText>(R.id.surname)

        val others = findViewById<TextInputEditText>(R.id.others)

        val email = findViewById<TextInputEditText>(R.id.email)

        val phone = findViewById<TextInputEditText>(R.id.phone)

        val password = findViewById<TextInputEditText>(R.id.password)

        val confirmpassword = findViewById<TextInputEditText>(R.id.confirm)


//        fecth the two radio buttons
        val maleradio = findViewById<RadioButton>(R.id.male)
        val femaleradio = findViewById<RadioButton>(R.id.female)

//        fetch create button
        val create = findViewById<MaterialButton>(R.id.create)
//        set click listener of the button
        create.setOnClickListener {
//            lets chesk the gender selected
            var gender = "N/A"
            if (maleradio.isChecked){
                gender="male"

            }

            if(femaleradio.isChecked){
                gender = "female"
            }
//            we want the location id
            //Check the selected location
            var location_id = ""
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                    val selectedLocation = locations[position]
                    location_id = selectedLocation.location_id
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    Toast.makeText(applicationContext,
                        "Please Select a location", Toast.LENGTH_SHORT).show()
                }
            }//end

//            Toast.makeText(applicationContext, "$gender", Toast.LENGTH_SHORT).show()
//            check if the password are matching
            if(password.text.toString() != confirmpassword.text.toString()){
//                the passwords dont match
                Toast.makeText(applicationContext, "password dont match", Toast.LENGTH_SHORT).show()
            }else{
//                the passwords match
//                Toast.makeText(applicationContext, "The password match", Toast.LENGTH_SHORT).show()
//                if the password match the we want to register the user/member
                val api = "https://tukeiremick.pythonanywhere.com/api/member_signup"
                val helper = ApiHelper(applicationContext)

                val body = JSONObject()
                body.put("email", email.text.toString())
                body.put("sur_name", surname.text.toString())
                body.put("others", others.text.toString())
                body.put("gender", gender       )
                body.put("phone", phone.text.toString())
                body.put("DOB",  edittextdate.toString())
                body.put("status", "TRUE")
                body.put("password", password.text.toString())
                body.put("location_id", "2" )

//                now we need to post the data
                helper.post(api, body, object: ApiHelper.CallBack{
                    override fun onSuccess(result: JSONArray?) {

                    }

                    override fun onSuccess(result: JSONObject?) {
                        Toast.makeText(applicationContext, result.toString(), Toast.LENGTH_SHORT).show()

                    }

                    override fun onFailure(result: String?) {
                        Toast.makeText(applicationContext, result.toString(), Toast.LENGTH_SHORT).show()
                    }

                })
            }
        }

    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        // Create a date picker dialog and set the current date as the default selection
        val datePickerDialog = DatePickerDialog(
            this,
            { _: DatePicker, year: Int, month: Int, day: Int ->
                val selectedDate = formatDate(year, month, day)
                edittextdate.setText(selectedDate)
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        // Show the date picker dialog, user cannot pick tomorrow
        datePickerDialog.datePicker.maxDate = System.currentTimeMillis() - 568080000000;
        datePickerDialog.show()
    }

    private fun formatDate(year: Int, month: Int, day: Int): String {
        val calendar = Calendar.getInstance()
        calendar.set(year,month,day)
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return dateFormat.format(calendar.time)
    }
}