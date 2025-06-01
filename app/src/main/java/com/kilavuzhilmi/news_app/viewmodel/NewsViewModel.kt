package com.kilavuzhilmi.news_app.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kilavuzhilmi.news_app.BuildConfig

import com.kilavuzhilmi.news_app.model.Article
import com.kilavuzhilmi.news_app.services.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel() {
    private val _articles = MutableStateFlow<List<Article>>(emptyList())
    val articles: StateFlow<List<Article>> = _articles

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery


    var newsList by mutableStateOf<List<Article>>(emptyList())
    var errorMessage by mutableStateOf("")
    var isLoading by mutableStateOf(true)

    init {
        fetchNews()
    }

    val filteredArticles: StateFlow<List<Article>> =
        combine(_articles, _searchQuery) { articles, query ->
            if (query.isBlank()) articles
            else articles.filter { it.title.contains(query, ignoreCase = true) }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun filterArticles(allArticles: List<Article>): List<Article> {
        val query = searchQuery.value.lowercase()
        return if (query.isBlank()) {
            allArticles
        } else {
            allArticles.filter { it.title.contains(query, ignoreCase = true) }
        }
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun getTopHeadlines(apiKey: String = BuildConfig.NEWS_API_KEY) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getTopHeadlines(apiKey = apiKey)
                _articles.value = response.articles
            } catch (e: Exception) {
                // hata işleme
            }
        }
    }


    fun fetchNews() {
        viewModelScope.launch {
            isLoading = true
            try {
                Log.d("NewsViewModel", "Fetching news...")
                Log.d("NewsViewModel", "BuildConfig API Key: ${BuildConfig.NEWS_API_KEY}")
                
                val response = RetrofitInstance.api.getTopHeadlines(
                    country = "us",
                    apiKey = BuildConfig.NEWS_API_KEY
                )
                Log.d("NewsViewModel", "News received: ${response.articles.size} articles")

                if (response.articles.isNotEmpty()) {
                    _articles.value = response.articles
                    newsList = response.articles
                    errorMessage = ""
                    Log.d("NewsViewModel", "First article title: ${response.articles[0].title}")
                } else {
                    errorMessage = "Haberler alınamadı: Boş liste döndü"
                    Log.e("NewsViewModel", "Empty articles list received")
                }
            } catch (e: Exception) {
                Log.e("NewsViewModel", "Error fetching news", e)
                Log.e("NewsViewModel", "Error message: ${e.message}")
                errorMessage = "Haberler alınamadı: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }


}