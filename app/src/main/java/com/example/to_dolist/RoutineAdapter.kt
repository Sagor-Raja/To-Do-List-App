package com.example.to_dolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RoutineAdapter(private val routines: List<Routine>) :
    RecyclerView.Adapter<RoutineAdapter.RoutineViewHolder>() {

    class RoutineViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvTime: TextView = itemView.findViewById(R.id.tvTime)
        val tvRoutineTitle: TextView = itemView.findViewById(R.id.tvRoutineTitle)
        val tvRoutineSub: TextView = itemView.findViewById(R.id.tvRoutineSub)
        val ivIcon: ImageView = itemView.findViewById(R.id.ivIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoutineViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_routine_card, parent, false)
        return RoutineViewHolder(view)
    }

    override fun onBindViewHolder(holder: RoutineViewHolder, position: Int) {
        val routine = routines[position]
        holder.tvTime.text = routine.time
        holder.tvRoutineTitle.text = routine.title
        holder.tvRoutineSub.text = routine.subtitle
        holder.ivIcon.setImageResource(routine.iconRes)
    }

    override fun getItemCount(): Int = routines.size
}