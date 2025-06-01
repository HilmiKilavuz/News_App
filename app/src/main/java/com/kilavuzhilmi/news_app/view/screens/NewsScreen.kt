package com.kilavuzhilmi.news_app.view.screens

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.gson.Gson
import com.kilavuzhilmi.news_app.view.compenents.NewsItem
import com.kilavuzhilmi.news_app.viewmodel.NewsViewModel
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction


@Composable
fun NewsScreen(
    navController: NavController,
    viewModel: NewsViewModel = viewModel()
) {
    val articles = viewModel.newsList
    val errorMessage = viewModel.errorMessage
    val isLoading = viewModel.isLoading

    val searchQuery by viewModel.searchQuery.collectAsState()
    val searchArticles by viewModel.filteredArticles.collectAsState()

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
                Column {
                    val focusManager = LocalFocusManager.current

                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = { viewModel.setSearchQuery(it) },
                        label = { Text("Haber Ara") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp).padding(top = 30.dp),
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                        keyboardActions = KeyboardActions(onSearch = {
                            focusManager.clearFocus() // This will close the keyboard
                        })
                    )

                    LazyColumn(
                        modifier = Modifier
                            .padding(innerPadding)
                            .fillMaxSize()
                    ) {
                        if(searchQuery.isEmpty()){
                            items(articles) { article ->
                                NewsItem(
                                    article = article,
                                    onItemClick = {
                                        val gson = Gson()
                                        val articleJson = Uri.encode(gson.toJson(article))
                                        navController.navigate("newsDetail/$articleJson")

                                    })
                            }

                        }else{
                            items(searchArticles) { article ->
                                NewsItem(
                                    article = article,
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
    }
}


