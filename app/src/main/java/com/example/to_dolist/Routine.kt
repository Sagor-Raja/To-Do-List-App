package com.example.to_dolist

data class Routine(
    val time: String,
    val title: String,
    val subtitle: String,
    val iconRes: Int = android.R.drawable.ic_menu_day
)