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
- **OkHttp Logging Interceptor**: HTTP isteklerini loglamak için

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
- Haberlerde arama yapma
- Anahtar kelimeye göre filtreleme
- Haber kaynağına gitme

## Kurulum

1. Projeyi klonlayın
   ```
   git clone https://github.com/HilmiKilavuz/News_App.git
   ```

2. API anahtarınızı ekleyin
   - [News API](https://newsapi.org/) üzerinden bir API anahtarı edinin
   - `gradle.properties` dosyasına API anahtarınızı ekleyin:
   ```
   NEWS_API_KEY=sizin_api_anahtariniz
   ```
   
   > NOT: `gradle.properties` dosyası Git tarafından izlenmez, bu yüzden API anahtarınız güvendedir.

3. Android Studio'da projeyi açın ve çalıştırın

## API Anahtarı Hakkında

Bu uygulama, haber verileri için [News API](https://newsapi.org/) kullanmaktadır. Uygulamayı çalıştırmak için geçerli bir API anahtarına ihtiyacınız vardır.

API anahtarı, güvenlik açısından doğrudan kodda saklanmamaktadır. Bunun yerine, BuildConfig aracılığıyla erişilen `gradle.properties` dosyasında tanımlanır.

Eğer API anahtarını değiştirmeniz gerekirse, sadece `gradle.properties` dosyasındaki `NEWS_API_KEY` değerini güncelleyin.

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
- **OkHttp Logging Interceptor**: For logging HTTP requests

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
- Search in news
- Filter by keyword
- Navigate to news source

## Installation

1. Clone the project
   ```
   git clone https://github.com/HilmiKilavuz/News_App.git
   ```

2. Add your API key
   - Get an API key from [News API](https://newsapi.org/)
   - Add your API key to the `gradle.properties` file:
   ```
   NEWS_API_KEY=your_api_key
   ```
   
   > NOTE: The `gradle.properties` file is not tracked by Git, so your API key is secure.

3. Open the project in Android Studio and run

## About API Key

This application uses [News API](https://newsapi.org/) for news data. You need a valid API key to run the application.

For security reasons, the API key is not stored directly in the code. Instead, it is defined in the `gradle.properties` file and accessed via BuildConfig.

If you need to change the API key, simply update the `NEWS_API_KEY` value in the `gradle.properties` file.
