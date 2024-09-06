package com.example.myapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch

@Composable
fun SideNav() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    var msg = Message(author = "Usman", body = "My test Body")

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent { selectedItem ->
                navController.navigate(selectedItem)
                scope.launch { drawerState.close() }
            }
        },
        content = {
            NavHost(
                navController = navController,
                startDestination = "home"
            ) {
                composable("home") {
//                    HomeScreen(
//                        drawerState = drawerState,
//                        onMenuClicked = {
//                            scope.launch {
//                                if (drawerState.isClosed) drawerState.open() else drawerState.close()
//                            }
//                        }
//                    )
                    Scaffold(
                        modifier = Modifier.fillMaxSize(),
                        containerColor = Color.White, // Set background color here
                        content = { innerPadding ->
                            // Use Padding to make sure content is properly aligned
                            DashBoard(msg, Modifier.padding(innerPadding))
                        }
                    )
                }
                composable("settings") {
                    SettingsPage()
                }
                composable("myCalendar"){
//                    val myCalendarViewModel: MyCalenderViewModel = viewModel();
                    MonthView()
                }
                // Add more composable routes here as needed
            }
        }
    )
}


@Composable
fun DrawerContent(onItemClicked: (String) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .width(300.dp)
            .background(Color.DarkGray),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Navigation Menu",
            modifier = Modifier
                .padding(16.dp),
            color = Color.White,
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
        Divider(color = Color.White)
        DrawerItem("Dashboard") { onItemClicked("Home") }
        DrawerItem("Profile") { onItemClicked("Profile") }
        DrawerItem("Settings") { onItemClicked("settings") }
        DrawerItem("My Calendar") { onItemClicked("myCalendar") }
    }
}

@Composable
fun DrawerItem(text: String, onClick: () -> Unit) {
    Text(
        text = text,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onClick() },
        color = Color.White,
        style = MaterialTheme.typography.bodyLarge
    )
}

@Composable
fun MainContent(
    drawerState: DrawerState,
    onMenuClicked: () -> Unit,
) {
    NavHost(
        navController = rememberNavController(),
        startDestination = "home"
    ) {
        composable("home") {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.Start
            ) {
                IconButton(onClick = onMenuClicked) {
                    Icon(
                        painter = painterResource(id = if (drawerState.isClosed) R.drawable.menu else R.drawable.close_menu),
                        contentDescription = if (drawerState.isClosed) "Open Menu" else "Close Menu"
                    )
                }
                // Home content goes here
                DashBoard(message = Message(author = "Usman", body = "My test Body"))
            }
        }
        composable("settings") {
            SettingsPage()
        }
    }
}

//@Composable
//fun HomeScreen(drawerState: DrawerState, onMenuClicked: () -> Unit) {
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        verticalArrangement = Arrangement.Top,
//        horizontalAlignment = Alignment.Start
//    ) {
//        IconButton(onClick = onMenuClicked) {
//            Icon(
//                painter = painterResource(id = if (drawerState.isClosed) R.drawable.menu else R.drawable.close_menu),
//                contentDescription = if (drawerState.isClosed) "Open Menu" else "Close Menu"
//            )
//        }
//
//        // Home content
//        Greeting(message = Message(author = "Usman", body = "My test Body"))
//    }
//}


