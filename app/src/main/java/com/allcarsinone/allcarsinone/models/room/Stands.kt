package com.allcarsinone.allcarsinone.models.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stand")
data class Stands(@PrimaryKey(autoGenerate = true)
                  @ColumnInfo(name = "standid") val standid: Int = 0,
                  @ColumnInfo(name = "name") val name: String = "",
                  @ColumnInfo(name = "location") val location: String = "",
                  @ColumnInfo(name = "mobilephone") val mobilephone: String = "",
                  @ColumnInfo(name = "phone") val phone: String = "",
                  @ColumnInfo(name = "schedule") val schedule: String = "",
                  @ColumnInfo(name = "userid") val userid: Int = 0,
)

