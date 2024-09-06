import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import java.util.*

@Composable
fun DKDatePickerDialog(
    initialMonth: Int, // 0-based month index (0 for January, 1 for February, etc.)
    initialYear: Int,
    onDateSelected: (String) -> Unit,
    onDateCancelled: () -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance().apply {
        set(Calendar.MONTH, initialMonth)
        set(Calendar.YEAR, initialYear)
    }

    // Show only the days of the specified month
    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
            val selectedDate = "$dayOfMonth/${month + 1}/$year"
            onDateSelected(selectedDate)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    ).apply {
        // Customize the DatePickerDialog here
        setButton(DatePickerDialog.BUTTON_POSITIVE, "Select") { _, _ ->
            // Handle OK button click
        }
        setButton(DatePickerDialog.BUTTON_NEGATIVE, "Cancel") { _, _ ->
            onDateCancelled()
        }
    }

    // Use reflection or custom methods to hide month/year picker if needed (e.g., by customizing view directly)
    // Alternatively, use a third-party library for more control.

    // Show the dialog
    LaunchedEffect(Unit) {
        datePickerDialog.show()
    }
}
