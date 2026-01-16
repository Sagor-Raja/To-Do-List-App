package com.example.to_dolist

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaskAdapter(
    private val tasks: MutableList<Task>,
    private val onStatusChanged: () -> Unit,
    private val onDeleteClick: (Int) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvSerial: TextView = itemView.findViewById(R.id.tvSerial)
        val tvTaskName: TextView = itemView.findViewById(R.id.tvTaskName)
        val btnDone: View = itemView.findViewById(R.id.btnDone)
        val btnCancel: View = itemView.findViewById(R.id.btnCancel)
        val btnDelete: ImageView = itemView.findViewById(R.id.btnDeleteTask)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_todo_pro, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.tvSerial.text = (position + 1).toString()
        holder.tvTaskName.text = task.title

        updateTaskStyle(holder.tvTaskName, task)

        holder.btnDone.setOnClickListener {
            task.isDone = !task.isDone
            if (task.isDone) task.isCancelled = false
            notifyItemChanged(position)
            onStatusChanged() // Notify activity to update progress
        }

        holder.btnCancel.setOnClickListener {
            task.isCancelled = !task.isCancelled
            if (task.isCancelled) task.isDone = false
            notifyItemChanged(position)
            onStatusChanged() // Notify activity to update progress
        }

        holder.btnDelete.setOnClickListener {
            onDeleteClick(position)
            onStatusChanged() // Notify activity to update progress after deletion
        }
    }

    private fun updateTaskStyle(textView: TextView, task: Task) {
        if (task.isDone) {
            textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            textView.alpha = 0.5f
        } else if (task.isCancelled) {
            textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            textView.alpha = 0.5f
        } else {
            textView.paintFlags = textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
            textView.alpha = 1.0f
        }
    }

    override fun getItemCount(): Int = tasks.size
}