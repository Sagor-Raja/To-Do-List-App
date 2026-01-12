package com.example.to_dolist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class DailyPlannerActivity : AppCompatActivity() {

    private lateinit var adapter: RoutineAdapter
    private val routineList = ArrayList<Routine>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_planner)

        val rvDailyPlan = findViewById<RecyclerView>(R.id.rvDailyPlan)

        // Adding some sample routine data
        routineList.add(Routine("06:30 AM", "Morning Exercise", "Yoga & Warm-up"))
        routineList.add(Routine("07:30 AM", "Breakfast", "Healthy meal to start the day"))
        routineList.add(Routine("09:00 AM", "Office Work", "Focus on important tasks"))
        routineList.add(Routine("01:30 PM", "Lunch Break", "Nutritious lunch & rest"))
        routineList.add(Routine("06:00 PM", "Evening Walk", "Refreshing walk in nature"))
        routineList.add(Routine("10:30 PM", "Sleep", "Good night rest"))

        adapter = RoutineAdapter(routineList)
        rvDailyPlan.layoutManager = LinearLayoutManager(this)
        rvDailyPlan.adapter = adapter

        // Back button logic
        findViewById<android.view.View>(R.id.btnBack).setOnClickListener {
            finish()
        }
    }
}