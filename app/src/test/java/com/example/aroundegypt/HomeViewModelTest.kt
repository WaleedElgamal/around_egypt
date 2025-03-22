package com.example.aroundegypt

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.aroundegypt.data.model.Experience
import com.example.aroundegypt.data.repository.ExperienceRepository
import com.example.aroundegypt.viewmodel.HomeViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.atLeastOnce
import org.mockito.Mockito.verify
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.whenever
import retrofit2.Response

@ExperimentalCoroutinesApi
class HomeViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val testDispatcher = StandardTestDispatcher()

    @Mock
    private lateinit var application: Application

    @Mock
    private lateinit var experienceRepository: ExperienceRepository

    private lateinit var homeViewModel: HomeViewModel

    @Mock
    private lateinit var recommendedObserver: Observer<List<Experience>>

    @Mock
    private lateinit var recentObserver: Observer<List<Experience>>

    @Mock
    private lateinit var searchObserver: Observer<List<Experience>>

    @Mock
    private lateinit var isLoadingObserver: Observer<Boolean>

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        whenever(application.applicationContext).thenReturn(application)
        homeViewModel = HomeViewModel(application)
        homeViewModel.experienceRepository = experienceRepository

        homeViewModel.recommendedExperiences.observeForever(recommendedObserver)
        homeViewModel.recentExperiences.observeForever(recentObserver)
        homeViewModel.searchExperiences.observeForever(searchObserver)
        homeViewModel.isLoading.observeForever(isLoadingObserver)
    }

    @Test
    fun  fetchRecommendedExperiencesUpdatesLiveData() = runTest {
        val experiences = listOf(Experience("1", "Pyramids", "","Description", 100, 10, 1, mapOf("name" to "Cairo")))
        whenever(experienceRepository.fetchRecommendedExperiences()).thenReturn(experiences)

        homeViewModel.fetchRecommendedExperiences()
        advanceUntilIdle()

        verify(recommendedObserver).onChanged(experiences)
        verify(isLoadingObserver, atLeastOnce()).onChanged(false)
    }

    @Test
    fun fetchRecentExperiencesUpdatesLiveData() = runTest {
        val experiences = listOf(Experience("2", "Nile Cruise", "","Description", 50, 5, 0, mapOf("name" to "Aswan")))
        whenever(experienceRepository.fetchRecentExperiences()).thenReturn(experiences)

        homeViewModel.fetchRecentExperiences()
        advanceUntilIdle()

        verify(recentObserver).onChanged(experiences)
        verify(isLoadingObserver, atLeastOnce()).onChanged(false)
    }

    @Test
    fun searchUpdatesLiveData() = runTest {
        val query = "Pyramids"
        val experiences = listOf(Experience("1", "Pyramids", "","Description", 100, 10, 1, mapOf("name" to "Cairo")))
        whenever(experienceRepository.fetchSearchExperiences(query)).thenReturn(experiences)

        homeViewModel.search(query)
        advanceUntilIdle()

        verify(searchObserver).onChanged(experiences)
        verify(isLoadingObserver, atLeastOnce()).onChanged(false)
    }

    @Test
    fun searchWithBlankQueryClearsExperiences() {
        homeViewModel.search("")
        verify(searchObserver).onChanged(emptyList())
    }

    @Test
    fun clearSearchResetsExperiences() {
        homeViewModel.clearSearch()
        verify(searchObserver).onChanged(emptyList())
    }

    @Test
    fun likeExperienceUpdatesLikesCount() = runTest {
        val experience = Experience("1", "Pyramids", "","Description", 100, 10, 1, mapOf("name" to "Cairo"))
        val updatedExperience = experience.copy(likes_no = experience.likes_no + 1)
        val experiences = listOf(experience)

        whenever(experienceRepository.fetchRecommendedExperiences()).thenReturn(experiences)
        whenever(experienceRepository.likeExperience("1")).thenReturn(Response.success(Unit))

        homeViewModel.fetchRecommendedExperiences()
        advanceUntilIdle()

        homeViewModel.likeExperience("1")
        advanceUntilIdle()

        verify(recommendedObserver).onChanged(listOf(updatedExperience))
    }
    @Test
    fun fetchRecommendedExperiencesHandlesEmptyList() = runTest {
        whenever(experienceRepository.fetchRecommendedExperiences()).thenReturn(emptyList())

        homeViewModel.fetchRecommendedExperiences()
        advanceUntilIdle()

        verify(recommendedObserver).onChanged(emptyList())
    }

    @Test
    fun fetchRecentExperiencesHandlesEmptyList() = runTest {
        whenever(experienceRepository.fetchRecentExperiences()).thenReturn(emptyList())

        homeViewModel.fetchRecentExperiences()
        advanceUntilIdle()

        verify(recentObserver).onChanged(emptyList())
    }
}
