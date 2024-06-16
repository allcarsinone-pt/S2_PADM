package com.allcarsinone.allcarsinone.models.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.allcarsinone.allcarsinone.models.Vehicle

@Entity(tableName = "vehicles")
data class VehiclesModel(@PrimaryKey(autoGenerate = true) val id: Int,
                         @ColumnInfo(name = "standid") val standid: Int = 0,
                         @ColumnInfo(name = "model") val model: String = "",
                         @ColumnInfo(name = "year") val year: Int = 0,
                         @ColumnInfo(name = "mileage") val mileage: Double = 0.0,
                         @ColumnInfo(name = "price") val price: Double = 0.0,
                         @ColumnInfo(name = "availability") val availability: Boolean = false,
                         @ColumnInfo(name = "description") val description: String = "",
                         @ColumnInfo(name = "location") val location: String,
                         @ColumnInfo(name = "consume") val consume: Double
) {
    constructor(vehicle: Vehicle):this(0, 1, vehicle.model, vehicle.year.toInt(), vehicle.mileage.toDouble(), vehicle.price.toDouble(), vehicle.availability, vehicle.description, vehicle.location, vehicle.consume.toDouble())
}