package com.example.microglucometer.repository

import androidx.lifecycle.LiveData
import com.example.microglucometer.database.UserDetail
import com.example.microglucometer.database.UserDetailDatabaseDao

class UserDetailRepository(private val userDetailDatabaseDao: UserDetailDatabaseDao) {

    val readAllData: LiveData<List<UserDetail>> = userDetailDatabaseDao.getAll()

    suspend fun addUserDetail(userDetail: UserDetail) {
        userDetailDatabaseDao.insert(userDetail)
    }
}
