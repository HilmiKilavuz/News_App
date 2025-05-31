# News App

## Proje Açıklaması

Bu uygulama, kullanıcılara güncel haberleri sunan modern bir Android uygulamasıdır. Jetpack Compose kullanılarak geliştirilmiş, MVVM mimarisi ile tasarlanmış ve modern Android geliştirme teknolojilerini içermektedir.

## Kullanılan Teknolojiler

- **Kotlin**: Projenin ana programlama dili
- **Jetpack Compose**: Modern UI toolkit
- **MVVM Mimarisi**: Model-View-ViewModel tasarım deseni
- **Coroutines**: Asenkron programlama için
- **Retrofit**: Ağ istekleri için
- **Gson**: JSON işleme
- **Navigation Compose**: Ekranlar arası gezinme
- **Coil**: Resim yükleme
- **Jetpack ViewModel**: UI durumunu yönetmek için
- **BuildConfig**: API anahtarlarını güvenli şekilde saklamak için

## Mimari Yapı

Uygulama, aşağıdaki MVVM (Model-View-ViewModel) mimari yapısını takip eder:

- **Model**: Veri modellerini ve iş mantığını içerir
  - `Article.kt`: Haber makalesi veri modeli
  - `NewResponse.kt`: API yanıtı için veri modeli

- **View**: UI bileşenlerini içerir
  - `screens/`: Ana ekranlar (NewsScreen, DetailsScreen)
  - `components/`: Yeniden kullanılabilir UI bileşenleri

- **ViewModel**: İş mantığını ve durumu yönetir
  - `NewsViewModel.kt`: Haber verilerini yönetir
  - `DetailsScreenViewModel.kt`: Detay ekranı için veri yönetimi

- **Services**: Ağ istekleri için yapılandırma
  - `NewsApiService.kt`: API endpoint tanımları
  - `RetrofitInstance.kt`: Retrofit yapılandırması

- **Navigation**: Uygulama içi gezinme mantığı
  - Uygulama içindeki farklı ekranlar arasında gezinmeyi sağlar

## Özellikler

- Güncel haberleri görüntüleme
- Haber detaylarını inceleme
- Kategori bazlı haber filtreleme
- Haber kaynağına gitme

## Kurulum

1. Projeyi klonlayın
2. api keyinizi  ekleyin
3. Android Studio'da projeyi açın ve çalıştırın

## API

Bu uygulama [News API](https://newsapi.org/) kullanmaktadır. Uygulama çalıştırmak için geçerli bir API anahtarına ihtiyacınız vardır.

---

# News App (English)

## Project Description

This application is a modern Android app that provides users with current news. It is developed using Jetpack Compose, designed with MVVM architecture, and incorporates modern Android development technologies.

## Technologies Used

- **Kotlin**: Main programming language
- **Jetpack Compose**: Modern UI toolkit
- **MVVM Architecture**: Model-View-ViewModel design pattern
- **Coroutines**: For asynchronous programming
- **Retrofit**: For network requests
- **Gson**: JSON processing
- **Navigation Compose**: For screen navigation
- **Coil**: Image loading
- **Jetpack ViewModel**: For managing UI state
- **BuildConfig**: For securely storing API keys

## Architectural Structure

The application follows the MVVM (Model-View-ViewModel) architectural pattern:

- **Model**: Contains data models and business logic
  - `Article.kt`: News article data model
  - `NewResponse.kt`: Data model for API response

- **View**: Contains UI components
  - `screens/`: Main screens (NewsScreen, DetailsScreen)
  - `components/`: Reusable UI components

- **ViewModel**: Manages business logic and state
  - `NewsViewModel.kt`: Manages news data
  - `DetailsScreenViewModel.kt`: Data management for detail screen

- **Services**: Configuration for network requests
  - `NewsApiService.kt`: API endpoint definitions
  - `RetrofitInstance.kt`: Retrofit configuration

- **Navigation**: In-app navigation logic
  - Enables navigation between different screens in the app

## Features

- View current news
- Examine news details
- Filter news by category
- Navigate to news source

## Installation

1. Clone the project
2. Add your api key file
3. Open the project in Android Studio and run

## API

This application uses the [News API](https://newsapi.org/). You need a valid API key to run the application. 
