package com.example.afya.helpers

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.afya.models.labTests


class SQLiteCartHelper(context: Context) : SQLiteOpenHelper(context, "cart.db", null, 1) {

//    make context visible inside all the functions

    val context = context
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE IF NOT EXISTS cart (test_id Integer PRIMARY KEY, test_name varchar, test_description text, test_cost integer)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS cart")
    }

//    sqlite helps to store data locally inside your phone

    //    Insert / save data
    fun InsertData(
        test_id: String,
        test_name: String,
        test_description: String,
        test_cost: String
    ) {


        val db = this.writableDatabase
        val values = ContentValues()
        values.put("test_id", test_id)
        values.put("test_name", test_name)
        values.put("test_description", test_description)
        values.put("test_cost", test_cost)

//    save the data in the table
        val result: Long = db.insert("cart", null, values)
        if(result < 1){
            println("Failed to add")
            Toast.makeText(context, "Item Already in Cart", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context, "Item Added to cart", Toast.LENGTH_SHORT).show()
        }



    }//end of function

//    count the number of items in the cart
    fun getItemCount():Int{
        val db = this.readableDatabase
        val result:Cursor = db.rawQuery("SELECT * FROM cart", null)
//        return the result count
        return result.count


    }//end of function

//    clear all records
fun clearCart(){
    val db = this.readableDatabase
    db.delete("cart", null,null )
    println("Cart Cleared")
    Toast.makeText(context, "Cart Cleared", Toast.LENGTH_SHORT).show()
}

//    remove item by test id
    fun clearCartById(test_id: String){
    val db = this.readableDatabase
    db.delete("cart","test_id=?", arrayOf(test_id) )
    println("Item with id $test_id is cleared")
    Toast.makeText(context, "Item id $test_id Cart Cleared", Toast.LENGTH_SHORT).show()


    }
//    find the total cost
    fun totalCoast():Double {
    val db = this.readableDatabase
    val result: Cursor = db.rawQuery("select test_cost from cart", null)
//    set total to zero
    var total: Double = 0.0

//    we loop throught all items
//    and then we get the cost of each item abd add all
    while (result.moveToNext()) {
        total = total + result.getDouble(0)

    }
    return total
}
    //Get all items from the Cart
    fun getAllItems(): ArrayList<labTests> {
        val db = this.writableDatabase
        val items = ArrayList<labTests>()
        val result: Cursor = db.rawQuery("select * from cart", null)
        //Lets Add  all  rows into the items arrayList
        while (result.moveToNext())
        {
            val model = labTests()
            //Map rows to the model
            model.test_id = result.getString(0)  //Assume one row, test_id
            model.test_name = result.getString(1)  //Assume one row, test_name
            model.test_cost = result.getString(2)  //Assume one row, test_cost
            model.test_description = result.getString(4)  //Assume one row, test_description
            items.add(model)//add model to ArrayList
        }//end while
        return items
    }//end function
}