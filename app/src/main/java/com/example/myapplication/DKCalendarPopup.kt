import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.myapplication.MyCalendarForm
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

//
@Composable
fun DKCalendarPopup(
    month: String,
    year : String = "2024",
    dataStore: DataStore<Preferences>,
    ENTRIES_KEY: Preferences.Key<String>,
    date: String,
    onClose: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        shape = MaterialTheme.shapes.medium
    ) {
        IconButton(
            onClick = onClose
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = "View this month expenses"
            )
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(3.dp)
                .background(Color.White),
        ) {
            IconButton(
                onClick = onClose
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "close Calendar"
                )
            }

            MyCalendarForm(
                month,
                year,
                ENTRIES_KEY,
                dataStore,
                date)
        }
    }
}

fun parseDate(dateString: String, format: String = "dd/MM/yyyy"): Date {
    val dateFormat = SimpleDateFormat(format, Locale.getDefault())
    return dateFormat.parse(dateString) ?: Date() // Return current date if parsing fails
}

fun dkDefaultDateFormat(dateString: String, inputFormat: String, outputFormat: String = "dd/MM/yyyy"): String {
    val dateFormat = SimpleDateFormat(inputFormat, Locale.getDefault())
    val outputDateFormat = SimpleDateFormat(outputFormat, Locale.getDefault())

    val date = dateFormat.parse(dateString) ?: return ""
    return outputDateFormat.format(date)
}

fun getMonthInName(date: Date): String {
    val monthName = SimpleDateFormat("MMMM", Locale.getDefault()).format(date)
    return monthName
}

fun getYear(date: Date): String {
    val year = SimpleDateFormat("YYYY", Locale.getDefault()).format(date)
    return year
}

fun getDateFromMonth(date: String): String {
    val date = SimpleDateFormat("dd", Locale.getDefault()).format(parseDate(date))

    return date
}

fun getDayFromMonth(date: String, format: String): String {
    val day = SimpleDateFormat(format, Locale.getDefault()).format(Date(date))
    return day
}


fun getMonthInteger(monthName: String) : Int{
    val monthMap = DateFormatSymbols().months
    val monthNameLower = monthName.lowercase(Locale.ROOT)
    return monthMap.indexOfFirst { it.lowercase(Locale.ROOT) == monthNameLower }
}

fun getYearInInt(year : String) : Int{
    return year.toInt();
}

fun formatDate(date: Date, format: String): String {
    val dateFormat = SimpleDateFormat(format, Locale.getDefault())
    return dateFormat.format(date)
}