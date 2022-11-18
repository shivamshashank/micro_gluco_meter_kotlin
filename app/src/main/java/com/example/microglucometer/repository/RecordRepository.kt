package com.example.microglucometer.repository

import androidx.lifecycle.LiveData
import com.example.microglucometer.database.Record
import com.example.microglucometer.database.RecordDatabaseDao

class RecordRepository(private val recordDatabaseDao: RecordDatabaseDao) {

    val readAllData: LiveData<List<Record>> = recordDatabaseDao.getAll()

    suspend fun addRecord(record: Record) {
        recordDatabaseDao.insert(record)
    }
}
