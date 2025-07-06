package com.kilavuzhilmi.news_app.services

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * RetrofitInstance object'i - Retrofit HTTP istemcisinin yapılandırıldığı singleton nesne
 * 
 * Bu object, NewsAPI.org servisi ile iletişim kurmak için gerekli olan Retrofit istemcisini oluşturur.
 * Singleton pattern kullanır, yani uygulama boyunca sadece bir kez oluşturulur ve tekrar kullanılır.
 * 
 * Retrofit, HTTP API çağrılarını yapmak için kullanılan modern bir kütüphanedir.
 * Bu object sayesinde, API çağrıları için gerekli tüm yapılandırma tek bir yerde toplanmıştır.
 * 
 * @object - Kotlin'de singleton pattern'i uygular, sadece bir instance oluşturulur
 */
object RetrofitInstance {
    
    /**
     * api property'si - NewsApiService interface'inin implementasyonu
     * 
     * Bu property, NewsApiService interface'ini implement eden Retrofit istemcisini döndürür.
     * lazy keyword'ü sayesinde, sadece ilk kullanımda oluşturulur (lazy initialization).
     * 
     * @lazy - Kotlin'de lazy initialization sağlar, sadece gerektiğinde oluşturulur
     * @by lazy - Property'nin lazy olarak başlatılacağını belirtir
     */
    val api: NewsApiService by lazy {
        
        /**
         * HttpLoggingInterceptor - HTTP isteklerini ve yanıtlarını loglamak için kullanılan interceptor
         * 
         * Bu interceptor, API çağrılarının detaylarını (URL, headers, body, response) konsola yazdırır.
         * Debug aşamasında API çağrılarını takip etmek için çok faydalıdır.
         * 
         * @level - Log seviyesini belirler (BODY = tüm detayları gösterir)
         */
        val logging = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY  // Tüm HTTP detaylarını logla
        }
        
        /**
         * OkHttpClient - HTTP isteklerini yapmak için kullanılan HTTP istemcisi
         * 
         * Bu istemci, Retrofit'in altında çalışan HTTP kütüphanesidir.
         * Timeout değerleri, interceptor'lar ve diğer HTTP ayarları burada yapılır.
         * 
         * @Builder pattern - OkHttpClient'ı yapılandırmak için kullanılan pattern
         * @addInterceptor - HTTP isteklerine interceptor ekler (logging için)
         * @connectTimeout - Sunucuya bağlanma zaman aşımı (30 saniye)
         * @readTimeout - Veri okuma zaman aşımı (30 saniye)
         */
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)  // Logging interceptor'ını ekle
            .connectTimeout(30, TimeUnit.SECONDS)  // Bağlantı zaman aşımı: 30 saniye
            .readTimeout(30, TimeUnit.SECONDS)  // Okuma zaman aşımı: 30 saniye
            .build()  // OkHttpClient'ı oluştur
            
        /**
         * Retrofit.Builder - Retrofit istemcisini yapılandırmak için kullanılan builder
         * 
         * Bu builder, Retrofit istemcisinin tüm ayarlarını yapar:
         * - Base URL (API'nin ana adresi)
         * - HTTP client (OkHttpClient)
         * - JSON converter (GsonConverterFactory)
         * 
         * @baseUrl - API'nin ana adresi (tüm endpoint'ler bu adrese eklenir)
         * @client - HTTP isteklerini yapacak OkHttpClient
         * @addConverterFactory - JSON verilerini Kotlin nesnelerine çeviren converter
         * @create - NewsApiService interface'inin implementasyonunu oluşturur
         */
        Retrofit.Builder()
            .baseUrl("https://newsapi.org/v2/")  // NewsAPI.org'un API adresi
            .client(client)  // Yapılandırılmış OkHttpClient'ı kullan
            .addConverterFactory(GsonConverterFactory.create())  // JSON'dan Kotlin nesnesine çevirme
            .build()  // Retrofit istemcisini oluştur
            .create(NewsApiService::class.java)  // NewsApiService interface'ini implement et
    }
}