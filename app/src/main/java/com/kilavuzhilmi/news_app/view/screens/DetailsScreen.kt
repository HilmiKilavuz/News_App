package com.kilavuzhilmi.news_app.view.screens

import android.content.Intent
import android.graphics.drawable.Icon
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.kilavuzhilmi.news_app.model.Article
import com.kilavuzhilmi.news_app.view.compenents.FloatingActionButton
import com.kilavuzhilmi.news_app.view.compenents.GradientButton
import com.kilavuzhilmi.news_app.view.compenents.IconTextButton
import com.kilavuzhilmi.news_app.view.compenents.NeumorphicButton
import com.kilavuzhilmi.news_app.view.compenents.OutlinedGlowButton
import com.kilavuzhilmi.news_app.viewmodel.DetailsScreenViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    navController: NavController,
    viewModel: DetailsScreenViewModel = viewModel(),
    article: Article
) {
    Scaffold(topBar = {
        TopAppBar(
            title = { Text("Haber Detayı") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(imageVector = Icons.Rounded.Close, contentDescription = "Geri")
                }
            }
        )

    }) { innepadding ->
        Column(modifier = Modifier.padding(innepadding)) {
            val context = LocalContext.current
            article.urlToImage.let { image ->
                Image(
                    painter = rememberAsyncImagePainter(image),
                    contentDescription = article.title,
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth()

                        .padding(bottom = 10.dp)
                )

            } ?: Text(text = "Image: Unknown")

            article.title.let { title ->
                Text(
                    text = title, modifier = Modifier
                        .padding(8.dp)
                        .padding(bottom = 10.dp),
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            } ?: Text(text = "Title: Unknown")
            article.description.let { description ->
                Text(
                    text = description.toString(),
                    modifier = Modifier
                        .padding(8.dp)
                        .padding(bottom = 10.dp),
                    style = MaterialTheme.typography.bodyMedium
                )

            } ?: Text(text = "Description: Unknown")
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                article.author.let { author ->
                    Text(
                        text = "Author: " + author.toString(),
                        modifier = Modifier
                            .padding(8.dp)
                            .padding(bottom = 10.dp),
                        style = MaterialTheme.typography.bodyMedium
                    )
                } ?: Text(text = "Author: Unknown")

                article.publishedAt.let { publishedAt ->
                    Text(
                        text = publishedAt.toString(),
                        modifier = Modifier
                            .padding(8.dp)
                            .padding(bottom = 10.dp)
                    )
                } ?: Text(text = "Published At: Unknown")
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                article.url.let { url ->


                    OutlinedGlowButton(
                        text = "Haberi Oku",
                        modifier = Modifier.padding(top = 10.dp),
                        onClick = {
                            article.url.let { url ->
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                                context.startActivity(intent)}}
                    ) }?: Text(text = "Url: Unknown")


            }






        }

    }


}

