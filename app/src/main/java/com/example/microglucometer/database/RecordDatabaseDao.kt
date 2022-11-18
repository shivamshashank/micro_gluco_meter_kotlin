package com.example.microglucometer.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RecordDatabaseDao {
    @Query("SELECT * from record_list")
    fun getAll(): LiveData<List<Record>>

    @Insert
    suspend fun insert(item: Record)
}
