package com.kilavuzhilmi.news_app.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kilavuzhilmi.news_app.model.Article
import com.kilavuzhilmi.news_app.services.RetrofitInstance
import kotlinx.coroutines.launch

class NewsViewModel : ViewModel(){

    var newsList by mutableStateOf<List<Article>>(emptyList())
    var errorMessage by mutableStateOf("")
    var isLoading by mutableStateOf(true)

    init {
        fetchNews()
    }

     fun fetchNews() {
        viewModelScope.launch {
            isLoading = true
            try {
                Log.d("NewsViewModel", "Fetching news...")
                val response = RetrofitInstance.api.getTopHeadlines( country = "us",apiKey = "")
                Log.d("NewsViewModel", "News received: ${response.articles.size} articles")

                if (response.articles.isNotEmpty()) {
                    newsList = response.articles
                    errorMessage = ""
                    Log.d("NewsViewModel", "First article title: ${response.articles[0].title}")
                } else {
                    errorMessage = "Haberler alınamadı: Boş liste döndü"
                    Log.e("NewsViewModel", "Empty articles list received")
                }
            } catch (e: Exception) {
                Log.e("NewsViewModel", "Error fetching news", e)
                errorMessage = "Haberler alınamadı: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }
}