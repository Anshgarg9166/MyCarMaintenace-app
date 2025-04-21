package com.pkndegwa.mycarmaintenance.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.pkndegwa.mycarmaintenance.models.Reminder
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertReminder(reminder: Reminder)

    @Update
    fun updateReminder(reminder: Reminder)

    @Delete
    fun deleteReminder(reminder: Reminder)

    @Query("SELECT * FROM reminders WHERE id = :id")
    fun getReminder(id: Int): Flow<Reminder>

    @Query("SELECT * FROM reminders ORDER BY reminder_date ASC")
    fun getAllReminders(): LiveData<List<Reminder>>
}