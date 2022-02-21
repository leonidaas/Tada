package de.leonfuessner.tada.model

data class Task(
    val id: String,
    val categoryId: String,
    val text: String,
    val isDone: Boolean
)