package com.example.to_dolist

data class Transaction(
    val id: Int,
    val title: String,
    val amount: Double,
    val type: String // "Income" or "Expense"
)