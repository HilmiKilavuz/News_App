package com.kilavuzhilmi.news_app.view.compenents

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

/**
 * SearchHistoryList - Arama geçmişini gösteren ve yöneten bileşen
 * 
 * Bu bileşen, kullanıcının daha önce yaptığı aramaları listeler ve şu işlevleri sağlar:
 * - Arama geçmişini görüntüleme
 * - Geçmiş aramalara tıklayarak tekrar arama yapma
 * - Tekil aramaları silme
 * - Tüm arama geçmişini temizleme
 * 
 * @param history - Gösterilecek arama geçmişi listesi (String listesi)
 * @param onClick - Bir arama geçmişine tıklandığında çalışacak fonksiyon
 * @param onDelete - Tekil bir aramayı silmek için çalışacak fonksiyon
 * @param onClearAll - Tüm arama geçmişini temizlemek için çalışacak fonksiyon
 */
@Composable
fun SearchHistoryList(
    history: List<String>,  // Arama geçmişi listesi
    onClick: (String) -> Unit,  // Arama geçmişine tıklama fonksiyonu
    onDelete: (String) -> Unit,  // Tekil silme fonksiyonu
    onClearAll: () -> Unit  // Tümünü silme fonksiyonu
) {

    /**
     * Column - Ana konteyner
     * 
     * Bu Column, arama geçmişinin tüm içeriğini dikey olarak düzenler:
     * 1. Başlık ve "Temizle" butonu (yan yana)
     * 2. Arama geçmişi listesi (her biri ayrı satır)
     */
    Column(modifier = Modifier.padding(horizontal = 8.dp)) {

        /**
         * Başlık ve Temizle Butonu Satırı
         * 
         * Bu Row, "Search History" başlığını ve "Clear All History" butonunu yan yana gösterir.
         * SpaceBetween kullanılarak başlık sola, buton sağa hizalanır.
         */
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            // "Search History" başlığı
            Text(
                text = "Search History", 
                fontWeight = FontWeight.Bold  // Kalın font
            )
            
            // "Clear All History" butonu
            TextButton(onClick = onClearAll) {
                Text(
                    text = "Clear All History", 
                    color = Color.Red  // Kırmızı renk (dikkat çekici)
                )
            }
        }
        
        /**
         * Arama Geçmişi Listesi
         * 
         * Bu bölüm, her bir arama geçmişini ayrı satırlarda gösterir.
         * forEach kullanılarak history listesindeki her öğe için bir satır oluşturulur.
         */
        history.forEach { item ->
            /**
             * Tekil Arama Geçmişi Satırı
             * 
             * Her satır, arama metnini ve silme butonunu yan yana gösterir.
             * SpaceBetween kullanılarak metin sola, silme butonu sağa hizalanır.
             */
            Row(
                modifier = Modifier
                    .fillMaxWidth()  // Genişliği tam kapla
                    .clickable { onClick(item) }  // Satıra tıklandığında aramayı tekrarla
                    .padding(vertical = 4.dp),  // Dikey boşluk (satırlar arası)
                horizontalArrangement = Arrangement.SpaceBetween  // İçerikleri sağa ve sola yasla
            ) {
                // Arama metni
                Text(text = item)
                
                // Silme butonu
                IconButton(onClick = { onDelete(item) }) {
                    Icon(
                        Icons.Default.Delete,  // Çöp kutusu ikonu
                        contentDescription = "Sil"  // Erişilebilirlik açıklaması
                    )
                }
            }
        }
    }
}