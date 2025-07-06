package com.kilavuzhilmi.news_app.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kilavuzhilmi.news_app.BuildConfig

import com.kilavuzhilmi.news_app.model.Article
import com.kilavuzhilmi.news_app.services.RetrofitInstance
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

/**
 * NewsViewModel - Ana haber ekranının veri yönetimi ve iş mantığı için ViewModel
 * 
 * Bu ViewModel, NewsScreen'in tüm veri yönetimini ve API etkileşimlerini sağlar.
 * MVVM (Model-View-ViewModel) mimarisinin ViewModel katmanını temsil eder.
 * 
 * Temel işlevleri:
 * - NewsAPI.org'dan haber verilerini çekme
 * - Haber arama işlevselliği
 * - Arama geçmişi yönetimi
 * - UI durumu yönetimi (yükleme, hata, başarı)
 * - Filtrelenmiş haber listesi oluşturma
 * 
 * @param ViewModel - Android Architecture Components'ten gelen temel ViewModel sınıfı
 * @param viewModelScope - Coroutine scope'u (ViewModel yaşam döngüsüne bağlı)
 */
class NewsViewModel : ViewModel() {
    
    /**
     * Arama Geçmişi State Yönetimi
     * 
     * StateFlow, reactive programming için kullanılan modern bir state yönetim sistemi.
     * MutableStateFlow, değeri değiştirilebilen StateFlow'dur.
     * asStateFlow(), MutableStateFlow'u salt okunur StateFlow'a çevirir.
     */
    private val _searchHistory = MutableStateFlow<List<String>>(emptyList())  // Arama geçmişi (değiştirilebilir)
    val searchHistory = _searchHistory.asStateFlow()  // Arama geçmişi (salt okunur)

    /**
     * Haber Listesi State Yönetimi
     * 
     * API'den gelen haberlerin listesini tutar.
     * StateFlow kullanılarak reactive bir şekilde UI'a bildirim yapılır.
     */
    private val _articles = MutableStateFlow<List<Article>>(emptyList())  // Haber listesi (değiştirilebilir)
    val articles: StateFlow<List<Article>> = _articles  // Haber listesi (salt okunur)

    /**
     * Arama Sorgusu State Yönetimi
     * 
     * Kullanıcının arama kutusuna yazdığı metni tutar.
     * Bu değer değiştiğinde otomatik olarak filtreleme yapılır.
     */
    private val _searchQuery = MutableStateFlow("")  // Arama sorgusu (değiştirilebilir)
    val searchQuery = _searchQuery.asStateFlow()  // Arama sorgusu (salt okunur)

    /**
     * UI Durumu Yönetimi
     * 
     * Bu değişkenler, UI'ın farklı durumlarını (yükleme, hata, veri) yönetir.
     * mutableStateOf kullanılarak Compose UI'ın bu değişiklikleri otomatik olarak algılaması sağlanır.
     */
    var newsList by mutableStateOf<List<Article>>(emptyList())  // Haber listesi (UI için)
    var errorMessage by mutableStateOf("")  // Hata mesajı
    var isLoading by mutableStateOf(true)  // Yükleme durumu

    /**
     * Constructor - ViewModel oluşturulduğunda otomatik olarak haberleri çek
     * 
     * init bloğu, ViewModel oluşturulduğunda bir kez çalışır.
     * Bu sayede uygulama açıldığında otomatik olarak haberler yüklenir.
     */
    init {
        fetchNews()  // Haberleri çek
    }

    /**
     * Arama Geçmişi Yönetim Fonksiyonları
     * 
     * Bu fonksiyonlar, kullanıcının arama geçmişini yönetir.
     */

    /**
     * clearSearchHistory - Tüm arama geçmişini temizler
     * 
     * Kullanıcı "Temizle" butonuna tıkladığında çağrılır.
     * Arama geçmişini boş liste yapar.
     */
    fun clearSearchHistory() {
        _searchHistory.value = emptyList()  // Geçmişi temizle
    }
    
    /**
     * removeSearchHistory - Belirli bir aramayı geçmişten siler
     * 
     * @param query - Silinecek arama sorgusu
     * 
     * Kullanıcı arama geçmişindeki silme butonuna tıkladığında çağrılır.
     * filterNot kullanılarak belirtilen sorguyu listeden çıkarır.
     */
    fun removeSearchHistory(query: String) {
        _searchHistory.value = _searchHistory.value.filterNot { it == query }  // Belirtilen sorguyu sil
    }
    
    /**
     * onSearchQueryChange - Arama sorgusunu günceller
     * 
     * @param newQuery - Yeni arama sorgusu
     * 
     * Kullanıcı arama kutusuna yazdığında veya geçmiş aramaya tıkladığında çağrılır.
     * Bu değişiklik otomatik olarak filtreleme işlemini tetikler.
     */
    fun onSearchQueryChange(newQuery: String) {
        _searchQuery.value = newQuery  // Arama sorgusunu güncelle
    }

    /**
     * addSearchToHistory - Arama geçmişine yeni sorgu ekler
     * 
     * @param query - Eklenecek arama sorgusu
     * 
     * Kullanıcı arama yaptığında çağrılır.
     * Boş olmayan ve daha önce eklenmemiş sorguları geçmişe ekler.
     * Yeni aramalar listenin başına eklenir (en son arama en üstte).
     */
    fun addSearchToHistory(query: String) {
        if (query.isNotBlank() && !_searchHistory.value.contains(query)) {  // Boş değilse ve daha önce eklenmemişse
            _searchHistory.value = listOf(query) + _searchHistory.value  // Yeni sorguyu listenin başına ekle
        }
    }

