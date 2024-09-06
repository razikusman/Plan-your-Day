package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.serialization.Serializable

val Context.dataStore by preferencesDataStore(name = "myCalander")

@SuppressLint("RememberReturnType")
@Composable
fun MyCalendar(
    date : String,
    ENTRIES_KEY: Preferences.Key<String>,
    dataStore: DataStore<Preferences>
) {
//    val context = LocalContext.current
//    val dataStore = remember { context.dataStore }
//    var entries by remember { mutableStateOf(listOf<Entry>()) }

    var calenderViewModel = MyCalenderViewModel(ds = dataStore);

    var title by remember { mutableStateOf(TextFieldValue("")) }
    var description by remember { mutableStateOf(TextFieldValue("")) }
    var number by remember { mutableStateOf(TextFieldValue("")) }
    var editIndex by remember { mutableIntStateOf(-1) }
    var total by remember { mutableIntStateOf(0) }


    val entries by calenderViewModel.loadEntry(ENTRIES_KEY).collectAsState(initial = null);
    var allEntries = entries

    if (allEntries != null) {
        total = allEntries.filter { e -> e.date == date }.sumOf { e -> e.number.toInt() }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = "Add Your Expense",
            style = MaterialTheme.typography.titleLarge,
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth(),
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = number,
            onValueChange = { number = it },
            label = { Text("Number") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick =
                {
                    if (editIndex >= 0) {
                        val updatedEntries = allEntries?.toMutableList()
                        updatedEntries?.set(
                            editIndex,
                            Entry(title.text, description.text, number.text, date)
                        )
                        allEntries = updatedEntries
                        editIndex = -1
                    } else {

                        if(allEntries !=null) {
                            var newENtry = Entry(title.text,description.text,number.text, date)
                            allEntries = allEntries!!.plus(newENtry);
                        }
                        else{
                            allEntries = listOf(Entry(title.text,description.text,number.text, date))
                        }
//                        total += number.text.toIntOrNull() ?: 0 bringme
                    }

                    title = TextFieldValue("")
                    description = TextFieldValue("")
                    number = TextFieldValue("")

                    calenderViewModel.saveEntry(allEntries!!, ENTRIES_KEY)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                Text(text = if (editIndex >= 0) "Update Entry" else "Add Entry")

            }

            Spacer(modifier = Modifier.weight(0.2f))

            VerticalDivider(
                color = Color.Black,
                thickness = 2.dp,
                modifier = Modifier.height(40.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp) // Space between $ and total value
            ) {
                Text(
                    text = "$",
                    style = MaterialTheme.typography.titleLarge,
                    color = Color.Black
                )

                Text(
                    text = "$total /=",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        HorizontalDivider(
            color = MaterialTheme.colorScheme.primary,
            thickness = 2.dp,
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .align(Alignment.CenterHorizontally)
        )

        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            itemsIndexed(allEntries ?: emptyList()) { index, entry ->
                if(entry.date == date) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 16.dp), // Space to the end for alignment
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            // Column for Edit and Delete buttons
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp) // Space between icons
                            ) {
                                IconButton(onClick = {
                                    // Edit the selected entry
                                    title = TextFieldValue(entry.title)
                                    description = TextFieldValue(entry.description)
                                    number = TextFieldValue(entry.number)
                                    editIndex = index
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Edit,
                                        contentDescription = "Edit Expense",
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                }

                                IconButton(onClick = {
                                    // Delete the selected entry
                                    val updatedEntries = allEntries?.toMutableList()
                                    updatedEntries?.removeAt(index)
                                    allEntries = updatedEntries
                                    calenderViewModel.saveEntry(allEntries!!, ENTRIES_KEY)
                                }) {
                                    Icon(
                                        imageVector = Icons.Outlined.Delete,
                                        contentDescription = "Delete Expense",
                                        tint = MaterialTheme.colorScheme.error
                                    )
                                }
                            }

                            // Column for Title and Amount
                            Row(
                                modifier = Modifier.weight(1f) // Take up remaining space
                            ) {
                                Column(
                                    modifier = Modifier
                                        .weight(1f) // Take up remaining space
                                        .padding(start = 16.dp) // Space between icons and text
                                ) {
                                    Text(
                                        text = entry.title,
                                        style = MaterialTheme.typography.bodyLarge,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    Text(
                                        text = entry.description,
                                        style = MaterialTheme.typography.bodySmall,
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                }

                                Text(
                                    text = "${entry.number} /=",
                                    style = MaterialTheme.typography.bodyLarge,
                                    modifier = Modifier
                                        .align(Alignment.CenterVertically)
                                        .padding(start = 8.dp) // Space between title and amount
                                )
                            }
                        }

                        // Divider
                        HorizontalDivider(
                            color = Color.LightGray,
                            thickness = 2.dp,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp) // Padding around divider
                        )
                    }
                }
            }
        }
    }
}



@Serializable
data class Entry(val title: String, val description: String, val number: String, val date: String)

