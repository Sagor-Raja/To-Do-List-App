package com.example.to_dolist

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ToDoActivity : AppCompatActivity() {

    private lateinit var adapter: TaskAdapter
    private val taskList = mutableListOf<Task>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do)

        val etNewTask = findViewById<EditText>(R.id.etNewTask)
        val btnAddTask = findViewById<Button>(R.id.btnAddTask)
        val rvTasks = findViewById<RecyclerView>(R.id.rvTasks)

        // Setup RecyclerView
        adapter = TaskAdapter(taskList) { position ->
            taskList.removeAt(position)
            adapter.notifyDataSetChanged()
            Toast.makeText(this, "Task removed", Toast.LENGTH_SHORT).show()
        }
        
        rvTasks.layoutManager = LinearLayoutManager(this)
        rvTasks.adapter = adapter

        // Add Task logic
        btnAddTask.setOnClickListener {
            val title = etNewTask.text.toString().trim()
            if (title.isNotEmpty()) {
                val newTask = Task(taskList.size + 1, title)
                taskList.add(newTask)
                adapter.notifyItemInserted(taskList.size - 1)
                etNewTask.text.clear()
                // Scroll to the bottom
                rvTasks.scrollToPosition(taskList.size - 1)
            } else {
                Toast.makeText(this, "Please enter a task", Toast.LENGTH_SHORT).show()
            }
        }

        // Back button logic
        findViewById<android.view.View>(R.id.btnBack).setOnClickListener {
            finish()
        }
    }
}