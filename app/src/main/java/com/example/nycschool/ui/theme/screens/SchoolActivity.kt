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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
                    SchoolListScreen(schoolViewModel, modifier = Modifier.padding(innerPadding))
                    /*SchoolDetailScreen(
                        schoolViewModel,
                        modifier = Modifier.padding(innerPadding)
                    )*/
                }
            }
        }
    }
}

@Composable
fun SchoolListScreen(schoolViewModel: SchoolViewModel, modifier: Modifier) {

    val networkStatus by schoolViewModel.networkStatus
    val schools by schoolViewModel.schools.collectAsState()

    Box(modifier = modifier.fillMaxSize()) {
        when (networkStatus) {
            is NetworkStatus.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is NetworkStatus.Success -> {
                val satScore = (networkStatus as NetworkStatus.Success<List<School>>).data
                Column {
                    Text(
                        text = "Sat Scores",
                        modifier = Modifier.padding(top = 30.dp),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Divider(
                        color = Color.Black,
                        thickness = 3.dp,
                        modifier = Modifier.padding(top = 10.dp)
                    )

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

@Composable
fun SchoolDetailScreen(viewModel: SchoolViewModel, modifier: Modifier) {
    val school by viewModel.schools.collectAsState()
    val satScore = viewModel.getSatScoreForSchool(school.first().dbn)

    Column {
        Text(text = school.first().schoolName, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        Text(text = "Location: ${school.first().location}")

        satScore?.let {
            Text(text = "SAT Scores:")
            /* Text(text = "Reading: ${it.satCriticalReadingAvgScore}")
             Text(text = "Math: ${it.satMathAvgScore}")
             Text(text = "Writing: ${it.satMathAvgScore}")*/
        }
    }
}