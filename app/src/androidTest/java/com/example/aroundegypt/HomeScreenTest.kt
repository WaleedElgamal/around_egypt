package com.example.aroundegypt

import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.lifecycle.asLiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.aroundegypt.data.model.Experience
import com.example.aroundegypt.view.screens.HomePage
import com.example.aroundegypt.viewmodel.HomeViewModel
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var mockHomeViewModel: HomeViewModel

    @Before
    fun setUp() {
        mockHomeViewModel = mockk(relaxed = true)

        val mockRecommendedData = MutableStateFlow(
            listOf(
                Experience("1", "Pyramids", "", "Description", 100, 10, 1, mapOf("name" to "Cairo")),
                Experience("2", "Sphinx", "", "Description", 50, 5, 0, mapOf("name" to "Giza"))
            )
        )
        every { mockHomeViewModel.recommendedExperiences } returns mockRecommendedData.asLiveData()

        val mockRecentData = MutableStateFlow(
            listOf(
                Experience("3", "Luxor", "", "Description", 50, 5, 0, mapOf("name" to "Luxor"))
            )
        )
        every { mockHomeViewModel.recentExperiences } returns mockRecentData.asLiveData()
    }

    @Test
    fun homeScreen_showsRecommendedAndRecentExperiences() {
        composeTestRule.setContent {
            HomePage(viewModel = mockHomeViewModel)
        }

        composeTestRule.onNodeWithText("Pyramids").assertExists()
        composeTestRule.onNodeWithText("Sphinx").assertExists()
        composeTestRule.onNodeWithText("Luxor").assertExists()
    }

    @Test
    fun clickingExperience_opensExperienceDialog() {
        composeTestRule.setContent {
            HomePage(viewModel = mockHomeViewModel)
        }

        composeTestRule.onNodeWithText("Pyramids").performClick()
        composeTestRule.onNodeWithContentDescription("Description Header")
            .assertExists()

        composeTestRule.onNodeWithContentDescription("Experience Description")
            .assertExists()
    }

    @Test
    fun searchFunctionality_filtersResults() {
        composeTestRule.setContent {
            HomePage(viewModel = mockHomeViewModel)
        }

        composeTestRule.onNodeWithTag("SearchBar").performTextInput("Luxor")

        composeTestRule.onAllNodesWithText("Luxor").assertCountEquals(2)
    }

    @Test
    fun clearSearch_resetsResults() {
        composeTestRule.setContent {
            HomePage(viewModel = mockHomeViewModel)
        }

        composeTestRule.onNodeWithTag("SearchBar").performTextInput("Luxor")
        composeTestRule.onNodeWithTag("ClearSearchButton").performClick()

        composeTestRule.onNodeWithText("Pyramids").assertExists()
        composeTestRule.onNodeWithText("Luxor").assertExists()
    }

    @Test
    fun clickingLikeButton_updatesLikesCount() {
        composeTestRule.setContent {
            HomePage(viewModel = mockHomeViewModel)
        }

        composeTestRule
            .onAllNodesWithTag("LikeButton")[0]
            .performClick()

        coVerify { mockHomeViewModel.likeExperience("1") }
    }
}
