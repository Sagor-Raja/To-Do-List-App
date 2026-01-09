package com.example.to_dolist

data class Task(
    val id: Int,
    var title: String,
    var isDone: Boolean = false,
    var isCancelled: Boolean = false
)