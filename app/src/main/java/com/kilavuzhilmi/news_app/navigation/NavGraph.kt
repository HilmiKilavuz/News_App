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

/**
 * NavGraph sınıfı - Uygulamanın ekranlar arası geçiş sistemini yöneten ana navigasyon bileşeni
 * 
 * Bu sınıf, uygulamadaki tüm ekranların nasıl birbirine bağlandığını ve aralarında nasıl geçiş yapılacağını tanımlar.
 * Jetpack Compose Navigation kütüphanesi kullanılarak oluşturulmuştur.
 * 
 * @OptIn(ExperimentalAnimationApi::class) - Animasyon API'sinin deneysel olduğunu belirtir
 * @param navController - Ekranlar arası geçişleri kontrol eden navigasyon kontrolcüsü
 * @param startDestination - Uygulamanın başlangıç ekranı (varsayılan: "newsList")
 * @param articleToShow - Gösterilecek haber (şu an kullanılmıyor ama gelecekte kullanılabilir)
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun NavGraph(
    navController: NavHostController,  // Ekranlar arası geçişleri yöneten kontrolcü
    startDestination: String = "newsList",  // Uygulamanın başlangıç ekranı
    articleToShow: Article? = null  // Gösterilecek haber (gelecekte kullanılabilir)
) {
    /**
     * AnimatedNavHost - Animasyonlu navigasyon konteynerı
     * 
     * Bu bileşen, ekranlar arası geçişlerde animasyon efektleri sağlar.
     * Normal NavHost'tan farklı olarak, geçiş animasyonları tanımlanabilir.
     * 
     * @param navController - Navigasyon kontrolcüsü
     * @param startDestination - Başlangıç ekranı
     * @param enterTransition - Yeni ekrana giriş animasyonu (sağdan sola kayma)
     * @param exitTransition - Mevcut ekrandan çıkış animasyonu (sola doğru kayma)
     * @param popEnterTransition - Geri dönüş giriş animasyonu (soldan sağa kayma)
     * @param popExitTransition - Geri dönüş çıkış animasyonu (sağa doğru kayma)
     */
    AnimatedNavHost(
        navController = navController,
        startDestination = startDestination,
        enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }) },  // Yeni ekran sağdan 1000 piksel kayarak girer
        exitTransition = { slideOutHorizontally(targetOffsetX = { -1000 }) },  // Mevcut ekran sola 1000 piksel kayarak çıkar
        popEnterTransition = { slideInHorizontally(initialOffsetX = { -1000 }) },  // Geri dönüşte ekran soldan 1000 piksel kayarak girer
        popExitTransition = { slideOutHorizontally(targetOffsetX = { 1000 }) }  // Geri dönüşte ekran sağa 1000 piksel kayarak çıkar
    ) {
        /**
         * "newsList" rotası - Haber listesi ekranı
         * 
         * Bu ekran, tüm haberlerin listelendiği ana ekrandır.
         * Kullanıcı buradan haber detaylarına geçiş yapabilir.
         */
        composable("newsList") {
            NewsScreen(navController = navController)  // Haber listesi ekranını göster
        }
        
        /**
         * "newsDetail/{article}" rotası - Haber detay ekranı
         * 
         * Bu ekran, seçilen haberin detaylarını gösterir.
         * Haber verisi URL parametresi olarak JSON formatında aktarılır.
         * 
         * @param route - Rota tanımı, {article} kısmı dinamik parametre
         * @param arguments - Rota parametrelerinin tanımı
         * @param navArgument - "article" parametresinin String tipinde olduğunu belirtir
         */
        composable(
            route = "newsDetail/{article}",  // Rota tanımı - {article} dinamik parametre
            arguments = listOf(navArgument("article") { type = NavType.StringType })  // "article" parametresi String tipinde
        ) { backStackEntry ->
            // URL'den gelen JSON verisini al
            val json = backStackEntry.arguments?.getString("article")
            
            // JSON verisini Article nesnesine dönüştür
            // Gson kütüphanesi kullanılarak JSON'dan Kotlin nesnesine çevrilir
            val article = Gson().fromJson(json, Article::class.java)

            // Haber detay ekranını göster
            DetailsScreen(article = article, navController = navController)
        }
    }
}