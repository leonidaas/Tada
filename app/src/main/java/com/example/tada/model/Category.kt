package com.example.tada.model

data class Category(
    val id: String,
    val imageId: Int,
    val title: String,
    val tasks: List<Task>
)