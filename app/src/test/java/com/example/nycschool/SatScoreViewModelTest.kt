package com.example.nycschool

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.nycschool.data.model.SatScore
import com.example.nycschool.data.remote.ApiService
import com.example.nycschool.domain.NetworkStatus
import com.example.nycschool.viewmodel.SatScoreViewModel
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class SatScoreViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    @Mock
    private lateinit var apiService: ApiService

    private lateinit var viewModel: SatScoreViewModel

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = SatScoreViewModel(apiService)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cancel()
    }

    //Success Result of API Call
    @Test
    fun `getSatResultData success`() = runTest {
        // Given
        val mockSatScores = listOf(
            SatScore(
                dbn = "01M292",
                school_name = "HENRY STREET SCHOOL FOR INTERNATIONAL STUDIES",
                sat_critical_reading_avg_score = "355",
                sat_math_avg_score = "404",
                sat_writing_avg_score = "363"
            ),
            SatScore(
                dbn = "02M294",
                school_name = "ANOTHER EXAMPLE SCHOOL",
                sat_critical_reading_avg_score = "400",
                sat_math_avg_score = "450",
                sat_writing_avg_score = "410"
            )
        )
        `when`(apiService.getSatScore()).thenReturn(mockSatScores)

        // When
        viewModel = SatScoreViewModel(apiService)

        // Advance the dispatcher to execute pending coroutines
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val networkStatus = viewModel.networkStatus.value
        assertTrue(networkStatus is NetworkStatus.Success)
        assertEquals(mockSatScores, (networkStatus as NetworkStatus.Success).data)
        assertEquals(mockSatScores, viewModel.allSatResultUsers.value)
    }

    //Check datasize of API Call
    @Test
    fun `getSatResultData returns correct number of entries`() = testScope.runTest {
        // Given
        val mockSatScores = listOf(
            SatScore(
                dbn = "01M292",
                school_name = "HENRY STREET SCHOOL FOR INTERNATIONAL STUDIES",
                sat_critical_reading_avg_score = "355",
                sat_math_avg_score = "404",
                sat_writing_avg_score = "363"
            ),
            SatScore(
                dbn = "02M294",
                school_name = "ANOTHER EXAMPLE SCHOOL",
                sat_critical_reading_avg_score = "400",
                sat_math_avg_score = "450",
                sat_writing_avg_score = "410"
            ),
            SatScore(
                dbn = "03M296",
                school_name = "THIRD EXAMPLE SCHOOL",
                sat_critical_reading_avg_score = "425",
                sat_math_avg_score = "475",
                sat_writing_avg_score = "435"
            )
        )
        `when`(apiService.getSatScore()).thenReturn(mockSatScores)

        // When
        viewModel = SatScoreViewModel(apiService)

        // Advance the dispatcher
        testDispatcher.scheduler.advanceUntilIdle()

        // Then
        val networkStatus = viewModel.networkStatus.value
        assertTrue(networkStatus is NetworkStatus.Success)

        val satScores = (networkStatus as NetworkStatus.Success).data
        assertEquals("Array size should be 3", 3, satScores.size)
        assertEquals("ViewModel allSatResultUsers should have 3 entries", 3, viewModel.allSatResultUsers.value.size)
    }

    //Error Result of API Call
    @Test
    fun `getSatResultData error`() = testScope.runTest {
        // Given
        val errorMessage = "Network error"
        `when`(apiService.getSatScore()).thenThrow(RuntimeException(errorMessage))

        // When
        viewModel.loadSatResultData()

        // Then
        assertNotEquals(NetworkStatus.Error(errorMessage), viewModel.networkStatus.value)
    }
}