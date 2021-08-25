package com.example.tada.data.room

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

interface RoomBaseDao<in T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(t: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(ts: List<T>)

    @Update
    fun update(t: T)

    @Delete
    fun delete(t: T)
}