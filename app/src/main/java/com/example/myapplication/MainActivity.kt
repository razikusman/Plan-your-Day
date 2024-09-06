package com.example.myapplication

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.myapplication.ui.theme.MyApplicationTheme

data class Message(val author: String, val body: String)
//val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : ComponentActivity() {
    var msg = Message(author = "Usman", body = "My test Body")

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                var showSplash by remember { mutableStateOf(true) }

                if (showSplash) {
                    SplashScreen(onSplashFinished = {
                        showSplash = false
                    })
                } else {
//                    Scaffold(
//                        modifier = Modifier.fillMaxSize(),
//                        containerColor = Color.White, // Set background color here
//                        content = { innerPadding ->
//                            // Use Padding to make sure content is properly aligned
//                            Greeting(msg, Modifier.padding(innerPadding))
//                        }
//                    )
                    SideNav()
                }
            }
        }
    }
}

@Composable
fun DashBoard(message: Message, modifier: Modifier = Modifier) {
    // Define the handleButtonClick function
    fun handleButtonClick() {
        // Handle the button click here
        println("Button clicked!")
    }

    // Use Column to arrange items vertically
    Column(
        modifier = modifier
            .fillMaxSize() // Fill the entire screen
            .padding(16.dp), // Add padding around the Column
        horizontalAlignment = Alignment.CenterHorizontally, // Center items horizontally
        verticalArrangement = Arrangement.Top // Start alignment from the top
    ) {
        // Logo Image
        Image(
            painter = painterResource(id = R.drawable.logo_inside),
            contentDescription = "Contact profile picture",
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
                .padding(top = 32.dp), // Add top padding to push logo down
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(24.dp)) // Add space between the image and button

        // Column to hold the buttons (Boxes)
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(), // Use fillMaxHeight and fillMaxWidth to ensure full space usage
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Button 1
            DKButton(onClick = { handleButtonClick() }, Modifier.weight(1f), "Budget")

            // Spacer to add a bit of space between the two buttons
            Spacer(modifier = Modifier.height(16.dp))

            // Button 2
            DKButton(onClick = { handleButtonClick() }, Modifier.weight(1f), "Test")
        }
    }
}

@Composable
fun DKButton(onClick: () -> Unit, modifier: Modifier, name: String) {
    Box(
        modifier = modifier
            .fillMaxWidth() // Take the full width of the parent
            .background(MaterialTheme.colorScheme.primary) // Set background color
            .clickable { onClick() }, // Handle the click
        contentAlignment = Alignment.Center // Center the content inside the box
    ) {
        Text(
            text = name,
            color = Color.White,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        var msg = Message(author = "Usman", body = "My test Body")

        DashBoard(msg)
    }
}
