package com.kilavuzhilmi.news_app.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * NewsResponse sınıfı - API'den gelen haber verilerinin ana konteynerı
 * 
 * Bu sınıf, NewsAPI.org servisinden gelen JSON yanıtını temsil eder.
 * API'den gelen tüm haberlerin listesini içerir.
 * 
 * @property articles - Haberlerin listesi. Her bir haber Article sınıfı tipinde
 */
data class NewResponse(
    val articles : List<Article>  // API'den gelen tüm haberlerin listesi
)

/**
 * Article sınıfı - Tek bir haberin tüm bilgilerini tutan veri modeli
 * 
 * Bu sınıf, her bir haberin tüm detaylarını (başlık, açıklama, resim, yazar vb.) saklar.
 * Parcelable interface'ini implement eder, bu sayede haber detayı ekranına veri aktarımı yapılabilir.
 * 
 * @Parcelize annotation'ı - Kotlin'in Parcelable implementasyonunu otomatik olarak oluşturur
 * Bu sayede manuel olarak Parcelable kodları yazmamıza gerek kalmaz.
 * 
 * @property author - Haberin yazarı (null olabilir çünkü bazı haberlerde yazar bilgisi olmayabilir)
 * @property title - Haberin başlığı (zorunlu alan)
 * @property description - Haberin kısa açıklaması (null olabilir)
 * @property urlToImage - Haberle ilgili görselin internet adresi (null olabilir)
 * @property url - Haberin tam metninin bulunduğu internet adresi (zorunlu alan)
 * @property publishedAt - Haberin yayınlanma tarihi (ISO formatında gelir, null olabilir)
 */
@Parcelize
data class Article(
    val author: String?,            // Yazar bilgisi - String? tipinde (null olabilir)
    val title: String,              // Başlık - String tipinde (zorunlu alan)
    val description: String?,       // Açıklama - String? tipinde (null olabilir)
    val urlToImage: String?,        // Görsel URL'si - String? tipinde (null olabilir)
    val url: String,                // Haber linki - String tipinde (zorunlu alan)
    val publishedAt: String?        // Yayınlanma tarihi (ISO formatında gelir) - String? tipinde (null olabilir)
): Parcelable  // Parcelable interface'i implement eder - bu sayede veri ekranlar arası aktarılabilir