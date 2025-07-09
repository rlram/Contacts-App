package com.rl.contacts.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.rl.contacts.model.entities.Contact
import com.rl.contacts.model.repositories.ContactRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ContactViewModel(private val contactRepository: ContactRepository): ViewModel() {

    private val _contactState = MutableStateFlow<List<Contact>>(emptyList())
    val contactState: StateFlow<List<Contact>> = _contactState

    init {
        viewModelScope.launch {
            contactRepository.contacts.collect {
                _contactState.value = it
            }
        }
    }

    fun insert(contact: Contact) {
        viewModelScope.launch {
            contactRepository.insert(contact)
        }
    }

    fun update(contact: Contact) {
        viewModelScope.launch {
            contactRepository.update(contact)
        }
    }

    fun delete(contact: Contact) {
        viewModelScope.launch {
            contactRepository.delete(contact)
        }
    }
}

class ContactViewModelFactory(private val contactRepository: ContactRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ContactViewModel(contactRepository) as T
    }
}