package com.example.to_dolist

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.ai.client.generativeai.GenerativeModel
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class AskAIActivity : AppCompatActivity() {

    private val apiKey = "AIzaSyD7kQef4H7_wZVKFLudQLl_UWFcLQZdf-Q"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ask_ai)

        val etQuestion = findViewById<EditText>(R.id.etQuestion)
        val btnAsk = findViewById<MaterialButton>(R.id.btnAsk)
        val tvResponse = findViewById<TextView>(R.id.tvResponse)
        val pbLoading = findViewById<ProgressBar>(R.id.pbLoading)
        val emptyState = findViewById<LinearLayout>(R.id.emptyState)
        val responseCard = findViewById<MaterialCardView>(R.id.responseCard)

        // Using gemini-1.5-flash for professional output
        val generativeModel = GenerativeModel(
            modelName = "gemini-1.5-flash",
            apiKey = apiKey
        )
//        coment

        btnAsk.setOnClickListener {
            val query = etQuestion.text.toString().trim()
            if (query.isNotEmpty()) {
                
                // Switch UI from Empty to Chatting
                emptyState.visibility = View.GONE
                responseCard.visibility = View.VISIBLE
                pbLoading.visibility = View.VISIBLE
                tvResponse.text = "Consulting AI partner..."
                etQuestion.text.clear()

                MainScope().launch {
                    try {
                        val response = generativeModel.generateContent(query)
                        val responseText = response.text
                        
                        if (!responseText.isNullOrEmpty()) {
                            tvResponse.text = responseText
                        } else {
                            tvResponse.text = "AI reached a limit. Try another query."
                        }
                    } catch (e: Exception) {
                        Log.e("AskAIError", "Error: ${e.message}")
                        tvResponse.text = "Session interrupted. Check internet or try later."
                    } finally {
                        pbLoading.visibility = View.GONE
                    }
                }
            } else {
                Toast.makeText(this, "Your objective is required first.", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<View>(R.id.btnBack).setOnClickListener {
            finish()
        }
    }
}