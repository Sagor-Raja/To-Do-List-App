package com.example.to_dolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GoalAdapter(
    private val goals: MutableList<Goal>,
    private val onItemClick: (Goal) -> Unit
) : RecyclerView.Adapter<GoalAdapter.GoalViewHolder>() {

    class GoalViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvGoalCat: TextView = itemView.findViewById(R.id.tvGoalCat)
        val tvGoalName: TextView = itemView.findViewById(R.id.tvGoalName)
        val tvPerc: TextView = itemView.findViewById(R.id.tvPerc)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GoalViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_goal, parent, false)
        return GoalViewHolder(view)
    }

    override fun onBindViewHolder(holder: GoalViewHolder, position: Int) {
        val goal = goals[position]
        holder.tvGoalCat.text = goal.category
        holder.tvGoalName.text = goal.title
        holder.tvPerc.text = "${goal.progress}%"

        holder.itemView.setOnClickListener { onItemClick(goal) }
    }

    override fun getItemCount(): Int = goals.size
}