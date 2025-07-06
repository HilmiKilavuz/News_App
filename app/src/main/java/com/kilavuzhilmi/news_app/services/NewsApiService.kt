package com.kilavuzhilmi.news_app.services

import com.kilavuzhilmi.news_app.model.NewResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * NewsApiService interface'i - NewsAPI.org servisi ile iletişim kurmak için kullanılan API arayüzü
 * 
 * Bu interface, Retrofit kütüphanesi kullanılarak HTTP isteklerini tanımlar.
 * NewsAPI.org servisinden haber verilerini çekmek için gerekli endpoint'leri içerir.
 * 
 * Retrofit, HTTP isteklerini yapmak için kullanılan modern bir kütüphanedir.
 * Bu interface sayesinde, API çağrıları Kotlin fonksiyonları olarak yazılabilir.
 * 
 * @interface - Kotlin'de interface tanımlar, sadece metod imzalarını içerir, implementasyonu yoktur
 */
interface NewsApiService{
    
    /**
     * getTopHeadlines fonksiyonu - En güncel haberleri getiren API endpoint'i
     * 
     * Bu fonksiyon, NewsAPI.org'un "top-headlines" endpoint'ini çağırır.
     * Belirtilen ülke ve kategori için en güncel haberleri döndürür.
     * 
     * @GET annotation'ı - HTTP GET isteği yapılacağını belirtir
     * @param "top-headlines" - API endpoint'inin yolu (base URL'e eklenir)
     * 
     * @Query annotation'ları - URL'ye query parametreleri ekler
     * @param country - Haberlerin hangi ülkeden geleceğini belirtir (varsayılan: "tr" - Türkiye)
     * @param category - Haber kategorisini belirtir (varsayılan: "general" - genel haberler)
     * @param apiKey - API erişim anahtarı (NewsAPI.org'dan alınan ücretsiz anahtar)
     * 
     * @return NewResponse - API'den dönen haber verilerinin konteynerı
     * @suspend - Bu fonksiyonun asenkron (coroutine) olarak çalışacağını belirtir
     */
    @GET("top-headlines")  // HTTP GET isteği yapılacak endpoint
    suspend fun getTopHeadlines(
        @Query("country")  // URL'ye "country" parametresi ekler
        country: String = "tr",  // Varsayılan değer Türkiye

        @Query("category")  // URL'ye "category" parametresi ekler
        category: String = "general",  // Varsayılan değer genel haberler

        @Query("apiKey")  // URL'ye "apiKey" parametresi ekler
        apiKey: String  // API erişim anahtarı (zorunlu parametre)

    ): NewResponse  // Dönen veri tipi NewResponse (haberlerin listesi)
}