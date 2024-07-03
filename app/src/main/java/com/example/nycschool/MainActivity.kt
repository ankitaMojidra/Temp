package com.example.nycschool

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.nycschool.data.repository.SatResultModel
import com.example.nycschool.ui.theme.NYCSchoolTheme
import com.example.nycschool.ui.theme.viewModel.SatResultViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val satResultViewModel: SatResultViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NYCSchoolTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HighSchoolResults(
                        // highSchoolViewModel = highSchoolViewModel,
                        satResultViewModel = satResultViewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
private fun HighSchoolResults(satResultViewModel: SatResultViewModel, modifier: Modifier) {
    val highSchoolResult by satResultViewModel.allSatResultUsers
    val errorMessage by satResultViewModel.errorMessage

    if (errorMessage.isNotEmpty()) {
        Text(text = errorMessage, modifier.padding(15.dp))
    } else {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.padding(15.dp)
        ) {
            Column {
                Text(
                    text = "Sat Scores",
                    modifier = Modifier.padding(top = 15.dp),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold, textAlign = TextAlign.Center
                )
                Divider(
                    color = Color.Black,
                    thickness = 3.dp,
                    modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
                )

                LazyColumn {
                    items(highSchoolResult) { result ->
                        SatResultItems(result)
                    }
                }
            }
        }
    }
}

@Composable
fun SatResultItems(result: SatResultModel) {

    Text(
        text = "Sat Math Avg Score: ${"result.sat_math_avg_score"}",
        modifier = Modifier.padding(top = 15.dp)
    )

    Text(
        text = "Sat Critical Avg Score: ${result.sat_critical_reading_avg_score}",
        modifier = Modifier.padding(top = 10.dp)
    )
    Text(
        text = "Sat Writing Avg Score: ${result.sat_writing_avg_score}",
        modifier = Modifier.padding(top = 10.dp)
    )
    Text(
        text = "School Name: ${result.school_name}",
        modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
    )
    Divider()
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