package com.rl.contacts.view.activities

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import com.rl.contacts.model.databases.ContactDatabase
import com.rl.contacts.model.repositories.ContactRepository
import com.rl.contacts.ui.theme.ContactsTheme
import com.rl.contacts.view.screens.HomeScreen
import com.rl.contacts.viewmodel.ContactViewModel
import com.rl.contacts.viewmodel.ContactViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val db = ContactDatabase.getDatabase(applicationContext)
        val contactRepository = ContactRepository(db.contactDao())
        val contactViewModelFactory = ContactViewModelFactory(contactRepository)
        val contactViewModel: ContactViewModel = ViewModelProvider(this, contactViewModelFactory)[ContactViewModel::class.java]
        setContent {
            ContactsTheme {
                HomeScreen(contactViewModel)
            }
        }
    }
}