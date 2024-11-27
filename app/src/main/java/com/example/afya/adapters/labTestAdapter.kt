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



class labTestsAdapter(var context: Context):
    RecyclerView.Adapter<labTestsAdapter.ViewHolder>() {


    //Create a List and connect it with our model
    var itemList : List<labTests> = listOf() //Its empty

    //Create a Class here, will hold our views in single_lab xml
    inner class  ViewHolder(itemView: View):  RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): labTestsAdapter.ViewHolder {
        //access/inflate the single labtests xml
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.single_labtests,
            parent, false)

        return ViewHolder(view) //pass the single lab to ViewHolder
    }

    override fun onBindViewHolder(holder: labTestsAdapter.ViewHolder, position: Int) {
        //Find your 3 text views
        val test_name = holder.itemView.findViewById<MaterialTextView>(R.id.test_name)
        val test_description = holder.itemView.findViewById<MaterialTextView>(R.id.test_description)
        val test_cost = holder.itemView.findViewById<MaterialTextView>(R.id.test_cost)
        //Assume one Lab, bind data,
        val item = itemList[position]
        test_name.text = item.test_name
        test_description.text = item.test_description
        test_cost.text = item.test_cost+" KES"
        holder.itemView.setOnClickListener {
            //To Navigate to single item Activity
            val intent = Intent(context,SingleLabTest::class.java)

//            we want to carry the content of lab test to a singlelabtest
//            we will use shared preference to store theat information
            intent.putExtra("test_id",item.test_id)
            intent.putExtra("test_name", item.test_name)
            intent.putExtra("test_description",item.test_description)
            intent.putExtra("test_cost",item.test_cost)
            intent.putExtra("test_discount",item.test_discount)
            intent.putExtra("availability",item.availability)
            intent.putExtra("more_info",item.more_info)



            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.startActivity(intent)
        }//end



        // Toast.makeText(context, "yyy"+item.test_cost, Toast.LENGTH_SHORT).show()
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
