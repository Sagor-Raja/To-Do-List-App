package com.example.to_dolist

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.ai.client.generativeai.GenerativeModel
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class AskAIActivity : AppCompatActivity() {

    // আপনার দেওয়া আসল API Key
    private val apiKey = "AIzaSyD7kQef4H7_wZVKFLudQLl_UWFcLQZdf-Q"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ask_ai)

        val etQuestion = findViewById<EditText>(R.id.etQuestion)
        val btnAsk = findViewById<MaterialButton>(R.id.btnAsk)
        val tvResponse = findViewById<TextView>(R.id.tvResponse)
        val pbLoading = findViewById<ProgressBar>(R.id.pbLoading)

        // মডেলের নাম পরিবর্তন করে 'gemini-pro' করা হলো (এটি বেশি স্ট্যাবল)
        val generativeModel = GenerativeModel(
            modelName = "gemini-pro",
            apiKey = apiKey
        )

        btnAsk.setOnClickListener {
            val query = etQuestion.text.toString().trim()
            if (query.isNotEmpty()) {
                
                pbLoading.visibility = View.VISIBLE
                tvResponse.text = "AI চিন্তা করছে..."
                etQuestion.text.clear()

                MainScope().launch {
                    try {
                        val response = generativeModel.generateContent(query)
                        val responseText = response.text
                        
                        if (!responseText.isNullOrEmpty()) {
                            tvResponse.text = responseText
                        } else {
                            tvResponse.text = "দুঃখিত, এআই কোনো উত্তর দিতে পারেনি।"
                        }
                    } catch (e: Exception) {
                        Log.e("GeminiError", "Error: ${e.message}")
                        val errorMessage = e.message ?: e.toString()
                        
                        // যদি 404 এরর আসে, তবে ইউজারকে জানানো
                        if (errorMessage.contains("404")) {
                            tvResponse.text = "ত্রুটি (404): এআই মডেলটি কাজ করছে না। দয়া করে আপনার এপিআই কি-টি গুগল এআই স্টুডিওতে সচল আছে কিনা চেক করুন।"
                        } else {
                            tvResponse.text = "একটি সমস্যা হয়েছে:\n$errorMessage\n\nদয়া করে নিশ্চিত করুন আপনার ফোনে ইন্টারনেট চালু আছে।"
                        }
                    } finally {
                        pbLoading.visibility = View.GONE
                    }
                }
            } else {
                Toast.makeText(this, "দয়া করে একটি প্রশ্ন লিখুন", Toast.LENGTH_SHORT).show()
            }
        }

        findViewById<View>(R.id.btnBack).setOnClickListener {
            finish()
        }
    }
}