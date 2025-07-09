package com.rl.contacts.model.repositories

import com.rl.contacts.model.databases.ContactDao
import com.rl.contacts.model.entities.Contact
import kotlinx.coroutines.flow.Flow

class ContactRepository(private val contactDao: ContactDao) {

    val contacts: Flow<List<Contact>> = contactDao.getAllContacts()

    suspend fun insert(contact: Contact) {
        contactDao.insert(contact)
    }

    suspend fun update(contact: Contact) {
        contactDao.update(contact)
    }

    suspend fun delete(contact: Contact) {
        contactDao.delete(contact)
    }
}