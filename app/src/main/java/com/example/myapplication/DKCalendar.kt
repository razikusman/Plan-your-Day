
package com.example.myapplication

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.util.LocalePreferences.CalendarType
import getMonthInName
import java.time.Month
import java.util.Calendar
import java.util.Date

@Composable
fun DKCalendar(
    month : Int,
    year : Int
) {

    var nextMonth = Date(year, month+1,1)
    var previousMonth = Date(year, month-1,1)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        var date = Date(year,month,1)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.5f)
        ) {
            Text(
                text = "${getMonthInName(date)} of $year",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(Alignment.CenterVertically),
                textAlign = TextAlign.Center
            )
        }

        val daysOfWeek = listOf("Su", "Mo", "Tu", "We", "Th", "Fr", "Sa")

        for (day in 0..6) { // Adjust the range to match the list indices
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            ) {
                // Display the two-letter abbreviation for each day
                Text(daysOfWeek[day])
            }
        }

        for (week in 0 until 5) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                for (day in 1..7) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                    ) {
                        Text("${day + (week * 7)}")
                    }
                }
            }
        }
    }

//        var date = Date(year,month,i)
//        if(date.after(nextMonth)){
//            println("available")
//        }
//        else{
//            println("available")
//        }
//
//        if(date.after(previousMonth)){
//            println("available")
//        }
//    }
//    var calendar = Calendar.getInstance()
//    calendar.set(year,month,0);
//    calendar.get()
//    Row {
//        var dayStart = 1
//        for (dayStart)
//    }

}

fun getTheDatesOfMonth(
    month : Int,
    year : Int
){
    var firstDate = 1;
    var lastDate = Date(year,month,1)
//    lastDate.
}

@Preview
@Composable
fun testCalendar(){
    DKCalendar(1,2024);
}