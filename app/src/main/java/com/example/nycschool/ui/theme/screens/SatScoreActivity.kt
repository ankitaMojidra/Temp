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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nycschool.data.model.SatScore
import com.example.nycschool.domain.NetworkStatus
import com.example.nycschool.ui.theme.theme.NYCSchoolTheme
import com.example.nycschool.viewmodel.SatScoreViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val satResultViewModel: SatScoreViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NYCSchoolTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    SatScoreListScreen(
                        satResultViewModel = satResultViewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
private fun SatScoreListScreen(satResultViewModel: SatScoreViewModel, modifier: Modifier) {
    val satScore by satResultViewModel.allSatResultUsers

    val networkStatus by satResultViewModel.networkStatus

    Box(modifier = modifier.fillMaxSize()) {
        when (networkStatus) {
            is NetworkStatus.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is NetworkStatus.Success -> {
                val satScore = (networkStatus as NetworkStatus.Success<List<SatScore>>).data
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
                            SatResultItems(result)
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
private fun SatResultItems(result: SatScore) {
    Text(
        text = "School DBN: ${result.dbn}",
        modifier = Modifier.padding(top = 10.dp)
    )
    Text(
        text = "Math Score: ${result.sat_math_avg_score}",
        modifier = Modifier.padding(top = 10.dp)
    )
    Text(
        text = "Reading Score: ${result.sat_critical_reading_avg_score}",
        modifier = Modifier.padding(top = 10.dp)
    )
    Text(
        text = "Writing Score: ${result.sat_writing_avg_score}",
        modifier = Modifier.padding(top = 10.dp)
    )
    Divider(modifier = Modifier.padding(top = 10.dp))
}


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NYCSchoolTheme {
        Greeting("Android")
    }
}