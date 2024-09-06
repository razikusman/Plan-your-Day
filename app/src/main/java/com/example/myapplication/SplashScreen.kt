package com.example.myapplication

import android.content.IntentSender.OnFinished
import android.window.SplashScreen
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Delay
import kotlinx.coroutines.delay

@Composable()
fun SplashScreen(onSplashFinished: () -> Unit){
    LaunchedEffect(Unit) {
        delay(3000)
        onSplashFinished()
    }

    Surface(
        modifier = Modifier.fillMaxSize()
            .background(Color.LightGray),
        color = Color.White,
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize(),
        ) {
            // Replace logo_inside with your drawable name
            Image(
                painter = painterResource(id = R.drawable.logo_face),
                contentDescription = "App Logo",
                modifier = Modifier.size(300.dp),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {

        SplashScreen(onSplashFinished = {})

}