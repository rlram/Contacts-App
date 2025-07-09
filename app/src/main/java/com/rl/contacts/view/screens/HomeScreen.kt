package com.rl.contacts.view.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rl.contacts.model.entities.Contact
import com.rl.contacts.viewmodel.ContactViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(contactViewModel: ContactViewModel) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var editContact by remember { mutableStateOf<Contact?>(null) }
    var showSheet by remember { mutableStateOf(false) }

    var name by remember { mutableStateOf("") }
    var phone by remember { mutableStateOf("") }

    fun openSheet(contact: Contact?) {
        editContact = contact
        if (contact != null) {
            name = contact.name
            phone = contact.phone
        } else {
            name = ""
            phone = ""
        }
        showSheet = true
    }

    if (showSheet) {
        ModalBottomSheet(
            onDismissRequest = {
                showSheet = false
            },
            sheetState = sheetState
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {

                Text(
                    text = if (editContact == null) "Add Contact" else "Update Contact",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = name,
                    onValueChange = {
                        name = it
                    },
                    label = { Text(text = "Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))


                OutlinedTextField(
                    value = phone,
                    onValueChange = {
                        phone = it
                    },
                    label = { Text(text = "Phone") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        if (name.isNotEmpty() && phone.isNotEmpty()) {
                            if (editContact == null) {
                                val contact = Contact(name = name, phone = phone)
                                contactViewModel.insert(contact)
                            } else {
                                val contact = Contact(id = editContact!!.id, name = name, phone = phone)
                                contactViewModel.update(contact)
                            }
                            showSheet = false
                        }
                    },
                    modifier = Modifier.fillMaxWidth()

                ) {
                    Text(if (editContact == null) "Save" else "Update")
                }
            }
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Contacts") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    openSheet(null)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add")
            }
        }

    ) { innerPadding ->

        val contacts by contactViewModel.contactState.collectAsState()
        Column(
            modifier = Modifier.padding(innerPadding)
        ) {
            LazyColumn {
                items(contacts) { contact ->
                    ContactItem(contact = contact, onEditClick = {openSheet(contact)})
                }
            }
        }
    }
}



@Composable
fun ContactItem(contact: Contact, onEditClick: () -> Unit) {
    Column(
      modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 12.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 16.dp)
        ) {
            Column {
                Text(text = contact.name, style = MaterialTheme.typography.titleMedium)
                Text(text = contact.phone, style = MaterialTheme.typography.bodyMedium)
            }
            IconButton(
                onClick = onEditClick
            ) {
                Icon(
                    imageVector = Icons.Default.Edit,
                    contentDescription = "Edit"
                )
            }
        }
    }
}