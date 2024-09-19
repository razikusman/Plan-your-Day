
package com.example.myapplication

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.YearMonth
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DKCalendar(
    onDateSelected: (LocalDate) -> Unit,
    month : Int,
    year : Int,
    modifier: Modifier,
    calendarContent: MutableList<DKDateCalendarContent> = mutableListOf()
) {

    var currentMonth = YearMonth.of(year,month)
    var currentMonthLastDate = currentMonth.atEndOfMonth().dayOfMonth;
    var previousMonthLastDate = currentMonth.minusMonths(1).atEndOfMonth().dayOfMonth;
    var currentMonthBeginningDay = currentMonth.atDay(1).dayOfWeek;

    Column(
        modifier = modifier
    ) {
        var currentMonthStartingDay = capitalizeString(currentMonthBeginningDay.toString().take(2));

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.5f)
        ) {
            Text(
                text = "${currentMonth.month} of $year",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(Alignment.CenterVertically),
                textAlign = TextAlign.Center
            )
        }

        val daysOfWeek = listOf("Su", "Mo", "Tu", "We", "Th", "Fr", "Sa")
        var currentMonthStartingDayIndex = daysOfWeek.indexOf(currentMonthStartingDay)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.3f)
        ) {
            for (day in 0..6) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f)
                ) { Text(daysOfWeek[day]) }
            }
        }

        var count = 1;
        var nextMonthBegin = 0;
        for (week in 0 until 6) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                daysOfWeek.withIndex().forEach { (index, day) ->
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .weight(1f)
                    ) {
                        if(week == 0 && index < currentMonthStartingDayIndex) {
                            Text(
                                "${previousMonthLastDate - currentMonthStartingDayIndex + index + 1}",
                                color = Color.LightGray
                            )
                        }
                        else if(count > currentMonthLastDate){
                            nextMonthBegin++
                            Text(
                                "${nextMonthBegin}",
                                color = Color.LightGray
                            )
                        }
                        else{
                            val currentCount = count

                            Text(
                                text = "$currentCount",
                                modifier = Modifier
                                    .clickable {
                                        onDateSelected(currentMonth.atDay(currentCount))
                                    }
                            )
                            count++

                            var currentDateContent = calendarContent.find { content -> content.date == currentCount}
                            if(currentDateContent != null){
                                DisplayDataWithDividers(currentDateContent.dataList)
                            }
                        }
                    }
            };
            }
        }
    }
}

@Composable
fun DisplayDataWithDividers(dataList: List<String>) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
    ) {
        dataList.forEach { data ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    VerticalDivider()

                    Text(
                        text = data,
                        modifier = Modifier,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.labelMedium,
                        color = Color.Gray
                    )
                }
            }
        }
    }
}

@Composable
fun VerticalDivider() {
    Box(
        modifier = Modifier
            .width(1.5.dp)
            .background(Color.Blue)
            .height(10.dp)
    )
}

@Composable
fun Preview() {
    // Sample data
    val dataList = listOf("data1", "data2", "data3")

    // Call the composable with sample data
    DisplayDataWithDividers(dataList)
}


fun capitalizeString(word: String): String {
    // Split the string into words based on whitespace
    return word.lowercase(Locale.ROOT).replaceFirstChar{it.uppercase()}
}

@Preview
@Composable
fun testCalendar(){
    val sampleData1 = DKDateCalendarContent(
        date = 1,
        dataList = mutableListOf("Event 1", "Meeting with John")
    )

    val sampleData2 = DKDateCalendarContent(
        date = 15,
        dataList = mutableListOf("Birthday Party", "Project Deadline", "Dinner with team")
    )

    val sampleData3 = DKDateCalendarContent(
        date = 20,
        dataList = mutableListOf("Conference", "Dinner with team")
    )

    var calendarContent : MutableList<DKDateCalendarContent> = mutableListOf()
    calendarContent.add(sampleData1)
    calendarContent.add(sampleData2)
    calendarContent.add(sampleData3)

    var selectedDate by remember { mutableStateOf("") }

    Column (modifier = Modifier.fillMaxHeight()) {
        DKCalendar(
            onDateSelected = { date ->
                // Update the state when a date is selected
                selectedDate = date.toString()
            },
            month = 2,
            year = 2024,
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .weight(0.8f),
            calendarContent = calendarContent
        )

        // This Text will update whenever selectedDate changes
        Text(
            "selectedDate = ${selectedDate}",
            modifier = Modifier.weight(0.2f)
        )
    }
}

data class DKDateCalendarContent(val date: Int, val dataList: MutableList<String> = mutableListOf())
