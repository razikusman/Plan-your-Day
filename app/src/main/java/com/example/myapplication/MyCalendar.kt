package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Label
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
import formatDate
import getMonthInteger
import getYearInInt
import kotlinx.serialization.Serializable
import java.util.Date

val Context.dataStore by preferencesDataStore(name = "myCalander")

@SuppressLint("RememberReturnType")
@Composable
fun MyCalendar(
    month: String,
    year: String = "2024",
    ENTRIES_KEY: Preferences.Key<String>,
    dataStore: DataStore<Preferences>
) {

    var calenderViewModel = MyCalenderViewModel(ds = dataStore);

    var title by remember { mutableStateOf(TextFieldValue("")) }
    var description by remember { mutableStateOf(TextFieldValue("")) }
    var number by remember { mutableStateOf(TextFieldValue("")) }
    var editIndex by remember { mutableIntStateOf(-1) }
    var total by remember { mutableIntStateOf(0) }
    var selectedDate by remember { mutableStateOf("") }

    val entries by calenderViewModel.loadEntry(ENTRIES_KEY).collectAsState(initial = null);
    var allEntries = entries

    if (allEntries != null && selectedDate != "") {
        total = allEntries.filter { e -> e.date == selectedDate }.sumOf { e -> e.number.toInt() }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp) // Space between elements
    ) {
        val date : DKDatePickerResponse = DKDatePicker(
            getMonthInteger(month),
            getYearInInt(year)
        )
        selectedDate = date.date;
        Text(
            text = "Selected Date: $selectedDate",
            style = MaterialTheme.typography.bodyLarge,
        )

        // Title Text Field
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        )

        // Description Text Field
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Description") },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        )

        // Amount Text Field
        OutlinedTextField(
            value = number,
            onValueChange = { number = it },
            label = { Text("Amount") },
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
        )

        Column {
            if (selectedDate == "") {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Select a Date",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Red
                    )
                    Icon(
                        Icons.Outlined.Info,
                        contentDescription = "Info Icon",
                        tint = Color.Red
                    )
                }
            }

            // Row for Add/Edit Button and Total Display
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Add/Edit Button
                Button(
                    onClick =
                    {
                        if (editIndex >= 0) {
                            val updatedEntries = allEntries?.toMutableList()
                            updatedEntries?.set(
                                editIndex,
                                Entry(title.text, description.text, number.text, selectedDate)
                            )
                            allEntries = updatedEntries
                            editIndex = -1
                        } else {
                            if (allEntries != null) {
                                val newEntry =
                                    Entry(title.text, description.text, number.text, selectedDate)
                                allEntries = allEntries!!.plus(newEntry)
                            } else {
                                allEntries = listOf(
                                    Entry(
                                        title.text,
                                        description.text,
                                        number.text,
                                        selectedDate
                                    )
                                )
                            }
                        }

                        title = TextFieldValue("")
                        description = TextFieldValue("")
                        number = TextFieldValue("")

                        calenderViewModel.saveEntry(allEntries!!, ENTRIES_KEY)
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    ),
                    enabled = selectedDate.isNotEmpty()
                ) {
                    Text(text = if (editIndex >= 0) "Update Entry" else "Add Entry")
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Total Display
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
                if(entry.date == selectedDate) {
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
                                    selectedDate = entry.date
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

