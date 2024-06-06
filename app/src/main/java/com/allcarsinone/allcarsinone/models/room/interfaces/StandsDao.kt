package com.allcarsinone.allcarsinone.models.room.interfaces

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.allcarsinone.allcarsinone.models.room.StandsModel

@Dao
interface StandsDao {
    @Query("SELECT * FROM stand")
    fun getAll(): List<StandsModel>

    @Query("SELECT * FROM stand WHERE standid = :id")
    fun findByID(id: Int)

    @Insert
    fun insert(stand: StandsModel)

    @Update
    fun update(stand: StandsModel)

    @Delete
    fun delete(id: Int)
}