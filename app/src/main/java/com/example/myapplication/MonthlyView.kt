package com.example.myapplication

import DKCalendarPopup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.Month
import java.util.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.Dialog
import androidx.datastore.preferences.core.stringPreferencesKey
import getMonthInName
import getMonthInteger
import getYear
import getYearInInt
import parseDate

@Composable()
fun MonthView() {
    val date = Date()

    LazyColumn(
        modifier = Modifier
            .padding(20.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        item {
            Text(text = getYear(date))
        }

        items(Month.entries) { month ->
            ExpandablePanel(month.name)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }

}

@Composable
fun ExpandablePanel(
    month: String,
    year : String = "2024"
){
    var isExpanded by remember { mutableStateOf(false) }

    Card {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = month, style = MaterialTheme.typography.titleMedium)
                IconButton(onClick = { isExpanded = !isExpanded }) {
                    Icon(
                        imageVector = if (isExpanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
                        contentDescription = if (isExpanded) "Collapse" else "Expand"
                    )
                }
            }

            if (isExpanded) {
                val ENTRIES_KEY = stringPreferencesKey("entries")
                val context = LocalContext.current
                val dataStore = remember { context.dataStore}
                var calenderViewModel = MyCalenderViewModel(ds = dataStore);

                val entries by calenderViewModel.loadEntry(ENTRIES_KEY).collectAsState(initial = null);
                var currentMonthEntries = entries?.filter { entry ->
                    val entryDate = parseDate(entry.date);
                    getMonthInName(entryDate).equals(month, ignoreCase = true) && getYear(entryDate) == year
                }

                if (currentMonthEntries != null) {

                    val total = currentMonthEntries.sumOf { e -> e.number.toInt() }

                    Text(
                        text = "Expense for this month = ${total}/=",
                        style = MaterialTheme.typography.titleMedium,
                    );
                }
                else{
                    Text(
                        text = "Expense for this month = 0/=",
                        style = MaterialTheme.typography.titleMedium,
                    );
                };

                    var showDialog by remember { mutableStateOf(false) }
                    Column {
                        IconButton(onClick = { showDialog = true }) {
                            Icon(
                                imageVector = Icons.Filled.AddCircle,
                                contentDescription = "Open Form"
                            )
                        }
                    }

                    if (showDialog) {
                        Dialog(onDismissRequest = { showDialog = false }) {

                            DKCalendarPopup(
                                month,
                                year,
                                dataStore,
                                ENTRIES_KEY,
                                onClose = { showDialog = false })
                        }
                    }

            }
        }
    }
}


@Preview
@Composable
fun View(){
    MonthView()
}