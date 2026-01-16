package com.example.to_dolist

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.card.MaterialCardView

class DailyPlannerActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_daily_planner)

        // All Cards IDs from XML
        val cardDaily = findViewById<MaterialCardView>(R.id.cardDaily)
        val cardMorning = findViewById<MaterialCardView>(R.id.cardMorning)
        val cardAfternoon = findViewById<MaterialCardView>(R.id.cardAfternoon)
        val cardEvening = findViewById<MaterialCardView>(R.id.cardEvening)
        val cardNight = findViewById<MaterialCardView>(R.id.cardNight)
        val cardHealthy = findViewById<MaterialCardView>(R.id.cardHealthy)
        val cardWealth = findViewById<MaterialCardView>(R.id.cardWealth)
        val cardGym = findViewById<MaterialCardView>(R.id.cardGym)
        val cardStudy = findViewById<MaterialCardView>(R.id.cardStudy)
        val cardDiscipline = findViewById<MaterialCardView>(R.id.cardDiscipline)
        val cardCustomize = findViewById<MaterialCardView>(R.id.cardCustomize)
        val cardHabits = findViewById<MaterialCardView>(R.id.cardHabits)
        val cardMillionaire = findViewById<MaterialCardView>(R.id.cardMillionaire)
        val cardAdd = findViewById<MaterialCardView>(R.id.cardAdd)

        // Click Logic for all cards
        val cards = mapOf(
            cardDaily to "Daily Routine",
            cardMorning to "Morning Routine",
            cardAfternoon to "Afternoon Routine",
            cardEvening to "Evening Routine",
            cardNight to "Night Routine",
            cardHealthy to "Health & Fitness",
            cardWealth to "Wealth Management",
            cardGym to "Gym & Workout",
            cardStudy to "Study Plan",
            cardDiscipline to "Discipline Mode",
            cardCustomize to "Custom Settings",
            cardHabits to "Habit Tracker",
            cardMillionaire to "Millionaire Mindset",
            cardAdd to "Create New Plan"
        )

        for ((card, title) in cards) {
            card.setOnClickListener {
                val intent = Intent(this, RoutineDetailsActivity::class.java)
                intent.putExtra("ROUTINE_TITLE", title)
                startActivity(intent)
            }
        }
    }
}