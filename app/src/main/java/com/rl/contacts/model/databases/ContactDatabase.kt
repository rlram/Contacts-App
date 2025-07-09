package com.rl.contacts.model.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.rl.contacts.model.entities.Contact

@Database(entities = [Contact::class], version = 1)
abstract class ContactDatabase: RoomDatabase() {

    abstract fun contactDao(): ContactDao

    companion object {
        @Volatile
        private var instance: ContactDatabase? = null

        fun getDatabase(context: Context): ContactDatabase {
            return instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    ContactDatabase::class.java,
                    "contacts_db"
                ).build().also { instance = it }
            }
        }
    }
}