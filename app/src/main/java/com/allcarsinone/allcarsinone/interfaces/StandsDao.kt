package com.allcarsinone.allcarsinone.interfaces

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.allcarsinone.allcarsinone.models.room.Stands

@Dao
interface StandsDao {
    @Query("SELECT * FROM stand")
    fun getAll(): List<Stands>

    @Query("SELECT * FROM stand WHERE standid = :id")
    fun findByID(id: Int)

    @Insert
    fun insert(stand: Stands)

    @Update
    fun update(stand: Stands)

    @Delete
    fun delete(id: Int)
}