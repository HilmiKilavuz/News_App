package com.kilavuzhilmi.news_app.view.compenents

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.kilavuzhilmi.news_app.R
import com.kilavuzhilmi.news_app.model.Article
import com.kilavuzhilmi.news_app.viewmodel.functions.FormatDate

@Composable
fun NewsItem(article: Article, onItemClick: () -> Unit, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth().defaultMinSize(minHeight = 300.dp )
            .padding(8.dp).clickable(
                onClick = onItemClick
            ),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Image at the top
            article.urlToImage?.let { imageUrl ->
                Image(
                    painter = rememberAsyncImagePainter(imageUrl),
                    contentDescription = article.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
            }?: Image(
                    painter = rememberAsyncImagePainter(R.drawable.nophoto),
                    contentDescription = article.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )


            // Content below the image
            Column(modifier = Modifier.padding(8.dp)) {
                article.title.let { title ->
                    Text(
                        text = title,
                        modifier = Modifier.padding(bottom = 4.dp),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold

                        )
                }?: Text(text = "Title: Unknown")

                article.description?.let { description ->
                    Text(
                        text = description,
                        modifier = Modifier.padding(bottom = 4.dp),
                        maxLines = 4,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }?: Text(text = "Description: Unknown")

                Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
                    article.author?.let { author ->
                        Text(
                            text ="Author: "+ author,
                            modifier = Modifier.padding(end = 8.dp),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    article.publishedAt?.let { publishedAt ->
                        Text(
                            text = FormatDate.formatDate(publishedAt),
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }
        }
    }
}