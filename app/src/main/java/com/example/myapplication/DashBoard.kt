package com.example.myapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

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
