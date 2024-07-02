package com.example.nycschool

import android.os.Bundle
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
import androidx.compose.ui.unit.dp
import com.example.nycschool.data.repository.HighSchoolModel
import com.example.nycschool.ui.theme.NYCSchoolTheme
import com.example.nycschool.ui.theme.viewModel.HighSchoolViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HighSchoolActivity : ComponentActivity() {

    private val highSchoolViewModel: HighSchoolViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NYCSchoolTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    HighSchoolResults(
                        highSchoolViewModel = highSchoolViewModel,
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
private fun HighSchoolResults(highSchoolViewModel: HighSchoolViewModel, modifier: Modifier) {
    val highSchoolResult by highSchoolViewModel.allHighSchoolUsers
    val errorMessage by highSchoolViewModel.errorMessage

    if (errorMessage.isNotEmpty()) {
        Text(text = errorMessage, modifier.padding(15.dp))
    } else {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.padding(15.dp)
        ) {
            LazyColumn {
                items(highSchoolResult) { result ->
                    HighSchoolItems(result)
                }
            }
        }
    }
}

@Composable
fun HighSchoolItems(result: HighSchoolModel) {
    Text(text = result.dbn, modifier = Modifier.padding(top = 10.dp))
}
