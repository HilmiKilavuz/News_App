package com.kilavuzhilmi.news_app.view.screens

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.gson.Gson
import com.kilavuzhilmi.news_app.view.compenents.NewsItem
import com.kilavuzhilmi.news_app.viewmodel.NewsViewModel


@Composable
fun NewsScreen(  navController: NavController,
                 viewModel: NewsViewModel = viewModel()) {
    val articles = viewModel.newsList
    val errorMessage = viewModel.errorMessage
    val isLoading = viewModel.isLoading
    when {
        isLoading ->
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }

        errorMessage.isNotEmpty() ->
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = errorMessage)
                    Button(
                        onClick = { viewModel.fetchNews() },
                        modifier = Modifier.padding(top = 8.dp)
                    ) {
                        Text("Tekrar Dene")
                    }
                }
            }

        articles.isEmpty() ->
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Haber bulunamadı")
            }

        else -> {
            Scaffold { innerPadding ->
                LazyColumn(modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()) {
                    items(articles) { article ->
                        NewsItem(article = article,
                            onItemClick = {
                                val gson = Gson()
                                val articleJson = Uri.encode(gson.toJson(article))
                                navController.navigate("newsDetail/$articleJson")

                        })
                    }
                }
            }
        }
    }
}