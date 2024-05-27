package com.allcarsinone.allcarsinone.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.allcarsinone.allcarsinone.interfaces.VehiclesDao
import com.allcarsinone.allcarsinone.models.room.Vehicles

@Database(entities = arrayOf(Vehicles::class), version = 1)
abstract class VehiclesDatabase: RoomDatabase() {
    abstract fun vehiclesDao(): VehiclesDao
    companion object {
        @Volatile private var instance: VehiclesDatabase? = null
        private val LOCK = Any()
        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }
        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            VehiclesDatabase::class.java, "vehicles.db")
            .build()
    }
}