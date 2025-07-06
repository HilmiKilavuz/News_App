package com.kilavuzhilmi.news_app.viewmodel.functions

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

/**
 * FormatDate - Tarih formatlama işlemlerini yöneten utility sınıfı
 * 
 * Bu sınıf, API'den gelen ISO formatındaki tarih string'lerini kullanıcı dostu formata çevirir.
 * NewsAPI.org'dan gelen tarihler "2024-01-15T10:30:00Z" formatındadır ve bunları
 * "15 Oca 2024 - 10:30" gibi okunabilir formata dönüştürür.
 * 
 * @object - Kotlin'de singleton pattern'i uygular, sadece bir instance oluşturulur
 * Bu sayede sınıfın metodlarına sınıf adı ile doğrudan erişilebilir (FormatDate.formatDate())
 */
object FormatDate {
    
    /**
     * formatDate - ISO formatındaki tarih string'ini kullanıcı dostu formata çeviren fonksiyon
     * 
     * Bu fonksiyon, NewsAPI.org'dan gelen tarih verilerini işler ve kullanıcıların anlayabileceği
     * bir formata dönüştürür. Hata durumlarında güvenli bir şekilde "Tarih Yok" mesajı döndürür.
     * 
     * @param input - Formatlanacak tarih string'i (ISO formatında, null olabilir)
     * @return String - Formatlanmış tarih string'i veya hata durumunda "Tarih Yok"
     * 
     * Örnek dönüşümler:
     * - "2024-01-15T10:30:00Z" → "15 Oca 2024 - 10:30"
     * - "2024-12-25T14:45:00Z" → "25 Ara 2024 - 14:45"
     * - null → "Tarih Yok"
     * - "geçersiz-tarih" → "Tarih Yok"
     */
    fun formatDate(input: String?): String {
        return try {
            /**
             * ISO Tarih Formatını Parse Etme
             * 
             * Bu bölüm, API'den gelen ISO formatındaki tarihi parse eder.
             * SimpleDateFormat kullanılarak string'den Date nesnesine çevrilir.
             * 
             * @param "yyyy-MM-dd'T'HH:mm:ss'Z'" - ISO 8601 tarih formatı
             * - yyyy: 4 haneli yıl
             * - MM: 2 haneli ay
             * - dd: 2 haneli gün
             * - T: Tarih ve saat arasındaki ayırıcı
             * - HH: 24 saat formatında saat
             * - mm: dakika
             * - ss: saniye
             * - Z: UTC zaman dilimi göstergesi
             * 
             * @param Locale.getDefault() - Sistemin varsayılan dil ayarını kullan
             * @param TimeZone.getTimeZone("UTC") - Tarihin UTC zaman diliminde olduğunu belirt
             */
            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
            parser.timeZone = TimeZone.getTimeZone("UTC")  // UTC zaman dilimini kullan
            val date = parser.parse(input ?: "")  // String'i Date nesnesine çevir (null ise boş string kullan)
            
            /**
             * Kullanıcı Dostu Tarih Formatını Oluşturma
             * 
             * Bu bölüm, parse edilen Date nesnesini kullanıcı dostu formata çevirir.
             * 
             * @param "dd MMM yyyy - HH:mm" - Kullanıcı dostu format
             * - dd: 2 haneli gün
             * - MMM: 3 harfli ay adı (Oca, Şub, Mar, vb.)
             * - yyyy: 4 haneli yıl
             * - -: Ayırıcı
             * - HH:mm: 24 saat formatında saat:dakika
             * 
             * @param Locale.getDefault() - Sistemin varsayılan dil ayarını kullan
             * @param date ?: Date() - Eğer parse başarısız olursa şu anki tarihi kullan
             */
            val formatter = SimpleDateFormat("dd MMM yyyy - HH:mm", Locale.getDefault())
            formatter.format(date ?: Date())  // Date'i string'e çevir
            
        } catch (e: Exception) {
            /**
             * Hata Yönetimi
             * 
             * Eğer tarih parse edilirken herhangi bir hata oluşursa
             * (geçersiz format, null değer, vb.), güvenli bir şekilde
             * "Tarih Yok" mesajı döndürülür.
             * 
             * Bu sayede uygulama çökmek yerine kullanıcı dostu bir mesaj gösterir.
             */
            "Tarih Yok"  // Hata durumunda varsayılan mesaj
        }
    }
}