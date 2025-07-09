package com.rl.contacts.model.databases

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.rl.contacts.model.entities.Contact
import kotlinx.coroutines.flow.StateFlow

@Dao
interface ContactDao {

    @Insert
    suspend fun insert(contact: Contact)

    @Update
    suspend fun update(contact: Contact)

    @Delete
    suspend fun delete(contact: Contact)

    @Query("SELECT * FROM contacts_table")
    fun getAllContacts(): StateFlow<List<Contact>>

}