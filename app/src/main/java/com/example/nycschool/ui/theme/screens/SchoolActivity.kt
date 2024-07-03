package com.example.nycschool.ui.theme.screens

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.nycschool.data.model.School
import com.example.nycschool.domain.NetworkStatus
import com.example.nycschool.ui.theme.theme.NYCSchoolTheme
import com.example.nycschool.viewmodel.SchoolViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SchoolActivity : ComponentActivity() {

    private val schoolViewModel: SchoolViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NYCSchoolTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SchoolListScreen(modifier = Modifier.padding(innerPadding),schoolViewModel)
                }
            }
        }
    }
}

@Composable
fun SchoolListScreen( modifier: Modifier = Modifier,
    schoolViewModel: SchoolViewModel = hiltViewModel(),

) {
    val networkStatus by schoolViewModel.networkStatus

    Box(modifier = modifier.fillMaxSize()) {
        when (networkStatus) {
            is NetworkStatus.Loading -> {
                //CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is NetworkStatus.Success -> {
                val satScore = (networkStatus as NetworkStatus.Success<List<School>>).data
                Column(modifier = Modifier.padding(top = 35.dp)) {

                    LazyColumn(modifier = Modifier.padding(15.dp)) {
                        items(satScore) { result ->
                            SchoolItems(result)
                        }
                    }
                }
            }

            is NetworkStatus.Error -> {
                val errorMessage = (networkStatus as NetworkStatus.Error).exception
                Text(
                    text = errorMessage,
                    modifier = Modifier
                        .padding(15.dp)
                        .align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
private fun SchoolItems(school: School) {
    Column {
        Text(
            text = "School DBN: ${school.dbn}",
            modifier = Modifier.padding(top = 10.dp)
        )
        Text(
            text = "School Name: ${school.schoolName}",
            modifier = Modifier.padding(top = 10.dp)
        )
        Text(
            text = "Total Students: ${school.totalStudents}",
            modifier = Modifier.padding(top = 10.dp)
        )
        Text(
            text = "Final Grades: ${school.finalGrades}",
            modifier = Modifier.padding(top = 10.dp)
        )
        Text(
            text = "School Location: ${school.location}",
            modifier = Modifier.padding(top = 10.dp)
        )
        Text(
            text = "School Website: ${school.website}",
            modifier = Modifier.padding(top = 10.dp)
        )
        Text(
            text = "School Email: ${school.schoolEmail}",
            modifier = Modifier.padding(top = 10.dp)
        )
        Text(
            text = "School Phone Number: ${school.phoneNumber}",
            modifier = Modifier.padding(top = 10.dp)
        )
        Text(
            text = "School ZipCode: ${school.zip}",
            modifier = Modifier.padding(top = 10.dp)
        )
        Text(
            text = "School Neighborhood: ${school.neighborhood}",
            modifier = Modifier.padding(top = 10.dp)
        )
        Divider(modifier = Modifier.padding(top = 10.dp))
    }
}