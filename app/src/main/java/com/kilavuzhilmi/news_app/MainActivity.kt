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

/**
 * MainActivity - Uygulamanın ana giriş noktası
 * 
 * Bu sınıf, Android uygulamasının başlangıç noktasıdır ve şu işlevleri sağlar:
 * - Uygulama başlatma ve yapılandırma
 * - Jetpack Compose UI'ın başlatılması
 * - Navigasyon sisteminin kurulması
 * - Tema ve stil ayarlarının uygulanması
 * 
 * @param ComponentActivity - Android'in modern Activity sınıfı
 * Bu sınıf, Jetpack Compose ile uyumlu olarak tasarlanmıştır.
 */
class MainActivity : ComponentActivity() {
    
    /**
     * onCreate - Activity oluşturulduğunda çağrılan yaşam döngüsü metodu
     * 
     * Bu metod, uygulama başlatıldığında bir kez çalışır ve şu işlemleri yapar:
     * 1. Activity'nin temel yapılandırmasını yapar
     * 2. Edge-to-edge desteğini etkinleştirir
     * 3. Jetpack Compose UI'ı başlatır
     * 4. Navigasyon sistemini kurar
     * 
     * @param savedInstanceState - Activity'nin önceki durumu (varsa)
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)  // Üst sınıfın onCreate metodunu çağır
        
        /**
         * enableEdgeToEdge - Edge-to-edge ekran desteğini etkinleştirir
         * 
         * Bu fonksiyon, uygulamanın sistem çubuklarının (status bar, navigation bar)
         * arkasına kadar uzanmasını sağlar. Modern Android tasarım trendlerine uygun
         * olarak tam ekran deneyimi sunar.
         */
        enableEdgeToEdge()
        
        /**
         * setContent - Jetpack Compose UI'ını başlatır
         * 
         * Bu fonksiyon, Activity'nin içeriğini Jetpack Compose ile tanımlar.
         * Compose, Android'in modern UI toolkit'idir ve declarative (bildirimsel)
         * programlama yaklaşımı kullanır.
         */
        setContent {
            // Navigasyon kontrolcüsünü oluştur
            val navController = rememberNavController()
            
            /**
             * News_AppTheme - Uygulamanın tema yapılandırması
             * 
             * Bu tema, uygulamanın görsel kimliğini tanımlar:
             * - Renk paleti
             * - Tipografi (yazı tipleri)
             * - Şekil ve boyutlar
             * - Karanlık/aydınlık tema desteği
             */
            News_AppTheme {
                /**
                 * NavGraph - Uygulamanın navigasyon yapısı
                 * 
                 * Bu bileşen, uygulamadaki tüm ekranların nasıl birbirine bağlandığını
                 * ve aralarında nasıl geçiş yapılacağını tanımlar.
                 * 
                 * @param navController - Ekranlar arası geçişleri yöneten kontrolcü
                 * @param startDestination - Uygulamanın başlangıç ekranı ("newsList")
                 */
                NavGraph(
                    navController = navController,  // Navigasyon kontrolcüsü
                    startDestination = "newsList"  // Başlangıç ekranı
                )
            }
        }
    }
}

/**
 * GreetingPreview - Preview fonksiyonu (geliştirme aracı)
 * 
 * Bu fonksiyon, Android Studio'da UI bileşenlerini önizlemek için kullanılır.
 * Geliştirici, kod yazarken UI'ın nasıl görüneceğini görebilir.
 * 
 * @param Preview - Compose Preview annotation'ı
 * @param showBackground - Arka planın gösterilip gösterilmeyeceği
 */
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    // Şu anda boş - gelecekte UI önizlemesi için kullanılabilir
}