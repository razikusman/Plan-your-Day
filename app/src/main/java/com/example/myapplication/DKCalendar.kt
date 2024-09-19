
package com.example.myapplication

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toLowerCase
import androidx.compose.ui.tooling.preview.Preview
import getDateFromMonth
import getMonthInName
import java.time.YearMonth
import java.util.Date
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DKCalendar(
    month : Int,
    year : Int
) {

    var currentMonth = YearMonth.of(year,month)
    var currentMonthLastDate = currentMonth.atEndOfMonth().dayOfMonth;
    var previousMonthLastDate = currentMonth.minusMonths(1).atEndOfMonth().dayOfMonth;
    var currentMonthBeginningDay = currentMonth.atDay(1).dayOfWeek;

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
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
                            Text("${count}")
                            count++
                        }
                    }
            };
//                for (day in 0..6) {
//                    Column(
//                        modifier = Modifier
//                            .fillMaxHeight()
//                            .weight(1f)
//                    ) {
//                        Text("${day + 1 + (week * 7)}")
//                    }
//                }
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

fun capitalizeString(word: String): String {
    // Split the string into words based on whitespace
    return word.lowercase(Locale.ROOT).replaceFirstChar{it.uppercase()}
}

@Preview
@Composable
fun testCalendar(){
    DKCalendar(2,2024);
}