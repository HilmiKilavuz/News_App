package com.kilavuzhilmi.news_app.viewmodel

import androidx.lifecycle.ViewModel

/**
 * DetailsScreenViewModel - Haber detay ekranının veri yönetimi için ViewModel
 * 
 * Bu ViewModel, DetailsScreen'in iş mantığını ve veri yönetimini sağlar.
 * Şu anda boş bir ViewModel olarak tanımlanmıştır, ancak gelecekte şu özellikler eklenebilir:
 * - Haber favorilere ekleme/çıkarma
 * - Haber paylaşma işlevleri
 * - Haber okunma durumu takibi
 * - Haber yorumları
 * - İlgili haberler önerisi
 * 
 * ViewModel, Android Architecture Components'in bir parçasıdır ve şu avantajları sağlar:
 * - Ekran döndürme gibi yapılandırma değişikliklerinde veri kaybını önler
 * - UI ile iş mantığını ayırır (separation of concerns)
 * - Test edilebilirliği artırır
 * - Memory leak'leri önler
 * 
 * @param ViewModel - Android Architecture Components'ten gelen temel ViewModel sınıfı
 */
class DetailsScreenViewModel : ViewModel() {
    // Şu anda boş ViewModel - gelecekte işlevsellik eklenebilir
}