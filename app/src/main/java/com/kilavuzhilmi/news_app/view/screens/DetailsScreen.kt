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

/**
 * DetailsScreen - Haber detaylarını gösteren ekran
 * 
 * Bu ekran, seçilen haberin tüm detaylarını gösterir ve şu özellikleri içerir:
 * - Haber görseli (büyük boyutta)
 * - Haber başlığı (kalın font ile)
 * - Haber açıklaması (tam metin)
 * - Yazar bilgisi
 * - Yayınlanma tarihi
 * - "Haberi Oku" butonu (haberin tam metnini web'de açar)
 * 
 * @param navController - Ekranlar arası geçiş için navigasyon kontrolcüsü
 * @param viewModel - Bu ekranın veri yönetimi için ViewModel (şu an boş)
 * @param article - Gösterilecek haber verisi
 */
@OptIn(ExperimentalMaterial3Api::class)  // TopAppBar için gerekli
@Composable
fun DetailsScreen(
    navController: NavController,  // Navigasyon kontrolcüsü
    viewModel: DetailsScreenViewModel = viewModel(),  // ViewModel (şu an kullanılmıyor)
    article: Article  // Gösterilecek haber
) {
    /**
     * Scaffold - Material Design sayfa yapısı
     * 
     * Bu bileşen, ekranın temel yapısını oluşturur:
     * - TopAppBar (üst çubuk)
     * - İçerik alanı (body)
     * 
     * @param topBar - Ekranın üst kısmındaki çubuk
     */
    Scaffold(topBar = {
        /**
         * TopAppBar - Ekranın üst çubuğu
         * 
         * Bu çubuk, ekran başlığını ve geri dönüş butonunu içerir.
         * Material Design 3 standartlarına uygun olarak tasarlanmıştır.
         */
        TopAppBar(
            title = { Text("Haber Detayı") },  // Ekran başlığı
            navigationIcon = {
                /**
                 * Geri Dönüş Butonu
                 * 
                 * Bu buton, kullanıcıyı önceki ekrana geri döndürür.
                 * popBackStack() fonksiyonu ile navigasyon yığınından bir önceki ekrana döner.
                 */
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Rounded.Close,  // Kapatma ikonu
                        contentDescription = "Geri"  // Erişilebilirlik açıklaması
                    )
                }
            }
        )
    }) { innerPadding ->
        /**
         * Ana İçerik Alanı
         * 
         * Bu Column, haberin tüm detaylarını dikey olarak sıralar.
         * innerPadding, TopAppBar'ın altında kalan alanı belirler.
         */
        Column(modifier = Modifier.padding(innerPadding)) {
            
            // Android Context'ini al (Intent için gerekli)
            val context = LocalContext.current
            
            /**
             * Haber Görseli Bölümü
             * 
             * Bu bölüm, haberle ilgili görseli büyük boyutta gösterir.
             * Eğer görsel yoksa "Image: Unknown" metni gösterir.
             */
            article.urlToImage.let { image ->
                Image(
                    painter = rememberAsyncImagePainter(image),  // Coil ile görsel yükle
                    contentDescription = article.title,  // Erişilebilirlik açıklaması
                    modifier = Modifier
                        .padding(8.dp)  // Dış boşluk
                        .fillMaxWidth()  // Genişliği tam kapla
                        .padding(bottom = 10.dp)  // Alt boşluk
                )
            } ?: run {
                // Görsel yoksa varsayılan metin göster
                Text(text = "Image: Unknown")
            }

            /**
             * Haber Başlığı
             * 
             * Haber başlığını büyük ve kalın font ile gösterir.
             * Eğer başlık yoksa "Title: Unknown" gösterir.
             */
            article.title.let { title ->
                Text(
                    text = title,  // Haber başlığı
                    modifier = Modifier
                        .padding(8.dp)  // Dış boşluk
                        .padding(bottom = 10.dp),  // Alt boşluk
                    style = MaterialTheme.typography.titleLarge,  // Büyük başlık stili
                    fontWeight = FontWeight.Bold  // Kalın font
                )
            } ?: run {
                // Başlık yoksa varsayılan metin göster
                Text(text = "Title: Unknown")
            }
            
            /**
             * Haber Açıklaması
             * 
             * Haber açıklamasını tam metin olarak gösterir.
             * Eğer açıklama yoksa "Description: Unknown" gösterir.
             */
            article.description.let { description ->
                Text(
                    text = description.toString(),  // Haber açıklaması
                    modifier = Modifier
                        .padding(8.dp)  // Dış boşluk
                        .padding(bottom = 10.dp),  // Alt boşluk
                    style = MaterialTheme.typography.bodyMedium  // Normal metin stili
                )
            } ?: run {
                // Açıklama yoksa varsayılan metin göster
                Text(text = "Description: Unknown")
            }
            
            /**
             * Yazar ve Tarih Bilgisi Satırı
             * 
             * Bu Row, yazar adı ve yayınlanma tarihini yan yana gösterir.
             * SpaceBetween kullanılarak yazar sola, tarih sağa hizalanır.
             */
            Row(horizontalArrangement = Arrangement.SpaceBetween) {
                
                /**
                 * Yazar Bilgisi
                 * 
                 * Haber yazarının adını gösterir.
                 * Eğer yazar bilgisi yoksa "Author: Unknown" gösterir.
                 */
                article.author.let { author ->
                    Text(
                        text = "Author: " + author.toString(),  // Yazar adı
                        modifier = Modifier
                            .padding(8.dp)  // Dış boşluk
                            .padding(bottom = 10.dp),  // Alt boşluk
                        style = MaterialTheme.typography.bodyMedium  // Normal metin stili
                    )
                } ?: run {
                    // Yazar yoksa varsayılan metin göster
                    Text(text = "Author: Unknown")
                }

                /**
                 * Yayınlanma Tarihi
                 * 
                 * Haberin yayınlanma tarihini gösterir.
                 * Eğer tarih bilgisi yoksa "Published At: Unknown" gösterir.
                 */
                article.publishedAt.let { publishedAt ->
                    Text(
                        text = publishedAt.toString(),  // Yayınlanma tarihi
                        modifier = Modifier
                            .padding(8.dp)  // Dış boşluk
                            .padding(bottom = 10.dp)  // Alt boşluk
                    )
                } ?: run {
                    // Tarih yoksa varsayılan metin göster
                    Text(text = "Published At: Unknown")
                }
            }
            
            /**
             * "Haberi Oku" Butonu Satırı
             * 
             * Bu Row, "Haberi Oku" butonunu ekranın ortasında gösterir.
             * Buton, haberin tam metnini web tarayıcısında açar.
             */
            Row(
                modifier = Modifier.fillMaxWidth(),  // Genişliği tam kapla
                horizontalArrangement = Arrangement.Center  // Butonu ortala
            ) {
                /**
                 * Haber URL'si Kontrolü ve Buton
                 * 
                 * Eğer haber URL'si varsa "Haberi Oku" butonunu gösterir.
                 * Butona tıklandığında Intent ile web tarayıcısında haber açılır.
                 */
                article.url.let { url ->
                    OutlinedGlowButton(
                        text = "Haberi Oku",  // Buton metni
                        modifier = Modifier.padding(top = 10.dp),  // Üst boşluk
                        onClick = {
                            // Haber URL'sini web tarayıcısında aç
                            article.url.let { url ->
                                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))  // Web tarayıcısı Intent'i
                                context.startActivity(intent)  // Intent'i başlat
                            }
                        }
                    )
                } ?: run {
                    // URL yoksa varsayılan metin göster
                    Text(text = "Url: Unknown")
                }
            }
        }
    }
}

