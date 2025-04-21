package com.pkndegwa.mycarmaintenance.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.pkndegwa.mycarmaintenance.models.Vehicle

@Dao
interface VehicleDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(vehicle: Vehicle): Long

    @Update
    suspend fun update(vehicle: Vehicle): Int

    @Delete
    suspend fun delete(vehicle: Vehicle): Int

    @Query("SELECT * FROM vehicles WHERE id = :id")
    fun getVehicle(id: Int): LiveData<Vehicle?>

    @Query("SELECT * FROM vehicles ORDER BY manufacturer ASC")
    fun getVehicles(): LiveData<List<Vehicle>>
}