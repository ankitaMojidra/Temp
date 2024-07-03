package com.example.nycschool

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nycschool.data.repository.SchoolRepository
import com.example.nycschool.ui.theme.NYCSchoolTheme
import com.example.nycschool.ui.theme.screens.SchoolListScreen
import com.example.nycschool.viewmodel.SchoolViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NYCSchoolTheme {
              //NYCSchoolsApp()
                }
            }
        }
    }

/*fun NYCSchoolsApp() {
    val navController = rememberNavController()
    val viewModel: SchoolViewModel = remember { SchoolViewModel(SchoolRepository(*//* Provide API instance *//*)) }

    NavHost(navController = navController, startDestination = "schoolList") {
        composable("schoolList") {
            SchoolListScreen(viewModel = viewModel) { school ->
               // navController.navigate("schoolDetail/${school.dbn}")
            }
        }
        composable("schoolDetail/{dbn}") { backStackEntry ->
            val dbn = backStackEntry.arguments?.getString("dbn")
            val school = viewModel.schools.value.find { it.dbn == dbn }
            school?.let {
               // SchoolDetailScreen(viewModel = viewModel, school = it)
            }
        }
    }
}*/

@Preview(showBackground = true)
@Composable
fun GreetingPreview2() {
    NYCSchoolTheme {
       // NYCSchoolsApp()
    }
}