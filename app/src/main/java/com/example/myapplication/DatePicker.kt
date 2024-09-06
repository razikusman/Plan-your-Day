package com.example.myapplication

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.*
import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

// Updated DKDatePickerResponse to take String
data class DKDatePickerResponse(val date: String, val isDateSelected: Boolean = false)

@Composable
fun DKDatePicker(
    month : Int,
    year : Int
): DKDatePickerResponse {
    var selectedDate by remember { mutableStateOf("") }
    var isDateSelected by remember { mutableStateOf(false) }

    val calendar = Calendar.getInstance().apply {
        set(Calendar.MONTH, month)
        set(Calendar.YEAR, year)
    }
    val year = calendar.get(Calendar.YEAR)
    val month = calendar.get(Calendar.MONTH)
    val day = calendar.get(Calendar.DAY_OF_MONTH)

    val datePickerDialog = DatePickerDialog(
        LocalContext.current,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            selectedDate = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
            isDateSelected = true
        }, year, month, day
    ).apply {
        setOnCancelListener {
            // This is called when the dialog is canceled (i.e., Cancel button is clicked or back pressed)
            isDateSelected = false
        }
    }

    IconButton(onClick = {
        datePickerDialog.show()
    }) {
        Icon(
            imageVector = Icons.Outlined.DateRange,
            contentDescription = "Pick a Date",
            tint = MaterialTheme.colorScheme.primary
        )
    }

    return DKDatePickerResponse(selectedDate, isDateSelected)
}

@Preview(showBackground = true)
@Composable
fun DatePickerDemoPreview() {
    val datePickerResponse = DKDatePicker(0,2024)

    // Check if a date was selected
    if (datePickerResponse.isDateSelected) {
        Text(text = "Selected Date: ${datePickerResponse.date}")
    } else {
        Text(text = "No Date Selected")
    }
}
