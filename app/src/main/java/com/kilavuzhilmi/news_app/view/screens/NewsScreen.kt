package com.kilavuzhilmi.news_app.view.screens

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction

/**
 * NewsScreen - Ana haber listesi ekranı
 * 
 * Bu ekran, uygulamanın ana ekranıdır ve şu özellikleri içerir:
 * - Haber arama kutusu
 * - Arama geçmişi listesi
 * - Haber listesi (LazyColumn ile)
 * - Yükleme durumu göstergesi
 * - Hata durumu mesajı
 * - Boş durum mesajı
 * 
 * @param navController - Ekranlar arası geçiş için navigasyon kontrolcüsü
 * @param viewModel - Bu ekranın veri yönetimi için ViewModel
 */
@Composable
fun NewsScreen(
    navController: NavController,  // Navigasyon kontrolcüsü
    viewModel: NewsViewModel = viewModel()  // ViewModel
) {
    // ViewModel'den gelen verileri al
    val articles = viewModel.newsList  // Haber listesi
    val errorMessage = viewModel.errorMessage  // Hata mesajı
    val isLoading = viewModel.isLoading  // Yükleme durumu
    val history by viewModel.searchHistory.collectAsState()  // Arama geçmişi (StateFlow'dan)
    val filteredArticles = viewModel.filterArticles(articles)  // Filtrelenmiş haberler

    // Arama ile ilgili state'ler
    val searchQuery by viewModel.searchQuery.collectAsState()  // Arama sorgusu (StateFlow'dan)
    val searchArticles by viewModel.filteredArticles.collectAsState()  // Arama sonuçları (StateFlow'dan)

    /**
     * Durum Kontrolü ve Uygun UI Gösterimi
     * 
     * Bu when bloğu, uygulamanın mevcut durumuna göre uygun UI'ı gösterir:
     * 1. Yükleniyor durumu
     * 2. Hata durumu
     * 3. Boş durum
     * 4. Normal durum (haberler var)
     */
    when {
        /**
         * Yükleniyor Durumu
         * 
         * API'den veri çekilirken gösterilen ekran.
         * Ekranın ortasında dönen bir yükleme göstergesi gösterir.
         */
        isLoading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()  // Dönen yükleme göstergesi
            }
        }

        /**
         * Hata Durumu
         * 
         * API çağrısı başarısız olduğunda gösterilen ekran.
         * Hata mesajını ve "Tekrar Dene" butonunu gösterir.
         */
        errorMessage.isNotEmpty() -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = errorMessage)  // Hata mesajı
                    Button(
                        onClick = { viewModel.fetchNews() },  // Haberleri tekrar çek
                        modifier = Modifier.padding(top = 8.dp)  // Üst boşluk
                    ) {
                        Text("Tekrar Dene")  // Buton metni
                    }
                }
            }
        }

        /**
         * Boş Durum
         * 
         * Hiç haber bulunamadığında gösterilen ekran.
         * "Haber bulunamadı" mesajını gösterir.
         */
        articles.isEmpty() -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "Haber bulunamadı")  // Boş durum mesajı
            }
        }

        /**
         * Normal Durum - Haberler Var
         * 
         * Haberler başarıyla yüklendiğinde gösterilen ana ekran.
         * Arama kutusu, arama geçmişi ve haber listesini içerir.
         */
        else -> {
            /**
             * Scaffold - Ana sayfa yapısı
             * 
             * Bu bileşen, ekranın temel yapısını oluşturur.
             * İçerik alanını (body) tanımlar.
             */
            Scaffold { innerPadding ->
                /**
                 * Ana İçerik Alanı
                 * 
                 * Bu Column, ekranın tüm içeriğini dikey olarak düzenler:
                 * 1. Arama kutusu
                 * 2. Arama geçmişi (varsa)
                 * 3. Haber listesi
                 */
                Column {
                    // Klavye yönetimi için focus manager
                    val focusManager = LocalFocusManager.current
                    println("GEÇMİŞ: $history")  // Debug için arama geçmişini yazdır

                    /**
                     * Arama Kutusu
                     * 
                     * Kullanıcının haber araması yapabileceği metin kutusu.
                     * Enter tuşuna basıldığında arama yapar ve klavyeyi kapatır.
                     */
                    OutlinedTextField(
                        value = searchQuery,  // Arama sorgusu
                        onValueChange = { viewModel.setSearchQuery(it) },  // Sorgu değiştiğinde
                        label = { Text("Haber Ara") },  // Arama kutusu etiketi
                        modifier = Modifier
                            .fillMaxWidth()  // Genişliği tam kapla
                            .padding(8.dp)  // Dış boşluk
                            .padding(top = 30.dp),  // Üst boşluk (status bar'dan kaçınmak için)
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),  // Enter tuşu "Ara" olarak ayarla
                        keyboardActions = KeyboardActions(onSearch = {
                            viewModel.addSearchToHistory(query = searchQuery)  // Aramayı geçmişe ekle
                            focusManager.clearFocus()  // Klavyeyi kapat
                        })
                    )
                    
                    /**
                     * Arama Geçmişi Bölümü
                     * 
                     * Arama kutusu boşsa ve geçmiş varsa gösterilir.
                     * Kullanıcının önceki aramalarını listeler.
                     */
                    if (searchQuery.isEmpty() && history.isNotEmpty()) {
                        Column(modifier = Modifier.padding(horizontal = 8.dp)) {
                            /**
                             * Arama Geçmişi Başlığı ve Temizle Butonu
                             * 
                             * "Geçmiş Aramalar" başlığını ve "Temizle" butonunu yan yana gösterir.
                             */
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Geçmiş Aramalar", fontWeight = FontWeight.Bold)  // Başlık
                                TextButton(onClick = {
                                    viewModel.clearSearchHistory()  // Geçmişi temizle
                                }) {
                                    Text("Temizle", color = Color.Red)  // Kırmızı temizle butonu
                                }
                            }

                            /**
                             * Arama Geçmişi Listesi
                             * 
                             * Her bir geçmiş aramayı ayrı satırda gösterir.
                             * Tıklanabilir ve silinebilir.
                             */
                            history.forEach { item ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()  // Genişliği tam kapla
                                        .clickable { viewModel.onSearchQueryChange(item) }  // Tıklandığında aramayı tekrarla
                                        .padding(vertical = 4.dp),  // Dikey boşluk
                                    horizontalArrangement = Arrangement.SpaceBetween  // İçerikleri sağa ve sola yasla
                                ) {
                                    Text(text = item)  // Arama metni
                                    IconButton(onClick = { viewModel.removeSearchHistory(item) }) {  // Silme butonu
                                        Icon(Icons.Default.Delete, contentDescription = "Sil")  // Çöp kutusu ikonu
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))  // Boşluk

                    /**
                     * Haber Listesi
                     * 
                     * LazyColumn kullanılarak performanslı bir şekilde haberleri listeler.
                     * Sadece görünür öğeler render edilir.
                     */
                    LazyColumn(
                        modifier = Modifier
                            .padding(innerPadding)  // Scaffold'dan gelen padding
                            .fillMaxSize()  // Kalan alanı tamamen kapla
                    ) {
                        /**
                         * Arama Durumuna Göre Haber Gösterimi
                         * 
                         * Eğer arama yapılmıyorsa tüm haberleri, arama yapılıyorsa filtrelenmiş haberleri gösterir.
                         */
                        if (searchQuery.isEmpty()) {
                            // Arama yapılmıyorsa tüm haberleri göster
                            items(articles) { article ->
                                NewsItem(
                                    article = article,  // Haber verisi
                                    onItemClick = {
                                        // Haber detayına git
                                        val gson = Gson()  // JSON dönüştürücü
                                        val articleJson = Uri.encode(gson.toJson(article))  // Haberi JSON'a çevir ve URL encode et
                                        navController.navigate("newsDetail/$articleJson")  // Detay ekranına git
                                    }
                                ) {}  // İkinci onClick fonksiyonu (kullanılmıyor)
                            }
                        } else {
                            // Arama yapılıyorsa filtrelenmiş haberleri göster
                            items(searchArticles) { article ->
                                NewsItem(
                                    article = article,  // Filtrelenmiş haber verisi
                                    onItemClick = {
                                        // Haber detayına git
                                        val gson = Gson()  // JSON dönüştürücü
                                        val articleJson = Uri.encode(gson.toJson(article))  // Haberi JSON'a çevir ve URL encode et
                                        navController.navigate("newsDetail/$articleJson")  // Detay ekranına git
                                    }
                                ) {
                                    // İkinci onClick fonksiyonu (şu an kullanılmıyor)
                                    navController.navigate(
                                        "detail/${
                                            Uri.encode(
                                                Gson().toJson(article)  // Haberi JSON'a çevir
                                            )
                                        }"
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}




