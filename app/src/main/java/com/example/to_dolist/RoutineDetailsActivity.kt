package com.example.to_dolist

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.progressindicator.LinearProgressIndicator

class RoutineDetailsActivity : AppCompatActivity() {

    private lateinit var adapter: TaskAdapter
    private val taskList = mutableListOf<Task>()
    private var currentTitle: String = ""

    private lateinit var progressIndicator: LinearProgressIndicator
    private lateinit var tvProgressStatus: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_routine_details)

        currentTitle = intent.getStringExtra("ROUTINE_TITLE") ?: "Routine Details"
        findViewById<TextView>(R.id.tvRoutineDetailsTitle).text = currentTitle

        progressIndicator = findViewById(R.id.routineProgress)
        tvProgressStatus = findViewById(R.id.tvProgressStatus)

        val rvTasks = findViewById<RecyclerView>(R.id.rvRoutineTasks)
        val etNewTask = findViewById<EditText>(R.id.etNewRoutineTask)
        val btnAdd = findViewById<MaterialButton>(R.id.btnAddRoutineTask)
        val btnReset = findViewById<View>(R.id.btnReset)

        adapter = TaskAdapter(taskList,
            onStatusChanged = { updateProgress() },
            onDeleteClick = { position ->
                taskList.removeAt(position)
                adapter.notifyDataSetChanged()
                updateProgress()
            }
        )

        rvTasks.layoutManager = LinearLayoutManager(this)
        rvTasks.adapter = adapter

        loadDefaultTasks(currentTitle)
        updateProgress()

        btnReset.setOnClickListener {
            showResetDialog()
        }

        btnAdd.setOnClickListener {
            val taskText = etNewTask.text.toString().trim()
            if (taskText.isNotEmpty()) {
                taskList.add(Task(taskList.size + 1, taskText))
                adapter.notifyItemInserted(taskList.size - 1)
                etNewTask.text.clear()
                rvTasks.scrollToPosition(taskList.size - 1)
                updateProgress()
            }
        }
    }

    private fun updateProgress() {
        if (taskList.isEmpty()) {
            progressIndicator.progress = 0
            tvProgressStatus.text = "0% Completed"
            return
        }
        val completedTasks = taskList.count { it.isDone }
        val progress = (completedTasks * 100) / taskList.size
        progressIndicator.setProgress(progress, true)
        tvProgressStatus.text = "$progress% Completed"
    }

    private fun showResetDialog() {
        AlertDialog.Builder(this)
            .setTitle("Reset Routine?")
            .setMessage("Are you sure you want to revert to the professional master plan?")
            .setPositiveButton("Reset") { _, _ ->
                loadDefaultTasks(currentTitle)
                updateProgress()
                Toast.makeText(this, "Routine reset successful", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun loadDefaultTasks(title: String) {
        taskList.clear()
        when {
            title.contains("Full Day") || title.contains("Daily Routine") -> {
                taskList.add(Task(1, "05:30 AM - Wake up & Affirmations ✨"))
                taskList.add(Task(2, "05:45 AM - Hydrate (500ml Water) 💧"))
                taskList.add(Task(3, "06:00 AM - Deep Meditation (15 mins) 🧘"))
                taskList.add(Task(4, "06:30 AM - Morning Exercise/Yoga 🤸"))
                taskList.add(Task(5, "07:15 AM - Cold Shower & Grooming 🚿"))
                taskList.add(Task(6, "07:45 AM - High-Protein Breakfast 🍳"))
                taskList.add(Task(7, "08:30 AM - Deep Work Session 1 (90 mins) 💻"))
                taskList.add(Task(8, "10:30 AM - Deep Work Session 2 (90 mins) 🚀"))
                taskList.add(Task(9, "12:30 PM - Mindful Lunch (No Screens) 🍱"))
                taskList.add(Task(10, "01:30 PM - Power Nap (20 mins) 😴"))
                taskList.add(Task(11, "02:00 PM - Admin Tasks/Calls/Mails ✉️"))
                taskList.add(Task(12, "04:30 PM - Creative Work or Learning 🎨"))
                taskList.add(Task(13, "06:00 PM - Gym or Intense Workout 🏋️"))
                taskList.add(Task(14, "08:00 PM - Healthy Dinner 🥗"))
                taskList.add(Task(15, "09:30 PM - Plan for Tomorrow 📝"))
            }
            title.contains("Morning") -> {
                taskList.add(Task(1, "Rise before the Sun (5:30 AM) ☀️"))
                taskList.add(Task(2, "Immediate Hydration (Water) 💧"))
                taskList.add(Task(3, "Deep Breathwork (Box Breathing) 🌬️"))
                taskList.add(Task(4, "No Phone for first 60 minutes 📵"))
                taskList.add(Task(5, "Morning Prayer/Meditation 🙏"))
                taskList.add(Task(6, "Stretching for flexibility 🤸"))
                taskList.add(Task(7, "Morning Sunlight exposure 🌅"))
                taskList.add(Task(8, "Journaling (Gratitude & Intent) ✍️"))
                taskList.add(Task(9, "Review Top 3 Priorities 🎯"))
                taskList.add(Task(10, "Listen to Motivation/Growth Podcast 🎧"))
                taskList.add(Task(11, "Organize Workspace 🧹"))
                taskList.add(Task(12, "Read 10 pages of Non-fiction 📖"))
                taskList.add(Task(13, "Mental Readiness Drill 🧠"))
                taskList.add(Task(14, "Healthy Supplement intake 💊"))
                taskList.add(Task(15, "Ready for First Deep Session ✅"))
            }
            title.contains("Afternoon") -> {
                taskList.add(Task(1, "Mindful Balanced Lunch 🍱"))
                taskList.add(Task(2, "Post-Lunch Walk (10 mins) 🚶"))
                taskList.add(Task(3, "Hydration Refill (1 Liter) 💧"))
                taskList.add(Task(4, "Batch Process Emails/Mails ✉️"))
                taskList.add(Task(5, "Solve one complex problem 🧩"))
                taskList.add(Task(6, "Skill Practice Session 🧠"))
                taskList.add(Task(7, "Digital Minimalism Check 📵"))
                taskList.add(Task(8, "Posture Correction Stretching 🧍"))
                taskList.add(Task(9, "Eat Fruit or Healthy Nuts 🍎"))
                taskList.add(Task(10, "Update Progress Tracker 📊"))
                taskList.add(Task(11, "Review Performance so far 📈"))
                taskList.add(Task(12, "Strategic Thinking Session 💡"))
                taskList.add(Task(13, "Eye relaxation (20-20-20 rule) 👀"))
                taskList.add(Task(14, "Sync meeting/Project Review 🤝"))
                taskList.add(Task(15, "Finalize Afternoon work batch ✅"))
            }
            title.contains("Evening") -> {
                taskList.add(Task(1, "Work Wrap-up & Shutdown 🖥️"))
                taskList.add(Task(2, "Digital Detox begins 📵"))
                taskList.add(Task(3, "Physical Movement/Sports 🏀"))
                taskList.add(Task(4, "Outdoor Sunset View 🌅"))
                taskList.add(Task(5, "Family/Friend connection time ❤️"))
                taskList.add(Task(6, "Cook a healthy meal 🍳"))
                taskList.add(Task(7, "Water the plants 🪴"))
                taskList.add(Task(8, "Practice a Non-work Hobby 🎨"))
                taskList.add(Task(9, "Listen to soft Music/Relax 🎵"))
                taskList.add(Task(10, "Review Daily Expenses 💰"))
                taskList.add(Task(11, "Prepare Outfit for tomorrow 👔"))
                taskList.add(Task(12, "Tidy up Living Area 🏠"))
                taskList.add(Task(13, "Herbal Tea Relaxation ☕"))
                taskList.add(Task(14, "Body Massage/Foam Rolling 💆"))
                taskList.add(Task(15, "Self-reflection moment ✅"))
            }
            title.contains("Night") -> {
                taskList.add(Task(1, "No Screens (Blue Light Off) 📵"))
                taskList.add(Task(2, "Journal: Wins of the day ✅"))
                taskList.add(Task(3, "Plan 3 Major Tasks for tomorrow 🎯"))
                taskList.add(Task(4, "Practice Deep Gratitude 🙏"))
                taskList.add(Task(5, "Dental Hygiene & Flossing 🪥"))
                taskList.add(Task(6, "Skincare Routine 🧼"))
                taskList.add(Task(7, "Read 15 mins of Philosophy 📖"))
                taskList.add(Task(8, "Cool down Bedroom temperature ❄️"))
                taskList.add(Task(9, "Set 05:30 AM Alarm ⏰"))
                taskList.add(Task(10, "Deep Breathing Exercise 🌬️"))
                taskList.add(Task(11, "Prepare Water for morning 💧"))
                taskList.add(Task(12, "Visualize Long term vision 🏔️"))
                taskList.add(Task(13, "Sleep Posture alignment 🛌"))
                taskList.add(Task(14, "No Caffeine after 4 PM check 🚫"))
                taskList.add(Task(15, "Deep Sleep by 10:30 PM 😴"))
            }
            title.contains("Health") -> {
                taskList.add(Task(1, "Morning Cardio - 20 mins 🏃"))
                taskList.add(Task(2, "Drink 3.5 Liters Water 💧"))
                taskList.add(Task(3, "Zero Sugar intake 🚫"))
                taskList.add(Task(4, "Eat 5 servings of Veggies 🥗"))
                taskList.add(Task(5, "Daily Vitamin D (Sunlight) ☀️"))
                taskList.add(Task(6, "Intermittent Fasting (16:8) ⏳"))
                taskList.add(Task(7, "Limit Alcohol/Smoking 🚭"))
                taskList.add(Task(8, "Maintain Good Posture 🧍"))
                taskList.add(Task(9, "No Junk Food / Fast Food ❌"))
                taskList.add(Task(10, "Daily Meditation 🧘"))
                taskList.add(Task(11, "Restful 8-hour sleep 😴"))
                taskList.add(Task(12, "Gut Health (Probiotics) 🍎"))
                taskList.add(Task(13, "Mindful Eating (Chew well) 🍽️"))
                taskList.add(Task(14, "Body Weight Tracking ⚖️"))
                taskList.add(Task(15, "Eye Care exercises ✅"))
            }
            title.contains("Wealth") -> {
                taskList.add(Task(1, "Track every single expense 💰"))
                taskList.add(Task(2, "Save 20% of Daily Income 🏦"))
                taskList.add(Task(3, "Read 1 Financial News daily 📰"))
                taskList.add(Task(4, "Check Investment Portfolio 📈"))
                taskList.add(Task(5, "Identify 1 Passive Income source 💸"))
                taskList.add(Task(6, "Study Stock Market/Crypto 📊"))
                taskList.add(Task(7, "Avoid Impulse Buying 🛒"))
                taskList.add(Task(8, "Review Debt/Loans 💳"))
                taskList.add(Task(9, "Set Yearly Savings Target 🎯"))
                taskList.add(Task(10, "Learn about Tax saving 📝"))
                taskList.add(Task(11, "Study Compound Interest 🧪"))
                taskList.add(Task(12, "Analyze High Ticket Skills 🛠️"))
                taskList.add(Task(13, "Create Financial Freedom Plan 💎"))
                taskList.add(Task(14, "Network with Wealthy People 🤝"))
                taskList.add(Task(15, "Read Financial Education Books ✅"))
            }
            title.contains("Gym") -> {
                taskList.add(Task(1, "Proper Warm-up (10 mins) 🤸"))
                taskList.add(Task(2, "Pushups - 3 Sets of 20 ⚓"))
                taskList.add(Task(3, "Squats - 3 Sets of 20 🦵"))
                taskList.add(Task(4, "Plank - 2 Minutes 🔥"))
                taskList.add(Task(5, "Pull-ups - 3 Sets of 8 🏗️"))
                taskList.add(Task(6, "Dumbbell Press Session 🏋️"))
                taskList.add(Task(7, "HIIT Cardio - 15 mins 🏃"))
                taskList.add(Task(8, "Burpees - 25 Reps ⚡"))
                taskList.add(Task(9, "Deadlifts (Form check) 🦾"))
                taskList.add(Task(10, "Focus Muscle Mind connection 🧠"))
                taskList.add(Task(11, "Post-workout Protein 🥤"))
                taskList.add(Task(12, "Stretching & Cool down 🧘"))
                taskList.add(Task(13, "Log Weights used 📝"))
                taskList.add(Task(14, "Check Training Form 📹"))
                taskList.add(Task(15, "Rest & Recovery Day check ✅"))
            }
            title.contains("Study") -> {
                taskList.add(Task(1, "Set a Clear Study Goal 🎯"))
                taskList.add(Task(2, "Pomodoro (50m study/10m break) ⏳"))
                taskList.add(Task(3, "Hardest Subject First 🧠"))
                taskList.add(Task(4, "Active Recall (Test memory) 📝"))
                taskList.add(Task(5, "Spaced Repetition Review 🔄"))
                taskList.add(Task(6, "No multitasking (Full Focus) 📵"))
                taskList.add(Task(7, "Take handwritten summaries ✍️"))
                taskList.add(Task(8, "Use Mind Maps 🗺️"))
                taskList.add(Task(9, "Solve previous year questions 🧩"))
                taskList.add(Task(10, "Limit Study sessions to 4h ⏱️"))
                taskList.add(Task(11, "Pre-study Meditation 🧘"))
                taskList.add(Task(12, "Organize study materials 📂"))
                taskList.add(Task(13, "Teach the concept to others 🗣️"))
                taskList.add(Task(14, "Listen to Concentration music 🎵"))
                taskList.add(Task(15, "Review notes before sleep ✅"))
            }
            title.contains("Discipline") -> {
                taskList.add(Task(1, "5 AM Wake up Challenge 🧊"))
                taskList.add(Task(2, "No Social Media all day 📵"))
                taskList.add(Task(3, "Cold Shower daily 🚿"))
                taskList.add(Task(4, "Finish most difficult task first 🔨"))
                taskList.add(Task(5, "Fasting until Noon ⏳"))
                taskList.add(Task(6, "Keep environment 100% Tidy 🧹"))
                taskList.add(Task(7, "Zero Procrastination today ⏰"))
                taskList.add(Task(8, "Face one fear today 🦁"))
                taskList.add(Task(9, "Eat only unprocessed food 🥗"))
                taskList.add(Task(10, "Be early to every appointment ⌚"))
                taskList.add(Task(11, "No complaining for 24h 🙊"))
                taskList.add(Task(12, "Practice Saying NO 🚫"))
                taskList.add(Task(13, "Keep your promises (Integrity) 💎"))
                taskList.add(Task(14, "Push through discomfort 🦾"))
                taskList.add(Task(15, "Full focus on core objectives ✅"))
            }
                title.contains("Habit", ignoreCase = true) -> {
                taskList.add(Task(1, "Read 10 pages daily 📖"))
                taskList.add(Task(2, "Drink Water upon waking 💧"))
                taskList.add(Task(3, "Floss every night 🦷"))
                taskList.add(Task(4, "Daily Journaling ✍️"))
                taskList.add(Task(5, "Evening Gratitude list 🙏"))
                taskList.add(Task(6, "Walk 10,000 steps 🚶"))
                taskList.add(Task(7, "No sugar in coffee/tea ☕"))
                taskList.add(Task(8, "Clean desk after work 🧹"))
                taskList.add(Task(9, "Plan outfit night before 👔"))
                taskList.add(Task(10, "Learn 1 new word daily 🗣️"))
                taskList.add(Task(11, "Review Goals weekly 📅"))
                taskList.add(Task(12, "Budget check daily 💰"))
                taskList.add(Task(13, "Mindful Breathing (5m) 🌬️"))
                taskList.add(Task(14, "Say Thank You more often ❤️"))
                taskList.add(Task(15, "Consistent Sleep time 😴"))
            }
            title.contains("Success") || title.contains("Millionaire") -> {
                taskList.add(Task(1, "Think in Years, not days ⏳"))
                taskList.add(Task(2, "Invest 50% time in Learning 📚"))
                taskList.add(Task(3, "Focus on Value creation 💡"))
                taskList.add(Task(4, "Surround with Winners 💎"))
                taskList.add(Task(5, "Value Time more than Money ⌚"))
                taskList.add(Task(6, "Take Strategic Risks 🚀"))
                taskList.add(Task(7, "Multiple Income Streams 💸"))
                taskList.add(Task(8, "Be Resilient to failure 🦾"))
                taskList.add(Task(9, "Networking with Mentors 🤝"))
                taskList.add(Task(10, "Think Big, Start Small 🏔️"))
                taskList.add(Task(11, "Daily Visualization Session 🧠"))
                taskList.add(Task(12, "Execute > Planning ⚡"))
                taskList.add(Task(13, "Control your emotions 🧘"))
                taskList.add(Task(14, "Legacy over Lifestyle 🏆"))
                taskList.add(Task(15, "Solve Huge Problems ✅"))
            }
            else -> {
                taskList.add(Task(1, "Define clear objectives 🎯"))
                taskList.add(Task(2, "Setting milestones 📝"))
                taskList.add(Task(3, "Step-by-step Execution 🚀"))
            }
        }
        adapter.notifyDataSetChanged()
    }
}