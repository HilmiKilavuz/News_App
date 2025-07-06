package com.kilavuzhilmi.news_app.view.compenents

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * GradientButton - Gradient (renk geçişli) arka plana sahip modern buton bileşeni
 * 
 * Bu buton, kullanıcı etkileşimlerinde animasyonlu efektler sağlar.
 * Basıldığında küçülme animasyonu ve gölge değişimi gösterir.
 * 
 * @param text - Buton üzerinde görünecek metin
 * @param onClick - Butona tıklandığında çalışacak fonksiyon
 * @param modifier - Butonun boyut ve konum ayarları
 * @param enabled - Butonun aktif olup olmadığı (varsayılan: true)
 * @param gradient - Butonun arka plan rengi geçişi (varsayılan: mavi-mor geçişi)
 */
@Composable
fun GradientButton(
    text: String,  // Buton metni
    onClick: () -> Unit,  // Tıklama fonksiyonu
    modifier: Modifier = Modifier,  // Boyut ve konum ayarları
    enabled: Boolean = true,  // Buton aktif mi?
    gradient: Brush = Brush.horizontalGradient(  // Renk geçişi tanımı
        colors = listOf(
            Color(0xFF667eea),  // Açık mavi
            Color(0xFF764ba2)   // Mor
        )
    )
) {
    // Butonun basılı olup olmadığını takip eden state
    var isPressed by remember { mutableStateOf(false) }
    
    // Basılma durumuna göre ölçek animasyonu (0.95 = %95 boyut, 1.0 = normal boyut)
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,  // Basılıysa küçült, değilse normal boyut
        animationSpec = tween(100)  // 100 milisaniyede animasyon tamamlansın
    )

    /**
     * Box - Butonun ana konteynerı
     * 
     * Bu Box, butonun tüm görsel özelliklerini (gölge, arka plan, animasyon) içerir.
     * Modifier zinciri ile sırayla uygulanan özellikler:
     * 1. scale - Animasyonlu boyut değişimi
     * 2. shadow - Gölge efekti (basılı duruma göre değişir)
     * 3. clip - Köşe yuvarlatma
     * 4. background - Gradient arka plan
     * 5. clickable - Tıklama işlevi
     * 6. padding - İç boşluk
     */
    Box(
        modifier = modifier
            .scale(scale)  // Animasyonlu boyut değişimi uygula
            .shadow(
                elevation = if (isPressed) 4.dp else 8.dp,  // Basılıysa daha az gölge
                shape = RoundedCornerShape(16.dp)  // Köşeleri yuvarlat
            )
            .clip(RoundedCornerShape(16.dp))  // Arka planı da yuvarlat
            .background(gradient)  // Gradient arka plan uygula
            .clickable(
                interactionSource = remember { MutableInteractionSource() },  // Tıklama etkileşimi
                indication = null,  // Varsayılan tıklama animasyonunu kapat
                enabled = enabled  // Buton aktif mi?
            ) {
                isPressed = true  // Basılı duruma geç
                onClick()  // Tıklama fonksiyonunu çalıştır
                isPressed = false  // Normal duruma dön
            }
            .padding(horizontal = 32.dp, vertical = 16.dp),  // İç boşluk ekle
        contentAlignment = Alignment.Center  // İçeriği ortala
    ) {
        // Buton metni
        Text(
            text = text,
            color = Color.White,  // Beyaz metin rengi
            fontSize = 16.sp,  // Metin boyutu
            fontWeight = FontWeight.SemiBold  // Yarı kalın font
        )
    }
}

/**
 * NeumorphicButton - Neumorphism tasarım stilinde buton bileşeni
 * 
 * Neumorphism, modern UI tasarım trendlerinden biridir.
 * Buton, hafif gölgeler ve yumuşak köşelerle 3D görünüm sağlar.
 * 
 * @param text - Buton metni
 * @param onClick - Tıklama fonksiyonu
 * @param modifier - Boyut ve konum ayarları
 * @param enabled - Buton aktif mi?
 */
