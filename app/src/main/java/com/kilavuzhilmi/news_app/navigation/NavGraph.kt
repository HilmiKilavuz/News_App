package com.kilavuzhilmi.news_app.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.gson.Gson
import com.kilavuzhilmi.news_app.model.Article
import com.kilavuzhilmi.news_app.view.screens.DetailsScreen
import com.kilavuzhilmi.news_app.view.screens.NewsScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavGraph(
    navController: NavHostController,
    startDestination: String = "newsList",
    articleToShow: Article? = null
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }) },
        exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }) },
        popEnterTransition = { slideInHorizontally(initialOffsetX = { -1000 }) },
        popExitTransition = { slideOutHorizontally(targetOffsetX = { 1000 }) }
    ) {
        composable("newsList") {
            NewsScreen(navController = navController)


        }
        composable(
            route = "newsDetail/{article}",
            arguments = listOf(navArgument("article") { type = NavType.StringType })
        ) { backStackEntry ->
            val json = backStackEntry.arguments?.getString("article")
            val article = Gson().fromJson(json, Article::class.java)

            DetailsScreen(article = article, navController = navController)
        }


    }

}