package com.example.nycschool

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.nycschool.data.repository.HighSchoolModel
import com.example.nycschool.ui.theme.NYCSchoolTheme
import com.example.nycschool.ui.theme.viewModel.HighSchoolViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HighSchoolActivity : ComponentActivity() {

    //  private val highSchoolViewModel: HighSchoolViewModel by viewModels()
    val viewModel = ViewModelProvider(this).get(HighSchoolViewModel::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NYCSchoolTheme {
                /* Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                     HighSchoolResults(
                         highSchoolViewModel = highSchoolViewModel,
                         modifier = Modifier.padding(innerPadding)
                     )
                 }*/

                HighSchoolList(viewModel)

            }
        }
    }
}

@Composable
fun HighSchoolList(viewModel: HighSchoolViewModel) {
    // Collecting the PagingData as LazyPagingItems
    val lazyPagingItems = viewModel.allHighSchoolUsers.collectAsLazyPagingItems()

    /*LazyColumn {
        // Correct usage of items with LazyPagingItems
        items(lazyPagingItems) { school ->
            // Assuming 'school' is a HighSchoolModel, display its data
            school?.let {
                Text(text = it.name) // Display the school name
            }
        }
    }*/
}

/*@Composable
private fun HighSchoolResults(highSchoolViewModel: HighSchoolViewModel, modifier: Modifier) {
    val highSchoolResult by highSchoolViewModel.allHighSchoolUsers
    val errorMessage by highSchoolViewModel.errorMessage

    if (errorMessage.isNotEmpty()) {
        Text(text = errorMessage, modifier.padding(15.dp))
        Log.e("ErrorMessage","ErrorMessage: $errorMessage")
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
}*/

@Composable
fun HighSchoolItems(result: HighSchoolModel) {
    Text(text = result.dbn, modifier = Modifier.padding(top = 10.dp))
}
