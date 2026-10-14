package ca.gbc.comp3074.porter_colin.lab4

import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Switch
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var etAge: EditText
    private lateinit var swActive: Switch
    private lateinit var btnAdd: Button
    private lateinit var btnViewAll: Button
    private lateinit var lvCustomerList: ListView

    private lateinit var databaseHelper: DataBaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etName = findViewById<EditText>(R.id.etName)
        etAge = findViewById<EditText>(R.id.etAge)
        swActive = findViewById<Switch>(R.id.swActive)
        btnViewAll = findViewById<Button>(R.id.btnViewAll)
        btnAdd = findViewById<Button>(R.id.btnAdd)
        lvCustomerList = findViewById<ListView>(R.id.lvCustomerList)

        databaseHelper = DataBaseHelper(this)

        showCustomersOnListView()

        btnViewAll.setOnClickListener {
            showCustomersOnListView()
        }
        btnAdd.setOnClickListener {
            try {
                val customer = CustomerModel(
                    name = etName.text.toString(),
                    age = etAge.text.toString().toInt(),
                    isActive = swActive.isChecked
                )
                val success = databaseHelper.addOne(customer)
                Toast.makeText(this, "Customer added successfully", Toast.LENGTH_LONG)
                    .show()
                if (success){
                    etName.text.clear()
                    etAge.text.clear()
                    swActive.isChecked = false
                    showCustomersOnListView()
                }
            }catch (e: Exception){
                Toast.makeText(this, "An error occurred", Toast.LENGTH_LONG).show()
                Log.e("ADD", "Error creating customer", e)
            }
        }
        lvCustomerList.setOnItemLongClickListener{
            parent, _, position, _ ->
            val clickedCustomer = parent.getItemAtPosition(position) as CustomerModel
            val success = databaseHelper.deleteOne(clickedCustomer)
            Toast.makeText(this, "Customer deleted: $clickedCustomer",
                Toast.LENGTH_LONG).show()
            showCustomersOnListView()
            success
        }
    }

    private fun showCustomersOnListView(){
        val customers = databaseHelper.getEveryone()
        val adapter = CustomerAdapter(
            this,
            customers
        )
        lvCustomerList.adapter = adapter
    }
}