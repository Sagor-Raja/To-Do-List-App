package com.example.to_dolist

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.LinearProgressIndicator
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class GoalDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal_details)

        // 1. Get Data from Intent
        val title = intent.getStringExtra("GOAL_TITLE") ?: "Goal Progress"
        val timeRemaining = intent.getStringExtra("GOAL_TIME") ?: "Tracking..."
        val yearlyPlan = intent.getStringExtra("YEARLY_PLAN") ?: "Strategic Vision"
        val monthlyPlan = intent.getStringExtra("MONTHLY_PLAN") ?: "Milestone Execution"
        val weeklyPlan = intent.getStringExtra("WEEKLY_PLAN") ?: "Progress Audit"
        val todayTask = intent.getStringExtra("TODAY_PLAN") ?: "Core Strategic Task"

        // 2. Bind Views
        val tvTitle = findViewById<TextView>(R.id.tvGoalDetailTitle)
        val tvTime = findViewById<TextView>(R.id.tvRemainingTime)
        val tvYearly = findViewById<TextView>(R.id.tvYearly)
        val tvMonthly = findViewById<TextView>(R.id.tvMonthly)
        val tvWeekly = findViewById<TextView>(R.id.tvWeekly)
        val tvDailyRoadmap = findViewById<TextView>(R.id.tvDailyRoadmap)
        val tvTodayTask = findViewById<TextView>(R.id.tvTodayTask)
        
        val progressIndicator = findViewById<LinearProgressIndicator>(R.id.progressOverall)
        val tvProgressInfo = findViewById<TextView>(R.id.tvProgressInfo)

        // 3. Set Data to Views
        tvTitle.text = title
        tvTime.text = timeRemaining
        tvYearly.text = yearlyPlan
        tvMonthly.text = monthlyPlan
        tvWeekly.text = weeklyPlan
        tvDailyRoadmap.text = "DAILY: Core Strategic Focus"
        tvTodayTask.text = todayTask

        // Dynamic Progress Indicator
        val progress = when(title) {
            "Financial Freedom" -> 45
            "Physical Peak" -> 70
            "English Fluency" -> 30
            else -> 20
        }
        progressIndicator.progress = progress
        tvProgressInfo.text = "$progress%"

        // 4. Detailed Click Listeners with Action Plans
        tvYearly.setOnClickListener {
            val details = "🎯 **MASTER VISION**\n\n" +
                    "• Define ultimate outcome: $yearlyPlan\n" +
                    "• Establish structural pillars for success.\n" +
                    "• Set semi-annual performance reviews.\n" +
                    "• Build required environment & resources."
            showInfoDialog("Yearly Strategic Plan", details)
        }

        tvMonthly.setOnClickListener {
            val details = "📆 **MONTHLY EXECUTION**\n\n" +
                    "• Core Focus: $monthlyPlan\n" +
                    "• Breakdown into 4 high-leverage milestones.\n" +
                    "• Analyze monthly resource consumption.\n" +
                    "• Adjust strategy based on previous data."
            showInfoDialog("Monthly Milestone Guide", details)
        }

        tvWeekly.setOnClickListener {
            val details = "🗓 **WEEKLY AUDIT**\n\n" +
                    "• Critical Steps: $weeklyPlan\n" +
                    "• Performance audit every Sunday night.\n" +
                    "• Re-align with the Monthly objective.\n" +
                    "• Optimize time allocation for major tasks."
            showInfoDialog("Weekly Sprint Details", details)
        }

        tvDailyRoadmap.setOnClickListener {
            val details = "☀️ **DAILY PROTOCOL**\n\n" +
                    "• Immediate Priority: $todayTask\n" +
                    "• First 90 minutes dedicated to this task.\n" +
                    "• No distractions during execution.\n" +
                    "• Mark as DONE to build momentum."
            showInfoDialog("Daily High-Leverage Task", details)
        }

        // Button Actions
        findViewById<MaterialButton>(R.id.btnDone).setOnClickListener {
            Toast.makeText(this, "Mission milestone marked DONE! 🚀", Toast.LENGTH_SHORT).show()
        }

        findViewById<MaterialButton>(R.id.btnSkip).setOnClickListener {
            Toast.makeText(this, "Task skipped. Stay disciplined tomorrow!", Toast.LENGTH_SHORT).show()
        }

        findViewById<android.view.View>(R.id.btnBack).setOnClickListener {
            finish()
        }
    }

    private fun showInfoDialog(header: String, info: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle(header)
            .setMessage(info)
            .setPositiveButton("Acknowledged", null)
            .show()
    }
}