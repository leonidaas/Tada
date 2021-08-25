package com.example.tada.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tada.data.room.models.RoomCategory
import com.example.tada.data.room.models.RoomTask

@Database(
    entities = [
        RoomCategory::class,
        RoomTask::class
    ],
    version = 1
)
abstract class TadaDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao

    abstract fun taskDao(): TaskDao

}