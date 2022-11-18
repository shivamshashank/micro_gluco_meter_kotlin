package com.example.microglucometer.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "record_list")
data class Record(

    @PrimaryKey(autoGenerate = true)
    var userId: Long = 0L,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "gender")
    val gender: String,

    @ColumnInfo(name = "age")
    val age: String,

    @ColumnInfo(name = "phoneNumber")
    val phoneNumber: String,

    @ColumnInfo(name = "region_of_interest1")
    val region_of_interest1: String,

    @ColumnInfo(name = "concentration1")
    val concentration1: String,

    @ColumnInfo(name = "region_of_interest2")
    val region_of_interest2: String,

    @ColumnInfo(name = "concentration2")
    val concentration2: String,

    @ColumnInfo(name = "region_of_interest3")
    val region_of_interest3: String,

    @ColumnInfo(name = "concentration3")
    val concentration3: String,

    )