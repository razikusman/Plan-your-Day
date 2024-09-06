import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
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
import com.example.myapplication.MyCalendar
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

//
@Composable
fun DKCalendarPopup(
    date: String,
    dataStore: DataStore<Preferences>,
    ENTRIES_KEY: Preferences.Key<String>,
    onClose: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        shape = MaterialTheme.shapes.medium
    ) {
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

            MyCalendar(
                date,
                ENTRIES_KEY,
                dataStore)
        }
    }
}

fun parseDate(dateString: String, format: String = "dd/MM/yyyy"): Date {
    val dateFormat = SimpleDateFormat(format, Locale.getDefault())
    return dateFormat.parse(dateString) ?: Date() // Return current date if parsing fails
}

fun getMonthInName(date: Date): String {
    val monthName = SimpleDateFormat("MMMM", Locale.getDefault()).format(date)
    return monthName
}

fun getYear(date: Date): String {
    val year = SimpleDateFormat("YYYY", Locale.getDefault()).format(date)
    return year
}

fun getDayFromMonth(date: String): String {
    val year = SimpleDateFormat("DD", Locale.getDefault()).format(date)
    return year
}

fun getMonthInteger(monthName: String) : Int{
    val monthMap = DateFormatSymbols().months
    val monthNameLower = monthName.lowercase(Locale.ROOT)
    return monthMap.indexOfFirst { it.lowercase(Locale.ROOT) == monthNameLower }
}

fun getYearInInt(year : String) : Int{
    return year.toInt();
}