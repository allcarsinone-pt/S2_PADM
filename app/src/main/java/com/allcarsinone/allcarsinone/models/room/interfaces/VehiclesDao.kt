package com.allcarsinone.allcarsinone.models.room.interfaces

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
    fun findByID(id: Int):Vehicles

    @Insert
    fun insert(vehicle: Vehicles)

    @Update
    fun update(vehicle: Vehicles)

    @Delete
    fun delete(vararg vehicle: Vehicles)
}