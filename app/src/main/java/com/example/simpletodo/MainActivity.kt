package com.example.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    var listOfTask = mutableListOf<String>()
    lateinit var adapter: TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongCLickListener = object : TaskItemAdapter.OnLongCLickListener{
            override fun onLongItemClicked(position: Int) {
                //1. Remove item from the list
                listOfTask.removeAt(position)
                //2. Notify the adapter that our data set has changed
                adapter.notifyDataSetChanged()

                saveItems()
            }
        }

        //1. Lets detect when User Click on Add Button
    //  findViewById<Button>(R.id.button).setOnClickListener {
    //        // Code in here gonna be executed when user Clicks on button
    //  Log.i("lonelyness", "User Clicked on button")
    //  }

        loadItems()

      //  listOfTask.add("Go to Preach")
      //  listOfTask.add("Read Bible")

        // Lookup the recyclerview in activity layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        // Create adapter passing in the sample user data
        adapter = TaskItemAdapter(listOfTask, onLongCLickListener)
        // Attach the adapter to the recyclerview to populate items
        recyclerView.adapter = adapter
        // Set layout manager to position the items
        recyclerView.layoutManager = LinearLayoutManager(this)

        //Set up the button and input field so that the user can enter a task and add it to the list
        val inputTextField =  findViewById<EditText>(R.id.addTaskField)
        //Get a reference to the button
        //and then set an onclicklistener
        findViewById<Button>(R.id.button).setOnClickListener{
            //1- Grab the text the user has inputted into @id/addTaskField
            val userInputtedTask = findViewById<EditText>(R.id.addTaskField).text.toString()

            //2- Add the String to our list of tasks : listOfTask
            listOfTask.add(userInputtedTask)

            //Notify the adapter that our data has been updated
            adapter.notifyItemInserted(listOfTask.size - 1)

            //3- Reset text field
            inputTextField.setText("")

            saveItems()
        }
    }
    // Save the data the user has inputted
    // Save data by writing and reading from a file

    // Get the file we need
    fun getDataFile() : File{

        //Every line is going to represent a specific task in our list of tasks
        return File(filesDir, "data.txt")
    }
    //Create a method to get the file we need

    // Load the items by reading every line in the data file
    fun loadItems(){
        try {
        listOfTask = org.apache.commons.io.FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        }catch (ioExeption: IOException) {
            ioExeption.printStackTrace()
        }
    }

    //Save items by writing them into our data file
    fun saveItems() {
        try {
            org.apache.commons.io.FileUtils.writeLines(getDataFile(), listOfTask)
        }catch (ioExeption: IOException){
            ioExeption.printStackTrace()
        }

    }
}