package com.example.afya.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.afya.R
import com.example.afya.SingleLabTest
import com.example.afya.models.labTests
import com.google.android.material.textview.MaterialTextView

class LabTestsCartAdapter (var context: Context): RecyclerView.Adapter<LabTestsCartAdapter.ViewHolder>() {


    //Create a List and connect it with our model
    var itemList : List<labTests> = listOf() //Its empty

    //Create a Class here, will hold our views in single_lab xml
    inner class  ViewHolder(itemView: View):  RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LabTestsCartAdapter.ViewHolder {
        //access/inflate the single labtests xml
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.single_labtests_cart,
            parent, false)

        return ViewHolder(view) //pass the single lab to ViewHolder
    }

    override fun onBindViewHolder(holder: LabTestsCartAdapter.ViewHolder, position: Int) {
        //Find your 3 text views
        val test_name = holder.itemView.findViewById<MaterialTextView>(R.id.testname)
        val test_description = holder.itemView.findViewById<MaterialTextView>(R.id.description)
        val test_cost = holder.itemView.findViewById<MaterialTextView>(R.id.cost)
        //Assume one Lab, bind data,
        val item = itemList[position]
        test_name.text = item.test_name
        test_description.text = item.test_description
        test_cost.text = item.test_cost+" KES"
        holder.itemView.setOnClickListener {

        }//end




    }

    //count number of items
    override fun getItemCount(): Int {
        return itemList.size  //Count how may Items in the List
    }

    //This is for filtering data
    fun filterList(filterList: List<labTests>){
        itemList = filterList
        notifyDataSetChanged()
    }


    //Earlier we mentioned item List is empty!
    //We will get data from our APi, then bring it to below function
    //The data you bring here must follow the Lab model
    fun setListItems(data: List<labTests>){
        itemList = data //map/link the data to itemlist
        notifyDataSetChanged()
        //Tell this adapter class that now itemList is loaded with data
    }

}
