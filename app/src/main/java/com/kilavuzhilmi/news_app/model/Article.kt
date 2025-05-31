package com.kilavuzhilmi.news_app.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class NewResponse(
    val articles : List<Article>

)
@Parcelize
data class Article(
    val author: String?,            // Yazar bilgisi
    val title: String,              // Başlık
    val description: String?,       // Açıklama
    val urlToImage: String?,        // Görsel URL'si
    val url: String,                // Haber linki
    val publishedAt: String?        // Yayınlanma tarihi (ISO formatında gelir)
): Parcelable