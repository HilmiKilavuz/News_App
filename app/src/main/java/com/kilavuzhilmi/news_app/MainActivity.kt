package com.kilavuzhilmi.news_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.kilavuzhilmi.news_app.navigation.NavGraph
import com.kilavuzhilmi.news_app.ui.theme.News_AppTheme
import com.kilavuzhilmi.news_app.view.screens.NewsScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            News_AppTheme {
                NavGraph(navController = navController,startDestination = "newsList")

            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

}