package com.allcarsinone.allcarsinone.models.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vehicles")
data class Vehicles(@PrimaryKey(autoGenerate = true) val id: Int = 0,
                    @ColumnInfo(name = "standid") val standid: Int = 0,
                    @ColumnInfo(name = "model") val model: String = "",
                    @ColumnInfo(name = "year") val year: Int = 0,
                    @ColumnInfo(name = "mileage") val mileage: Double = 0.0,
                    @ColumnInfo(name = "price") val price: Double = 0.0,
                    @ColumnInfo(name = "availability") val availability: Boolean = false,
                    @ColumnInfo(name = "description") val description: String = "",
                    @ColumnInfo(name = "gastypeid") val gastypeid: Int = 0,   // REFERENCES gastypes(id)
                    @ColumnInfo(name = "brandid") val brandid: Int = 0,       // REFERENCES brands(id)
)