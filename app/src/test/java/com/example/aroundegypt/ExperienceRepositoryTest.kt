package com.example.aroundegypt

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.aroundegypt.data.local.database.ExperienceDao
import com.example.aroundegypt.data.model.Experience
import com.example.aroundegypt.data.remote.ExperienceApiService
import com.example.aroundegypt.data.repository.ExperienceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.whenever
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class ExperienceRepositoryTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    @Mock
    private lateinit var mockApplication: Application

    @Mock
    private lateinit var mockApiService: ExperienceApiService

    @Mock
    private lateinit var mockExperienceDao: ExperienceDao

    private lateinit var experienceRepository: ExperienceRepository

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        whenever(mockApplication.applicationContext).thenReturn(mockApplication)
        experienceRepository = ExperienceRepository(mockApplication)

        val apiField = ExperienceRepository::class.java.getDeclaredField("apiService")
        apiField.isAccessible = true
        apiField.set(experienceRepository, mockApiService)
    }

    @Test
    fun fetchSearchExperiencesNoReturnWhenOffline() = runTest{
        val result = experienceRepository.fetchSearchExperiences("Luxor")

        assertEquals(emptyList<Experience>(), result)
    }

    @Test
    fun likeExperienceReturnSuccessfulResponse() = runTest {
        whenever(mockApiService.likeExperience("1")).thenReturn(Response.success(Unit))

        val response = experienceRepository.likeExperience("1")

        assertEquals(true, response.isSuccessful)
    }

    @Test
    fun likeExperienceHandlesAPIError() = runTest {
        whenever(mockApiService.likeExperience("1")).thenReturn(Response.error(500,
            "Error".toResponseBody(null)
        ))

        val response = experienceRepository.likeExperience("1")

        assertEquals(false, response.isSuccessful)
    }
}
