import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.nycschool.data.model.School
import com.example.nycschool.data.remote.ApiService
import com.example.nycschool.domain.NetworkStatus
import com.example.nycschool.viewmodel.SchoolViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.*
import kotlinx.coroutines.withTimeout
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.junit.Assert.*

@ExperimentalCoroutinesApi
class SchoolViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    @Mock
    private lateinit var apiService: ApiService

    private lateinit var viewModel: SchoolViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `loadSchoolData success`() = runTest {
        // Given
        val mockSchools = listOf(
            School(
                dbn = "01M292",
                schoolName = "HENRY STREET SCHOOL FOR INTERNATIONAL STUDIES",
                totalStudents = "200",
                finalGrades = "9-12",
                location = "123 Main St",
                website = "www.example.com",
                schoolEmail = "school@example.com",
                phoneNumber = "123-456-7890",
                zip = "12345",
                neighborhood = "Downtown"
            )
        )
        `when`(apiService.getSchools()).thenReturn(mockSchools)

        // When
        viewModel = SchoolViewModel(apiService)

        // Then
        advanceUntilIdle() // This will run all pending coroutines

        // Wait for the status to change (with a timeout)
        withTimeout(5000) {
            while (viewModel.networkStatus.value !is NetworkStatus.Success) {
                delay(100)
            }
        }

        val networkStatus = viewModel.networkStatus.value
        println("Network Status: $networkStatus") // Add this line for debugging

        assertTrue("NetworkStatus should be Success", networkStatus is NetworkStatus.Success)
        if (networkStatus is NetworkStatus.Success) {
            assertEquals("Data in Success status should match", mockSchools, networkStatus.data)
        }
    }

    @Test
    fun `loadSchoolData error`() = runTest {
        // Given
        val errorMessage = "Network error"
        `when`(apiService.getSchools()).thenThrow(RuntimeException(errorMessage))

        // When
        viewModel = SchoolViewModel(apiService)

        // Then
        testDispatcher.scheduler.advanceUntilIdle()

        val networkStatus = viewModel.networkStatus.value
        assertTrue("NetworkStatus should be Error", networkStatus is NetworkStatus.Error)

        assertEquals("Error message should match", errorMessage, (networkStatus as NetworkStatus.Error).exception)

        assertTrue("ViewModel allSchools should be empty", viewModel.schools.value.isEmpty())
    }
}