package com.allcarsinone.allcarsinone.interfaces

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.allcarsinone.allcarsinone.models.room.Vehicles

@Dao
interface VehiclesDao {

    @Query("SELECT * FROM vehicles")
    fun getAll(): List<Vehicles>

    @Query("SELECT * FROM vehicles WHERE id = :id")
    fun findByID(id: Int)

    @Insert
    fun insert(vehicle: Vehicles)

    @Update
    fun update(vehicle: Vehicles)

    @Delete
    @Query("DELETE FROM vehicles WHERE 1")
    fun delete(id: Int)
}