@Composable
fun NeumorphicButton(
    text: String,  // Buton metni
    onClick: () -> Unit,  // Tıklama fonksiyonu
    modifier: Modifier = Modifier,  // Boyut ve konum ayarları
    enabled: Boolean = true  // Buton aktif mi?
) {
    // Butonun basılı olup olmadığını takip eden state
    var isPressed by remember { mutableStateOf(false) }
    
    // Neumorphism için açık gri arka plan rengi
    val backgroundColor = Color(0xFFE0E5EC)

    Box(
        modifier = modifier
            .shadow(
                elevation = if (isPressed) 2.dp else 6.dp,  // Basılıysa daha az gölge (içe basık görünüm)
                shape = RoundedCornerShape(20.dp),  // Yuvarlak köşeler
                ambientColor = Color.Black.copy(alpha = 0.1f),  // Ortam gölgesi
                spotColor = Color.Black.copy(alpha = 0.1f)  // Nokta gölgesi
            )
            .clip(RoundedCornerShape(20.dp))  // Arka planı da yuvarlat
            .background(backgroundColor)  // Açık gri arka plan
            .clickable(
                interactionSource = remember { MutableInteractionSource() },  // Tıklama etkileşimi
                indication = null,  // Varsayılan animasyonu kapat
                enabled = enabled  // Buton aktif mi?
            ) {
                isPressed = !isPressed  // Basılı durumu tersine çevir
                onClick()  // Tıklama fonksiyonunu çalıştır
            }
            .padding(horizontal = 28.dp, vertical = 14.dp),  // İç boşluk
        contentAlignment = Alignment.Center  // İçeriği ortala
    ) {
        // Buton metni
        Text(
            text = text,
            color = Color(0xFF2D3748),  // Koyu gri metin rengi
            fontSize = 16.sp,  // Metin boyutu
            fontWeight = FontWeight.Medium  // Orta kalınlıkta font
        )
    }
}

/**
 * FloatingActionButton - Yuvarlak, yüzen aksiyon butonu
 * 
 * Bu buton, Material Design'ın Floating Action Button (FAB) konseptini uygular.
 * Genellikle ekranın sağ alt köşesinde yer alır ve ana aksiyon için kullanılır.
 * 
 * @param icon - Buton üzerinde gösterilecek ikon
 * @param onClick - Tıklama fonksiyonu
 * @param modifier - Boyut ve konum ayarları
 * @param containerColor - Buton arka plan rengi
 * @param contentColor - İkon rengi
 */
@Composable
fun FloatingActionButton(
    icon: ImageVector,  // Gösterilecek ikon
    onClick: () -> Unit,  // Tıklama fonksiyonu
    modifier: Modifier = Modifier,  // Boyut ve konum ayarları
    containerColor: Color = Color(0xFF6C5CE7),  // Mor arka plan rengi
    contentColor: Color = Color.White  // Beyaz ikon rengi
) {
    // Butonun basılı olup olmadığını takip eden state
    var isPressed by remember { mutableStateOf(false) }
    
    // Basılma durumuna göre ölçek animasyonu
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.9f else 1f,  // Basılıysa %90 boyut
        animationSpec = tween(150)  // 150 milisaniyede animasyon
    )

    Box(
        modifier = modifier
            .size(56.dp)  // Sabit 56dp boyut
            .scale(scale)  // Animasyonlu boyut değişimi
            .shadow(
                elevation = if (isPressed) 6.dp else 12.dp,  // Basılı duruma göre gölge
                shape = CircleShape  // Tam yuvarlak şekil
            )
            .clip(CircleShape)  // Arka planı da yuvarlat
            .background(containerColor)  // Arka plan rengi
            .clickable(
                interactionSource = remember { MutableInteractionSource() },  // Tıklama etkileşimi
                indication = null  // Varsayılan animasyonu kapat
            ) {
                isPressed = true  // Basılı duruma geç
                onClick()  // Tıklama fonksiyonunu çalıştır
                isPressed = false  // Normal duruma dön
            },
        contentAlignment = Alignment.Center  // İçeriği ortala
    ) {
        // İkon bileşeni
        Icon(
            imageVector = icon,  // Gösterilecek ikon
            contentDescription = null,  // Erişilebilirlik açıklaması (null = gerekli değil)
            tint = contentColor,  // İkon rengi
            modifier = Modifier.size(24.dp)  // İkon boyutu
        )
    }
}

/**
 * OutlinedGlowButton - Kenarlıklı ve parıltı efektli buton
 * 
 * Bu buton, hover (üzerine gelme) durumunda renk değişimi animasyonu gösterir.
 * Modern web tasarımından esinlenen bir buton stilidir.
 * 
 * @param text - Buton metni
 * @param onClick - Tıklama fonksiyonu
 * @param modifier - Boyut ve konum ayarları (varsayılan: 250x40dp)
 * @param enabled - Buton aktif mi?
 * @param borderColor - Kenarlık rengi
 */
