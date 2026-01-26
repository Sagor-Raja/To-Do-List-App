package com.example.to_dolist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.ai.client.generativeai.GenerativeModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class GoalProgressActivity : AppCompatActivity() {

    private val apiKey = "AIzaSyD7kQef4H7_wZVKFLudQLl_UWFcLQZdf-Q"
    private val generativeModel = GenerativeModel(modelName = "gemini-1.5-flash", apiKey = apiKey)

    private lateinit var adapter: GoalAdapter
    private val goalList = mutableListOf<Goal>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal_progress)

        val rvGoalsList = findViewById<RecyclerView>(R.id.rvGoalsList)
        val btnCreateGoal = findViewById<MaterialButton>(R.id.btnCreateGoal)

        // Setup RecyclerView
        adapter = GoalAdapter(goalList) { goal ->
            navigateToDetails(goal)
        }
        rvGoalsList.layoutManager = LinearLayoutManager(this)
        rvGoalsList.adapter = adapter

        // Load Default Professional Goals
        loadDefaultGoals()

        btnCreateGoal.setOnClickListener {
            showSmartGoalDialog()
        }

        findViewById<android.view.View>(R.id.headerMission)?.setOnClickListener {
            finish()
        }
    }

    private fun loadDefaultGoals() {
        goalList.clear()
        goalList.add(Goal("Financial Freedom", "⏳ 2 Years", "🎯 YEARLY: Build $120k Portfolio", "📆 MONTHLY: Invest $10k", "🗓 WEEKLY: Review Market", "☀️ TODAY: Analyze Dividends", "FINANCIAL", 45))
        goalList.add(Goal("Physical Peak", "⏳ 6 Months", "🎯 YEARLY: Reach 15% Body Fat", "📆 MONTHLY: Gain 1kg Muscle", "🗓 WEEKLY: 5 Day Workout", "☀️ TODAY: Leg Day Session", "HEALTH", 70))
        goalList.add(Goal("English Fluency", "⏳ 3 Months", "🎯 YEARLY: Native Fluency", "📆 MONTHLY: Finish 4 Masteries", "🗓 WEEKLY: 2 Mock Debates", "☀️ TODAY: 30m Mirror Practice", "SKILL", 30))
        adapter.notifyDataSetChanged()
    }

    private fun navigateToDetails(goal: Goal) {
        val intent = Intent(this, GoalDetailsActivity::class.java).apply {
            putExtra("GOAL_TITLE", goal.title)
            putExtra("GOAL_TIME", goal.time)
            putExtra("YEARLY_PLAN", goal.yearly)
            putExtra("MONTHLY_PLAN", goal.monthly)
            putExtra("WEEKLY_PLAN", goal.weekly)
            putExtra("TODAY_PLAN", goal.today)
        }
        startActivity(intent)
    }

    private fun showSmartGoalDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_transaction, null)
        val etGoalName = dialogView.findViewById<EditText>(R.id.etTransactionTitle)
        val etDuration = dialogView.findViewById<EditText>(R.id.etTransactionAmount)

        etGoalName.hint = "Goal Name (e.g. Master Coding)"
        etDuration.hint = "Duration (e.g. 6 Months)"
        etDuration.inputType = android.text.InputType.TYPE_CLASS_TEXT

        MaterialAlertDialogBuilder(this)
            .setTitle("AI Smart Planner")
            .setView(dialogView)
            .setPositiveButton("Generate") { _, _ ->
                val goalName = etGoalName.text.toString()
                val duration = etDuration.text.toString()
                if (goalName.isNotEmpty()) {
                    generateAiPlan(goalName, duration)
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun generateAiPlan(goalName: String, duration: String) {
        val prompt = "Create a professional $duration master plan for: $goalName. " +
                "Provide 4 distinct lines starting exactly with: YEARLY:, MONTHLY:, WEEKLY:, TODAY:."

        Toast.makeText(this, "AI is drafting your roadmap...", Toast.LENGTH_LONG).show()

        MainScope().launch {
            try {
                val response = generativeModel.generateContent(prompt)
                val fullPlan = response.text ?: ""

                val yearly = fullPlan.lines().find { it.contains("YEARLY:", true) } ?: "YEARLY: Milestone set"
                val monthly = fullPlan.lines().find { it.contains("MONTHLY:", true) } ?: "MONTHLY: Key actions"
                val weekly = fullPlan.lines().find { it.contains("WEEKLY:", true) } ?: "WEEKLY: Check progress"
                val today = fullPlan.lines().find { it.contains("TODAY:", true) } ?: "TODAY: Start first step"

                val newGoal = Goal(goalName, "⏳ $duration", yearly, monthly, weekly, today, "CUSTOM", 0)
                goalList.add(0, newGoal)
                adapter.notifyItemInserted(0)
                Toast.makeText(this@GoalProgressActivity, "New Goal Created!", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(this@GoalProgressActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }
}