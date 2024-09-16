package com.example.myapplication

import android.app.LocaleConfig
import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SideNav() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    var msg = Message(author = "Usman", body = "My test Body")
    val currentNavBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentNavBackStackEntry?.destination?.route ?: "Home"
    val navigationIcon = when (currentRoute) {
        "home" -> Icons.Filled.Home
        "settings" -> Icons.Filled.Settings
        "myCalendar" -> Icons.Filled.DateRange
        else -> Icons.Filled.Home
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            DrawerContent(currentRoute) { selectedItem ->
                navController.navigate(selectedItem)
                scope.launch { drawerState.close() }
            }
        },
        content = {
            Scaffold (
                topBar = {
                    TopAppBar(
                        title = { Text(currentRoute.replaceFirstChar {
                            if (it.isLowerCase()) it.titlecase(
                                Locale.ROOT
                            ) else it.toString()
                        }) },
                        navigationIcon = {
                            IconButton(onClick = {
                                scope.launch {
                                    if (drawerState.isClosed) drawerState.open() else drawerState.close()
                                }
                            }) {
                                Icon(Icons.Outlined.Menu, contentDescription = "Menu")
                            }
                        }
                    )
                },
                content = { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding)
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
                        composable("My Calendar"){
//                    val myCalendarViewModel: MyCalenderViewModel = viewModel();
                            MonthView()
                        }
                        // Add more composable routes here as needed
                    }
                }
            )
        }
    )
}


@Composable
fun DrawerContent(
    currentRoute :String,
    onItemClicked: (String) -> Unit
) {
    val customGray = Color(0xFF888888)
    val isDarkMode = LocalConfiguration.current.uiMode and Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
    ModalDrawerSheet(modifier = Modifier.background(Color.Red)) {
        NavigationDrawerItem(
            label = {
                Text(
                    text = "Home",
                    style = MaterialTheme.typography.titleMedium,
                )
            },
            icon = { Icon(imageVector = Icons.Outlined.Home, contentDescription = null) },
            shape = MaterialTheme.shapes.small,
            selected = currentRoute == "Home",
            onClick = {
                onItemClicked("Home");
            },
        )
        NavigationDrawerItem(
            label = {
                Text(
                    text = "Settings",
                    style = MaterialTheme.typography.titleMedium,
                )
            },
            icon = { Icon(imageVector = Icons.Outlined.Settings, contentDescription = null) },
            shape = MaterialTheme.shapes.small,
            selected = currentRoute == "Settings",
            onClick = { onItemClicked("Settings") },
        )

        NavigationDrawerItem(
            label = {
                Text(
                    text = "My Calendar",
                    style = MaterialTheme.typography.titleMedium
                )
            },
            icon = { Icon(imageVector = Icons.Outlined.DateRange, contentDescription = null) },
            shape = MaterialTheme.shapes.small,
            selected = currentRoute == "My Calendar",
            onClick = { onItemClicked("My Calendar") },
        )
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


