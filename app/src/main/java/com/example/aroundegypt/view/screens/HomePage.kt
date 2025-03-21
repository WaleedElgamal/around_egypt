package com.example.aroundegypt.view.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.aroundegypt.data.model.Experience
import com.example.aroundegypt.view.components.CustomNavBar
import com.example.aroundegypt.view.components.DescriptionHeader
import com.example.aroundegypt.view.components.ItemCard
import com.example.aroundegypt.view.components.TitleHeader
import com.example.aroundegypt.viewmodel.HomeViewModel

@Composable
fun HomePage(viewModel: HomeViewModel) {
    val recommended by viewModel.recommendedExperiences.observeAsState(emptyList())
    val recent by viewModel.recentExperiences.observeAsState(emptyList())
    val searchResults by viewModel.searchExperiences.observeAsState(emptyList())
    val isSearching by viewModel.isSearching.observeAsState(false)
    val isLoading by viewModel.isLoading.observeAsState(false)

    Scaffold(
        containerColor = Color.White,
        topBar = {
            CustomNavBar(
                onSearchTriggered = { query -> viewModel.search(query) },
                onClearSearch = { viewModel.clearSearch() }
            )
        }
    ) {
        innerPadding ->
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {
                item {
                    if (isLoading) {
                        LoadingIndicator()
                    } else {
                        if (isSearching) {
                            SearchResults(searchResults, viewModel)
                        } else {
                            MainContent(recommended, recent, viewModel)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingIndicator() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        CircularProgressIndicator(color = Color.Black)
    }
}

@Composable
fun SearchResults(searchResults: List<Experience>, viewModel: HomeViewModel) {
    TitleHeader(title = "Search Results")
    if (searchResults.isEmpty()) {
        NoResultsFound()
    } else {
        ExperienceListVertical(searchResults, viewModel)
    }
}

@Composable
fun NoResultsFound() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        Text(text = "No results matching your search", color = Color.Black)
    }
}

@Composable
fun MainContent(recommended: List<Experience>, recent: List<Experience>, viewModel: HomeViewModel) {
    TitleHeader(title = "Welcome!")
    DescriptionHeader()

    TitleHeader(title = "Recommended Experiences")
    ExperienceListHorizontal(recommended, viewModel)

    TitleHeader(title = "Most Recent")
    ExperienceListVertical(recent, viewModel)
}


@Composable
fun ExperienceListHorizontal( experiences: List<Experience>, viewModel: HomeViewModel)
{
    LazyRow(
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(experiences) { experience ->
            ItemCard(experience, onLike = { id -> viewModel.likeExperience(id)})
        }
    }
}

@Composable
fun ExperienceListVertical(experiences: List<Experience>, viewModel: HomeViewModel)
{
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        experiences.forEach { experience ->
            ItemCard(experience = experience, onLike = { id -> viewModel.likeExperience(id)})
        }
    }
}
