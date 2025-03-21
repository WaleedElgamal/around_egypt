package com.example.aroundegypt.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aroundegypt.data.model.Experience
import com.example.aroundegypt.data.repository.ExperienceRepository
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    val experienceRepository : ExperienceRepository = ExperienceRepository()

    private val _recommendedExperiences = MutableLiveData<List<Experience>>()
    val recommendedExperiences: LiveData<List<Experience>> = _recommendedExperiences

    private val _recentExperiences = MutableLiveData<List<Experience>>()
    val recentExperiences: LiveData<List<Experience>> = _recentExperiences

    private val _searchExperiences = MutableLiveData<List<Experience>>()
    val searchExperiences: LiveData<List<Experience>> = _searchExperiences

    private val _isSearching = MutableLiveData(false)
    val isSearching: LiveData<Boolean> = _isSearching

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    init{
        viewModelScope.launch {
            fetchRecommendedExperiences()
            fetchRecentExperiences()
        }
    }

    fun fetchRecommendedExperiences(){
        viewModelScope.launch {
            _isLoading.postValue(true)
            val experiences = experienceRepository.fetchRecommendedExperiences()
            _recommendedExperiences.postValue(experiences)
        }
    }

    fun fetchRecentExperiences(){
        viewModelScope.launch {
            val experiences = experienceRepository.fetchRecentExperiences()
            _recentExperiences.postValue(experiences)
            _isLoading.postValue(false)

        }
    }

    fun search(query: String) {
        if (query.isBlank()) {
            _isSearching.value = false
            _searchExperiences.postValue(emptyList())
            return
        }

        _isSearching.value = true

        viewModelScope.launch {
            _isLoading.postValue(true)
            val experiences = experienceRepository.fetchSearchExperiences(query)
            _searchExperiences.postValue(experiences)
            _isLoading.postValue(false)
        }
    }

    fun clearSearch() {
        _isSearching.value = false
        _searchExperiences.postValue(emptyList())
    }

    fun likeExperience(id: String){
        viewModelScope.launch {
            val response = experienceRepository.likeExperience(id)

            if (response.isSuccessful) {
                _recommendedExperiences.value = _recommendedExperiences.value?.map { exp ->
                    if (exp.id == id) exp.copy(likes_no = exp.likes_no + 1) else exp
                }

                _recentExperiences.value = _recentExperiences.value?.map { exp ->
                    if (exp.id == id) exp.copy(likes_no = exp.likes_no + 1) else exp
                }

            }

        }
    }
}