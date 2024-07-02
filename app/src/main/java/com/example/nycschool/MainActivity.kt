package com.example.nycschool

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nycschool.data.repository.SatResultModel
import com.example.nycschool.ui.theme.NYCSchoolTheme
import com.example.nycschool.ui.theme.viewModel.HighSchoolViewModel
import com.example.nycschool.ui.theme.viewModel.SatResultViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val highSchoolViewModel: HighSchoolViewModel by viewModels()
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
            LazyColumn {
                items(highSchoolResult) { result ->
                    SatResultItems(result)
                }
            }
        }
    }
}

@Composable
fun SatResultItems(result: SatResultModel) {
    Text(text = result.school_name, modifier = Modifier.padding(top = 10.dp))
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