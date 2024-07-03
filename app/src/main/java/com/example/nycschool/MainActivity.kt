package com.example.nycschool

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nycschool.ui.theme.NYCSchoolTheme
import com.example.nycschool.ui.theme.screens.SatScoreListScreen
import com.example.nycschool.ui.theme.screens.SchoolListScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NYCSchoolTheme {
                NYCSchoolsApp()
            }
        }
    }
}

@Composable
fun NYCSchoolsApp() {
    val navController = rememberNavController()
    var selectedTabIndex by remember { mutableStateOf(0) }
    val scope = rememberCoroutineScope()
    val tabs = listOf("Schools", "Sat Scores")

    Scaffold(
        topBar = {
            TabRow(selectedTabIndex = selectedTabIndex) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = {
                            selectedTabIndex = index
                            scope.launch {
                                when (index) {
                                    0 -> navController.navigate("schools") { popUpTo(navController.graph.startDestinationId) }
                                    1 -> navController.navigate("sat_scores") {
                                        popUpTo(
                                            navController.graph.startDestinationId
                                        )
                                    }
                                }
                            }
                        },
                        text = { Text(title) },
                        modifier = if (selectedTabIndex == index) Modifier.padding(bottom = 4.dp) else Modifier.padding(
                            bottom = 8.dp
                        ),
                        selectedContentColor = Color.Black,
                        unselectedContentColor = Color.Gray
                    )
                }
            }
        }
    ) {
        NavHost(navController = navController, startDestination = "schools") {
            composable("schools") {
                SchoolListScreen()
            }
            composable("sat_scores") {
                SatScoreListScreen()
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun NYCSchoolsAppPreview() {
    NYCSchoolTheme {
        NYCSchoolsApp()
    }
}
