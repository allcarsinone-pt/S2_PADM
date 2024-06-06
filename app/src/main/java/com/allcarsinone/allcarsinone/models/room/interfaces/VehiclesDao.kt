package com.allcarsinone.allcarsinone.models.room.interfaces

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.allcarsinone.allcarsinone.models.room.VehiclesModel

@Dao
interface VehiclesDao {

    @Query("SELECT * FROM vehicles")
    fun getAll(): List<VehiclesModel>

    @Query("SELECT * FROM vehicles WHERE id = :id")
    fun findByID(id: Int):VehiclesModel

    @Insert
    fun insert(vehicle: VehiclesModel)

    @Update
    fun update(vehicle: VehiclesModel)

    @Delete
    fun delete(vararg vehicle: VehiclesModel)
}