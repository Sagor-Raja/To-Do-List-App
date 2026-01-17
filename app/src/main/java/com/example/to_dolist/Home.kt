package com.example.to_dolist

import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.card.MaterialCardView

class Home : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        // Card Click Listeners (Updated types to match XML FrameLayout)
        val cardTodo = findViewById<FrameLayout>(R.id.card_todo)
        val cardDailyPlanner = findViewById<FrameLayout>(R.id.card_daily_planner)
        val cardCalculator = findViewById<FrameLayout>(R.id.card_calculate_cost)
        val cardGoalProgress = findViewById<FrameLayout>(R.id.card_goal_progress)
        val cardIdea = findViewById<FrameLayout>(R.id.card_idea)
        val cardDocument = findViewById<FrameLayout>(R.id.card_document)
        val cardAskAi = findViewById<FrameLayout>(R.id.card_ask_ai)

        cardTodo.setOnClickListener {
            startActivity(Intent(this, ToDoActivity::class.java))
        }

        cardDailyPlanner.setOnClickListener {
            startActivity(Intent(this, DailyPlannerActivity::class.java))
        }

        cardCalculator.setOnClickListener {
            startActivity(Intent(this, CalculatorActivity::class.java))
        }

        cardGoalProgress.setOnClickListener {
            startActivity(Intent(this, GoalProgressActivity::class.java))
        }

        cardIdea.setOnClickListener {
            startActivity(Intent(this, IdeaActivity::class.java))
        }

        cardDocument.setOnClickListener {
            startActivity(Intent(this, DocumentActivity::class.java))
        }

        cardAskAi.setOnClickListener {
            startActivity(Intent(this, AskAIActivity::class.java))
        }

        // Menu Icon Click Listener
        val menuIcon = findViewById<ImageView>(R.id.menu_icon)
        menuIcon.setOnClickListener { view ->
            val popupMenu = PopupMenu(this, view)
            popupMenu.menuInflater.inflate(R.menu.profile_menu, popupMenu.menu)
            popupMenu.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId) {
                    R.id.menu_profile -> {
                        startActivity(Intent(this, ProfileActivity::class.java))
                        true
                    }
                    R.id.menu_settings -> {
                        startActivity(Intent(this, SettingsActivity::class.java))
                        true
                    }
                    else -> false
                }
            }
            popupMenu.show()
        }

        // Bottom Navigation Bar Click Listener
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> true
                R.id.nav_todo -> {
                    startActivity(Intent(this, ToDoActivity::class.java))
                    true
                }
                R.id.nav_daily -> {
                    startActivity(Intent(this, DailyPlannerActivity::class.java))
                    true
                }
                R.id.nav_calc -> {
                    startActivity(Intent(this, CalculatorActivity::class.java))
                    true
                }
                R.id.nav_idea -> {
                     startActivity(Intent(this, IdeaActivity::class.java))
                    true
                }
                else -> false
            }
        }
        bottomNav.menu.findItem(R.id.nav_home).isChecked = true
    }
}