    /**
     * Filtrelenmiş Haber Listesi
     * 
     * Bu StateFlow, arama sorgusuna göre otomatik olarak filtrelenmiş haberleri döndürür.
     * combine operatörü kullanılarak _articles ve _searchQuery birleştirilir.
     * 
     * @param combine - İki StateFlow'u birleştiren operatör
     * @param stateIn - StateFlow'u belirli bir scope'ta başlatan operatör
     * @param SharingStarted.WhileSubscribed - Abonelik sürdüğü sürece aktif tutar
     */
    val filteredArticles: StateFlow<List<Article>> =
        combine(_articles, _searchQuery) { articles, query ->
            if (query.isBlank()) articles  // Sorgu boşsa tüm haberleri döndür
            else articles.filter { it.title.contains(query, ignoreCase = true) }  // Sorguya göre filtrele
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    /**
     * filterArticles - Manuel filtreleme fonksiyonu
     * 
     * @param allArticles - Filtrelenecek haber listesi
     * @return List<Article> - Filtrelenmiş haber listesi
     * 
     * Bu fonksiyon, verilen haber listesini mevcut arama sorgusuna göre filtreler.
     * Şu anda kullanılmıyor ama gelecekte kullanılabilir.
     */
    fun filterArticles(allArticles: List<Article>): List<Article> {
        val query = searchQuery.value.lowercase()  // Sorguyu küçük harfe çevir
        return if (query.isBlank()) {
            allArticles  // Sorgu boşsa tüm haberleri döndür
        } else {
            allArticles.filter { it.title.contains(query, ignoreCase = true) }  // Başlığa göre filtrele
        }
    }

    /**
     * setSearchQuery - Arama sorgusunu ayarlar
     * 
     * @param query - Yeni arama sorgusu
     * 
     * UI'dan arama sorgusunu güncellemek için kullanılır.
     * Bu değişiklik otomatik olarak filtreleme işlemini tetikler.
     */
    fun setSearchQuery(query: String) {
        _searchQuery.value = query  // Arama sorgusunu güncelle
    }

    /**
     * getTopHeadlines - API'den haberleri çeken fonksiyon
     * 
     * @param apiKey - NewsAPI.org API anahtarı (varsayılan: BuildConfig'den alınır)
     * 
     * Bu fonksiyon, NewsAPI.org'dan en güncel haberleri çeker.
     * Şu anda kullanılmıyor ama gelecekte kullanılabilir.
     */
    fun getTopHeadlines(apiKey: String = BuildConfig.NEWS_API_KEY) {
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getTopHeadlines(apiKey = apiKey)  // API çağrısı
                _articles.value = response.articles  // Haberleri güncelle
            } catch (e: Exception) {
                // Hata işleme (şu anda boş)
            }
        }
    }

    /**
     * fetchNews - Ana haber çekme fonksiyonu
     * 
     * Bu fonksiyon, uygulama açıldığında ve "Tekrar Dene" butonuna basıldığında çağrılır.
     * NewsAPI.org'dan ABD'den en güncel haberleri çeker.
     * 
     * İşlem adımları:
     * 1. Yükleme durumunu true yapar
     * 2. API çağrısı yapar
     * 3. Başarılı olursa haberleri günceller
     * 4. Hata olursa hata mesajını ayarlar
     * 5. Yükleme durumunu false yapar
     */
    fun fetchNews() {
        viewModelScope.launch {
            isLoading = true  // Yükleme başladı
            try {
                Log.d("NewsViewModel", "Fetching news...")  // Debug log
                Log.d("NewsViewModel", "BuildConfig API Key: ${BuildConfig.NEWS_API_KEY}")  // API anahtarını logla

                // API çağrısı - ABD'den genel haberler
                val response = RetrofitInstance.api.getTopHeadlines(
                    country = "us",  // ABD'den haberler
                    apiKey = BuildConfig.NEWS_API_KEY  // API anahtarı
                )
                Log.d("NewsViewModel", "News received: ${response.articles.size} articles")  // Haber sayısını logla

                if (response.articles.isNotEmpty()) {
                    // Başarılı durum - haberler var
                    _articles.value = response.articles  // StateFlow'u güncelle
                    newsList = response.articles  // UI state'ini güncelle
                    errorMessage = ""  // Hata mesajını temizle
                    Log.d("NewsViewModel", "First article title: ${response.articles[0].title}")  // İlk haber başlığını logla
                } else {
                    // Boş liste durumu
                    errorMessage = "Haberler alınamadı: Boş liste döndü"  // Hata mesajı
                    Log.e("NewsViewModel", "Empty articles list received")  // Hata logu
                }
            } catch (e: Exception) {
                // Hata durumu
                Log.e("NewsViewModel", "Error fetching news", e)  // Hata detayını logla
                Log.e("NewsViewModel", "Error message: ${e.message}")  // Hata mesajını logla
                errorMessage = "Haberler alınamadı: ${e.message}"  // Kullanıcıya hata mesajı
            } finally {
                isLoading = false  // Yükleme bitti (başarılı veya başarısız)
            }
        }
    }
}