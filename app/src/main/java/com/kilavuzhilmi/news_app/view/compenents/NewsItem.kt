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

/**
 * NewsItem - Tek bir haberin görsel temsilini oluşturan bileşen
 * 
 * Bu bileşen, haber listesinde her bir haberi göstermek için kullanılır.
 * Haber kartı formatında tasarlanmıştır ve şu öğeleri içerir:
 * - Haber görseli (varsa, yoksa varsayılan görsel)
 * - Haber başlığı
 * - Haber açıklaması
 * - Yazar bilgisi
 * - Yayınlanma tarihi
 * 
 * @param article - Gösterilecek haber verisi (Article sınıfı)
 * @param onItemClick - Haber kartına tıklandığında çalışacak fonksiyon
 * @param onClick - İkinci tıklama fonksiyonu (şu an kullanılmıyor)
 */
@Composable
fun NewsItem(article: Article, onItemClick: () -> Unit, onClick: () -> Unit) {
    /**
     * Card - Material Design kart bileşeni
     * 
     * Bu kart, haberin tüm bilgilerini içeren ana konteyner görevi görür.
     * Gölge efekti ve yuvarlak köşeler ile modern bir görünüm sağlar.
     * 
     * @param modifier - Kartın boyut ve konum ayarları
     * @param elevation - Kartın gölge yüksekliği (4dp)
     * @param clickable - Kartın tıklanabilir olup olmadığı
     */
    Card(
        modifier = Modifier
            .fillMaxWidth()  // Genişliği ekranın tamamını kapla
            .defaultMinSize(minHeight = 300.dp)  // Minimum 300dp yükseklik
            .padding(8.dp)  // Dış boşluk (diğer kartlardan ayırmak için)
            .clickable(onClick = onItemClick),  // Tıklanabilir yap
        elevation = CardDefaults.cardElevation(4.dp)  // 4dp gölge efekti
    ) {
        /**
         * Column - Dikey düzen konteynerı
         * 
         * Bu Column, haberin tüm içeriğini dikey olarak sıralar:
         * 1. Haber görseli (en üstte)
         * 2. Haber içeriği (başlık, açıklama, yazar, tarih)
         */
        Column(modifier = Modifier.fillMaxWidth()) {
            
            /**
             * Haber Görseli Bölümü
             * 
             * Bu bölüm, haberle ilgili görseli gösterir.
             * Eğer haber görseli varsa onu gösterir, yoksa varsayılan görsel gösterir.
             */
            article.urlToImage?.let { imageUrl ->
                // Haber görseli varsa onu göster
                Image(
                    painter = rememberAsyncImagePainter(imageUrl),  // Coil ile görsel yükle
                    contentDescription = article.title,  // Erişilebilirlik için açıklama
                    modifier = Modifier
                        .fillMaxWidth()  // Genişliği tam kapla
                        .height(200.dp)  // Sabit 200dp yükseklik
                )
            } ?: run {
                // Haber görseli yoksa varsayılan görseli göster
                Image(
                    painter = rememberAsyncImagePainter(R.drawable.nophoto),  // Varsayılan görsel
                    contentDescription = article.title,  // Erişilebilirlik için açıklama
                    modifier = Modifier
                        .fillMaxWidth()  // Genişliği tam kapla
                        .height(200.dp)  // Sabit 200dp yükseklik
                )
            }

            /**
             * Haber İçeriği Bölümü
             * 
             * Bu bölüm, haberin metin içeriğini (başlık, açıklama, yazar, tarih) gösterir.
             * 8dp iç boşluk ile görselden ayrılır.
             */
            Column(modifier = Modifier.padding(8.dp)) {
                
                /**
                 * Haber Başlığı
                 * 
                 * Haber başlığını kalın font ile gösterir.
                 * Eğer başlık yoksa "Title: Unknown" gösterir.
                 */
                article.title.let { title ->
                    Text(
                        text = title,  // Haber başlığı
                        modifier = Modifier.padding(bottom = 4.dp),  // Alt boşluk
                        style = MaterialTheme.typography.titleMedium,  // Material Design başlık stili
                        fontWeight = FontWeight.Bold  // Kalın font
                    )
                } ?: run {
                    // Başlık yoksa varsayılan metin göster
                    Text(text = "Title: Unknown")
                }

                /**
                 * Haber Açıklaması
                 * 
                 * Haber açıklamasını gösterir, maksimum 4 satır.
                 * Eğer açıklama yoksa "Description: Unknown" gösterir.
                 */
                article.description?.let { description ->
                    Text(
                        text = description,  // Haber açıklaması
                        modifier = Modifier.padding(bottom = 4.dp),  // Alt boşluk
                        maxLines = 4,  // Maksimum 4 satır göster
                        style = MaterialTheme.typography.bodyMedium  // Material Design metin stili
                    )
                } ?: run {
                    // Açıklama yoksa varsayılan metin göster
                    Text(text = "Description: Unknown")
                }

                /**
                 * Yazar ve Tarih Bilgisi
                 * 
                 * Bu bölüm, yazar adı ve yayınlanma tarihini yan yana gösterir.
                 * Row kullanılarak yatay düzen oluşturulur.
                 */
                Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp)) {
                    
                    /**
                     * Yazar Bilgisi
                     * 
                     * Haber yazarının adını gösterir.
                     * Eğer yazar bilgisi yoksa gösterilmez.
                     */
                    article.author?.let { author ->
                        Text(
                            text = "Author: " + author,  // Yazar adı
                            modifier = Modifier.padding(end = 8.dp),  // Sağ boşluk (tarihten ayırmak için)
                            style = MaterialTheme.typography.bodySmall  // Küçük metin stili
                        )
                    }

                    /**
                     * Yayınlanma Tarihi
                     * 
                     * Haberin yayınlanma tarihini formatlanmış şekilde gösterir.
                     * FormatDate.formatDate() fonksiyonu kullanılarak tarih formatlanır.
                     */
                    article.publishedAt?.let { publishedAt ->
                        Text(
                            text = FormatDate.formatDate(publishedAt),  // Formatlanmış tarih
                            style = MaterialTheme.typography.bodySmall  // Küçük metin stili
                        )
                    }
                }
            }
        }
    }
}