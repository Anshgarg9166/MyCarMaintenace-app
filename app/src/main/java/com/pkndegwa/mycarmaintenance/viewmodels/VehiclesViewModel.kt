package com.pkndegwa.mycarmaintenance.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.pkndegwa.mycarmaintenance.database.VehicleDao
import com.pkndegwa.mycarmaintenance.models.Vehicle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * The [ViewModel] that is attached to the VehiclesFragment.
 */
class VehiclesViewModel(private val vehicleDao: VehicleDao) : ViewModel() {

    val allVehicles: LiveData<List<Vehicle>> = vehicleDao.getVehicles()

    /**
     * This function retrieves the vehicle details from the database based on the vehicle [id].
     * @return LiveData<Vehicle?>
     */
    fun retrieveVehicle(id: Int): LiveData<Vehicle?>? {
        return vehicleDao.getVehicle(id)
    }

    /**
     * Public function that takes in vehicle details, gets a new [Vehicle] instance,
     * and passes the information to [insertVehicle] to be saved to the database.
     */
    fun addNewVehicle(
        vehicleImageUri: String,
        vehicleType: String,
        vehicleManufacturer: String,
        vehicleModel: String,
        vehicleModelYear: String,
        vehicleLicensePlate: String,
        vehicleFuelType: String,
        vehicleMileage: String
    ) {
        val newVehicle = Vehicle(
            vehicleImageUri = vehicleImageUri,
            type = vehicleType,
            manufacturer = vehicleManufacturer,
            model = vehicleModel,
            modelYear = vehicleModelYear,
            licensePlate = vehicleLicensePlate,
            fuelType = vehicleFuelType,
            mileage = vehicleMileage.toIntOrNull() ?: 0
        )
        viewModelScope.launch(Dispatchers.IO) {
            try {
                vehicleDao.insert(newVehicle)
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    throw e
                }
            }
        }
    }

    /**
     * This function deletes a Vehicle object from the database on a background thread.
     */
    fun deleteVehicle(vehicle: Vehicle) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                vehicleDao.delete(vehicle)
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    throw e
                }
            }
        }
    }

    /**
     * This function takes in a [Vehicle] object and updates the data of the existing
     * vehicle in the database on a background thread.
     * @param [vehicle]
     * @return Boolean
     */
    fun updateVehicle(
        vehicleId: Int,
        vehicleImageUri: String,
        vehicleType: String,
        vehicleManufacturer: String,
        vehicleModel: String,
        vehicleModelYear: String,
        vehicleLicensePlate: String,
        vehicleFuelType: String,
        vehicleMileage: String
    ): Boolean {
        var result = false
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val updatedVehicle = Vehicle(
                    id = vehicleId,
                    vehicleImageUri = vehicleImageUri,
                    type = vehicleType,
                    manufacturer = vehicleManufacturer,
                    model = vehicleModel,
                    modelYear = vehicleModelYear,
                    licensePlate = vehicleLicensePlate,
                    fuelType = vehicleFuelType,
                    mileage = vehicleMileage.toIntOrNull() ?: 0
                )
                val rowsAffected = vehicleDao.update(updatedVehicle)
                result = rowsAffected > 0
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    throw e
                }
            }
        }
        return result
    }

    companion object {
        fun createFactory(vehicleDao: VehicleDao): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return VehiclesViewModel(vehicleDao) as T
                }
            }
        }
    }
}