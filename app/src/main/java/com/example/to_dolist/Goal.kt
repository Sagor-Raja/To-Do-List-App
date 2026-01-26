package com.example.to_dolist

data class Goal(
    val title: String,
    val time: String,
    val yearly: String,
    val monthly: String,
    val weekly: String,
    val today: String,
    val category: String,
    val progress: Int
)