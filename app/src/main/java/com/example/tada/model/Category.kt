package com.example.tada.model

data class Category(
    val id: Long,
    val title: String,
    val tasks: List<Task>
)