@Composable
fun OutlinedGlowButton(
    text: String,  // Buton metni
    onClick: () -> Unit,  // Tıklama fonksiyonu
    modifier: Modifier = Modifier.size(width = 250.dp, height = 40.dp),  // Varsayılan boyut
    enabled: Boolean = true,  // Buton aktif mi?
    borderColor: Color = Color(0xFF00D4FF)  // Mavi kenarlık rengi
) {
    // Butonun üzerine gelinip gelinmediğini takip eden state
    var isHovered by remember { mutableStateOf(false) }

    // Hover durumuna göre kenarlık rengi animasyonu
    val animatedBorderColor by animateColorAsState(
        targetValue = if (isHovered) borderColor else borderColor.copy(alpha = 0.6f),  // Hover'da tam renk, normalde %60 şeffaf
        animationSpec = tween(300)  // 300 milisaniyede animasyon
    )

    // Hover durumuna göre metin rengi animasyonu
    val animatedTextColor by animateColorAsState(
        targetValue = if (isHovered) borderColor else Color(0xFF2D3748),  // Hover'da kenarlık rengi, normalde koyu gri
        animationSpec = tween(300)  // 300 milisaniyede animasyon
    )

    // Dış konteyner (kenarlık efekti için)
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))  // Köşeleri yuvarlat
            .background(
                if (isHovered) borderColor.copy(alpha = 0.1f) else Color.Transparent  // Hover'da hafif renkli arka plan
            )
            .clickable(
                interactionSource = remember { MutableInteractionSource() },  // Tıklama etkileşimi
                indication = null,  // Varsayılan animasyonu kapat
                enabled = enabled  // Buton aktif mi?
            ) {
                isHovered = !isHovered  // Hover durumunu tersine çevir
                onClick()  // Tıklama fonksiyonunu çalıştır
            }
            .padding(2.dp)  // Kenarlık kalınlığı için boşluk
    ) {
        // İç konteyner (beyaz arka plan)
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))  // İç köşeleri yuvarlat
                .background(Color.White)  // Beyaz arka plan
                .padding(horizontal = 24.dp, vertical = 12.dp),  // İç boşluk
            contentAlignment = Alignment.Center  // İçeriği ortala
        ) {
            // Buton metni
            Text(
                text = text,
                color = animatedTextColor,  // Animasyonlu metin rengi
                fontSize = 14.sp,  // Metin boyutu
                fontWeight = FontWeight.Medium  // Orta kalınlıkta font
            )
        }
    }
}

/**
 * IconTextButton - İkon ve metin içeren buton bileşeni
 * 
 * Bu buton, hem ikon hem de metin içerir ve modern UI tasarımında sıkça kullanılır.
 * Koyu arka plan üzerinde beyaz içerik ile kontrast oluşturur.
 * 
 * @param text - Buton metni
 * @param icon - Gösterilecek ikon
 * @param onClick - Tıklama fonksiyonu
 * @param modifier - Boyut ve konum ayarları
 * @param backgroundColor - Arka plan rengi
 * @param contentColor - İçerik rengi (ikon ve metin)
 */
@Composable
fun IconTextButton(
    text: String,  // Buton metni
    icon: ImageVector,  // Gösterilecek ikon
    onClick: () -> Unit,  // Tıklama fonksiyonu
    modifier: Modifier = Modifier,  // Boyut ve konum ayarları
    backgroundColor: Color = Color(0xFF1A202C),  // Koyu arka plan rengi
    contentColor: Color = Color.White  // Beyaz içerik rengi
) {
    // Butonun basılı olup olmadığını takip eden state
    var isPressed by remember { mutableStateOf(false) }
    
    // Basılma durumuna göre ölçek animasyonu
    val scale by animateFloatAsState(
        targetValue = if (isPressed) 0.95f else 1f,  // Basılıysa %95 boyut
        animationSpec = tween(100)  // 100 milisaniyede animasyon
    )

    // Yatay düzen (ikon ve metin yan yana)
    Row(
        modifier = modifier
            .scale(scale)  // Animasyonlu boyut değişimi
            .shadow(
                elevation = if (isPressed) 2.dp else 6.dp,  // Basılı duruma göre gölge
                shape = RoundedCornerShape(14.dp)  // Yuvarlak köşeler
            )
            .clip(RoundedCornerShape(14.dp))  // Arka planı da yuvarlat
            .background(backgroundColor)  // Arka plan rengi
            .clickable(
                interactionSource = remember { MutableInteractionSource() },  // Tıklama etkileşimi
                indication = null  // Varsayılan animasyonu kapat
            ) {
                isPressed = true  // Basılı duruma geç
                onClick()  // Tıklama fonksiyonunu çalıştır
                isPressed = false  // Normal duruma dön
            }
            .padding(horizontal = 20.dp, vertical = 12.dp),  // İç boşluk
        horizontalArrangement = Arrangement.spacedBy(8.dp),  // İkon ve metin arası 8dp boşluk
        verticalAlignment = Alignment.CenterVertically  // Dikey olarak ortala
    ) {
        // İkon bileşeni
        Icon(
            imageVector = icon,  // Gösterilecek ikon
            contentDescription = null,  // Erişilebilirlik açıklaması
            tint = contentColor,  // İkon rengi
            modifier = Modifier.size(20.dp)  // İkon boyutu
        )
        
        // Buton metni
        Text(
            text = text,
            color = contentColor,  // Metin rengi
            fontSize = 15.sp,  // Metin boyutu
            fontWeight = FontWeight.Medium  // Orta kalınlıkta font
        )
    }
}
