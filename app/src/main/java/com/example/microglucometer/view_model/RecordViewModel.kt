package com.example.microglucometer.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.microglucometer.database.Record
import com.example.microglucometer.database.RecordDatabase
import com.example.microglucometer.models.ConcentrationModel
import com.example.microglucometer.models.User
import com.example.microglucometer.repository.RecordRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecordViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData: LiveData<List<Record>>
    private val recordRepository: RecordRepository

    init {
        val recordDao = RecordDatabase.getInstance(application).recordDao()
        recordRepository = RecordRepository(recordDao)
        readAllData = recordRepository.readAllData
    }

    fun addRecord(user: User, concentrationModel: ConcentrationModel) {
        viewModelScope.launch(Dispatchers.IO) {
            val record: Record = Record(
                System.currentTimeMillis(),
                user.name,
                user.gender,
                user.age,
                user.phoneNumber,
                concentrationModel.region_of_interest1,
                concentrationModel.concentration1,
                concentrationModel.region_of_interest2,
                concentrationModel.concentration2,
                concentrationModel.region_of_interest3,
                concentrationModel.concentration3,
            )

            recordRepository.addRecord(record)
        }
    }

}
