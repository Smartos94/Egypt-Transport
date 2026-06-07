package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Campaign
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.DirectionsTransit
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.viewinterop.AndroidView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.nativead.NativeAd
import com.google.android.gms.ads.nativead.NativeAdView
import com.google.android.gms.ads.AdLoader
import com.google.android.gms.ads.appopen.AppOpenAd
import com.google.android.gms.ads.FullScreenContentCallback
import android.widget.TextView
import android.widget.Button
import android.widget.LinearLayout
import android.view.ViewGroup
import android.os.Build
import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext

fun isRunningOnEmulator(): Boolean {
    val model = Build.MODEL ?: ""
    val brand = Build.BRAND ?: ""
    val device = Build.DEVICE ?: ""
    val product = Build.PRODUCT ?: ""
    val hardware = Build.HARDWARE ?: ""
    val fingerprint = Build.FINGERPRINT ?: ""

    return (brand.startsWith("generic") && device.startsWith("generic"))
            || fingerprint.startsWith("generic")
            || fingerprint.startsWith("unknown")
            || model.contains("google_sdk")
            || model.contains("Emulator")
            || model.contains("Android SDK built for x86")
            || hardware.contains("goldfish")
            || hardware.contains("ranchu")
            || product.contains("sdk_gphone")
            || product.contains("google_sdk")
            || product.contains("emulator")
}

// ==================== CONFIGURATION & GUIDELINES ====================
/*
  دليلك لربط إعلانات AdMob الحقيقية بالتطبيق:
  1. بعد إضافة مكتبة 'play-services-ads' لمشروعك، قم بتهيئة الـ SDK في الـ Application أو MainActivity:
     MobileAds.initialize(this) {}
  2. تأكد من إضافة الـ App ID في ملف AndroidManifest.xml:
     <meta-data
         android:name="com.google.android.gms.ads.APPLICATION_ID"
         android:value="ca-app-pub-3940256099942544~3347511713"/> // معرف الاختبار، استبدله بالمعرف الحقيقي الخاص بك.
*/

object AdMobConfig {
    // شفرات إعلانات جوجل أدسنس الحقيقية المخصصة من المستخدم
    var bannerAdUnitId = "ca-app-pub-6240992863518966/3327862330" // البانر المتكيف
    var interstitialAdUnitId = "ca-app-pub-6240992863518966/3286818515" // البيني
    var rewardedAdUnitId = "ca-app-pub-6240992863518966/1888364563" // فيديو بمكافأة
    var nativeAdUnitId = "ca-app-pub-6240992863518966/1973736842" // مدمج المحتوى
    var appOpenAdUnitId = "ca-app-pub-6240992863518966/4134707198" // إعلان شاشة البداية
}

/**
 * 1. Adaptive Banner (البانر المتكيف) - وضعه ممتاز ومثالي أسفل أو أعلى الشاشة.
 * يظهر هنا بتصميم فخم ومتناسق مع الوضع الليلي والوضع الفاتح لتجربة مستخدم فاخرة ومريحة للعين.
 */
@Composable
fun AdBanner(
    modifier: Modifier = Modifier,
    isEn: Boolean = false,
    adsRemoved: Boolean = false
) {
    var hasError by remember { mutableStateOf(false) }

    if (hasError || isRunningOnEmulator() || adsRemoved) {
        Box(modifier = modifier)
    } else {
        AndroidView(
            modifier = modifier.fillMaxWidth().height(58.dp),
            factory = { context ->
                try {
                    AdView(context).apply {
                        setAdSize(AdSize.BANNER)
                        adUnitId = AdMobConfig.bannerAdUnitId
                        adListener = object : AdListener() {
                            override fun onAdFailedToLoad(error: LoadAdError) {
                                super.onAdFailedToLoad(error)
                                hasError = true
                            }
                        }
                        loadAd(AdRequest.Builder().build())
                    }
                } catch (t: Throwable) {
                    hasError = true
                    android.view.View(context)
                }
            },
            update = { }
        )
    }
}

/**
 * 2. Native Advanced Ad Box (الإعلان مدمج المحتوى) - يوضع وسط القوائم (مثل قائمة المحطات).
 * هذا الإصدار يقوم بتحميل إعلان حقيقي من جوجل أد موب مع الرجوع للتصميم الافتراضي في حال فشل التحميل.
 */
@Composable
fun NativeAdCard(
    modifier: Modifier = Modifier,
    isEn: Boolean = false,
    adsRemoved: Boolean = false
) {
    val context = LocalContext.current
    var nativeAd by remember { mutableStateOf<NativeAd?>(null) }
    var adStatus by remember { mutableStateOf("loading") } // loading, loaded, error
    val isDark = MaterialTheme.colorScheme.background == com.example.ui.theme.ObsidianDarkBg
    val containerBg = MaterialTheme.colorScheme.surface
    val borderCol = MaterialTheme.colorScheme.primary.copy(alpha = if (isDark) 0.3f else 0.8f)

    if (isRunningOnEmulator() || adsRemoved) {
        // لا تظهر شيئاً إذا لم يتم تحميل الإعلان
        Box(modifier = modifier)
    } else {
        Box(modifier = modifier.fillMaxWidth().wrapContentHeight()) {
            if (adStatus == "loaded" && nativeAd != null) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    border = BorderStroke(if (isDark) 1.2.dp else 2.dp, borderCol),
                    colors = CardDefaults.cardColors(containerColor = containerBg)
                ) {
                    AndroidView(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(16.dp),
                        factory = { ctx ->
                            val adView = NativeAdView(ctx)
                            val adContainer = LinearLayout(ctx).apply {
                                orientation = LinearLayout.VERTICAL
                                layoutParams = ViewGroup.LayoutParams(
                                    ViewGroup.LayoutParams.MATCH_PARENT,
                                    ViewGroup.LayoutParams.WRAP_CONTENT
                                )
                            }
                            
                            populateNativeAdView(nativeAd!!, adView, adContainer, isEn, isDark)
                            adView.addView(adContainer)
                            adView.setNativeAd(nativeAd!!)
                            adView
                        },
                        update = {
                            // Empty update block is essential to prevent loading the ad repeatedly on every recomposition.
                        }
                    )
                }
            } else {
                // لا تظهر شيئاً إذا لم يتم تحميل الإعلان
                Box(modifier = modifier)
            }
        }

        LaunchedEffect(Unit) {
            val adLoader = AdLoader.Builder(context, AdMobConfig.nativeAdUnitId)
                .forNativeAd { ad ->
                    nativeAd = ad
                    adStatus = "loaded"
                }
                .withAdListener(object : AdListener() {
                    override fun onAdFailedToLoad(error: LoadAdError) {
                        adStatus = "error"
                    }
                })
                .build()
            adLoader.loadAd(AdRequest.Builder().build())
        }
    }
}

private fun populateNativeAdView(nativeAd: NativeAd, adView: NativeAdView, container: LinearLayout, isEn: Boolean, isDark: Boolean) {
    val context = adView.context
    
    // العنوان
    val headline = TextView(context).apply {
        text = nativeAd.headline
        textSize = 15f
        setTextColor(android.graphics.Color.parseColor(if (isDark) "#F8FAFC" else "#0F172A"))
        setTypeface(null, android.graphics.Typeface.BOLD)
        setPadding(0, 0, 0, 8)
    }
    adView.headlineView = headline
    container.addView(headline)

    // الوصف
    nativeAd.body?.let {
        val body = TextView(context).apply {
            text = it
            textSize = 12f
            setTextColor(android.graphics.Color.parseColor(if (isDark) "#94A3B8" else "#475569"))
            setPadding(0, 0, 0, 12)
        }
        adView.bodyView = body
        container.addView(body)
    }

    // زر اتخاذ إجراء (Call to Action)
    nativeAd.callToAction?.let {
        val shape = android.graphics.drawable.GradientDrawable().apply {
            cornerRadius = 24f
            setColor(android.graphics.Color.parseColor(if (isDark) "#9FE514" else "#5B9D0B"))
        }
        val cta = Button(context).apply {
            text = it
            background = shape
            setTextColor(android.graphics.Color.parseColor(if (isDark) "#0C1014" else "#FFFFFF"))
            transformationMethod = null // منع الأحرف الكبيرة التلقائية
            typeface = android.graphics.Typeface.create("sans-serif", android.graphics.Typeface.BOLD)
            val params = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                topMargin = 12
            }
            layoutParams = params
        }
        adView.callToActionView = cta
        container.addView(cta)
    }
}

/**
 * مدير الإعلانات البينية الحقيقي (Real Interstitial Ad Manager)
 */
object RealInterstitialAdManager {
    private var mInterstitialAd: com.google.android.gms.ads.interstitial.InterstitialAd? = null
    private var isLoading = false

    fun loadAd(context: Context) {
        if (mInterstitialAd != null || isLoading) return
        isLoading = true
        val adRequest = AdRequest.Builder().build()
        com.google.android.gms.ads.interstitial.InterstitialAd.load(
            context,
            AdMobConfig.interstitialAdUnitId,
            adRequest,
            object : com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mInterstitialAd = null
                    isLoading = false
                }

                override fun onAdLoaded(interstitialAd: com.google.android.gms.ads.interstitial.InterstitialAd) {
                    mInterstitialAd = interstitialAd
                    isLoading = false
                }
            }
        )
    }

    fun showAd(activity: Activity, onAdClosed: () -> Unit): Boolean {
        if (mInterstitialAd != null) {
            mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    mInterstitialAd = null
                    onAdClosed()
                    loadAd(activity)
                }

                override fun onAdFailedToShowFullScreenContent(adError: com.google.android.gms.ads.AdError) {
                    mInterstitialAd = null
                    onAdClosed()
                    loadAd(activity)
                }
            }
            mInterstitialAd?.show(activity)
            return true
        } else {
            loadAd(activity)
            return false
        }
    }
}

/**
 * مدير إعلانات الفيديو بمكافأة (Rewarded Ad Manager)
 */
object RealRewardedAdManager {
    private var mRewardedAd: com.google.android.gms.ads.rewarded.RewardedAd? = null
    private var isLoading = false

    fun loadAd(context: Context) {
        if (mRewardedAd != null || isLoading) return
        isLoading = true
        val adRequest = AdRequest.Builder().build()
        com.google.android.gms.ads.rewarded.RewardedAd.load(
            context,
            AdMobConfig.rewardedAdUnitId,
            adRequest,
            object : com.google.android.gms.ads.rewarded.RewardedAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mRewardedAd = null
                    isLoading = false
                }
                override fun onAdLoaded(rewardedAd: com.google.android.gms.ads.rewarded.RewardedAd) {
                    mRewardedAd = rewardedAd
                    isLoading = false
                }
            }
        )
    }

    fun showAd(activity: Activity, onAdEarned: () -> Unit): Boolean {
        if (mRewardedAd != null) {
            mRewardedAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    mRewardedAd = null
                    loadAd(activity)
                }
                override fun onAdFailedToShowFullScreenContent(adError: com.google.android.gms.ads.AdError) {
                    mRewardedAd = null
                    onAdEarned()
                    loadAd(activity)
                }
            }
            mRewardedAd?.show(activity) { onAdEarned() }
            return true
        } else {
            loadAd(activity)
            return false
        }
    }
}

/**
 * Unity Ads Manager - يعمل كبديل احتياطي لـ AdMob
 */
object UnityAdManager {
    private const val GAME_ID = "800002560"
    private const val REWARDED_PLACEMENT = "800002560"
    private const val INTERSTITIAL_PLACEMENT = "800002560"

    private var isInitialized = false
    private var rewardedLoaded = false
    private var interstitialLoaded = false

    fun initialize(context: Context) {
        if (isInitialized) return
        try {
            com.unity3d.ads.UnityAds.initialize(context, GAME_ID, object : com.unity3d.ads.IUnityAdsInitializationListener {
                override fun onInitializationComplete() {
                    isInitialized = true
                    loadRewardedAd(context)
                    loadInterstitialAd(context)
                }
                override fun onInitializationFailed(error: com.unity3d.ads.UnityAds.UnityAdsInitializationError, message: String) {}
            })
        } catch (_: Exception) {}
    }

    fun loadRewardedAd(context: Context) {
        if (!isInitialized || rewardedLoaded) return
        com.unity3d.ads.UnityAds.load(REWARDED_PLACEMENT, object : com.unity3d.ads.IUnityAdsLoadListener {
            override fun onUnityAdsAdLoaded(placementId: String) { rewardedLoaded = true }
            override fun onUnityAdsFailedToLoad(placementId: String, error: com.unity3d.ads.UnityAds.UnityAdsLoadError, message: String) { rewardedLoaded = false }
        })
    }

    fun loadInterstitialAd(context: Context) {
        if (!isInitialized || interstitialLoaded) return
        com.unity3d.ads.UnityAds.load(INTERSTITIAL_PLACEMENT, object : com.unity3d.ads.IUnityAdsLoadListener {
            override fun onUnityAdsAdLoaded(placementId: String) { interstitialLoaded = true }
            override fun onUnityAdsFailedToLoad(placementId: String, error: com.unity3d.ads.UnityAds.UnityAdsLoadError, message: String) { interstitialLoaded = false }
        })
    }

    fun showRewardedAd(activity: Activity, onEarned: () -> Unit): Boolean {
        if (!rewardedLoaded) { loadRewardedAd(activity); return false }
        com.unity3d.ads.UnityAds.show(activity, REWARDED_PLACEMENT, object : com.unity3d.ads.IUnityAdsShowListener {
            override fun onUnityAdsShowComplete(placementId: String, state: com.unity3d.ads.UnityAds.UnityAdsShowCompletionState) {
                if (state == com.unity3d.ads.UnityAds.UnityAdsShowCompletionState.COMPLETED) onEarned()
                rewardedLoaded = false
                loadRewardedAd(activity)
            }
            override fun onUnityAdsShowFailure(placementId: String, error: com.unity3d.ads.UnityAds.UnityAdsShowError, message: String) {
                rewardedLoaded = false
                loadRewardedAd(activity)
            }
            override fun onUnityAdsShowStart(placementId: String) {}
            override fun onUnityAdsShowClick(placementId: String) {}
        })
        return true
    }

    fun showInterstitialAd(activity: Activity, onClosed: () -> Unit): Boolean {
        if (!interstitialLoaded) { loadInterstitialAd(activity); return false }
        com.unity3d.ads.UnityAds.show(activity, INTERSTITIAL_PLACEMENT, object : com.unity3d.ads.IUnityAdsShowListener {
            override fun onUnityAdsShowComplete(placementId: String, state: com.unity3d.ads.UnityAds.UnityAdsShowCompletionState) {
                interstitialLoaded = false
                onClosed()
                loadInterstitialAd(activity)
            }
            override fun onUnityAdsShowFailure(placementId: String, error: com.unity3d.ads.UnityAds.UnityAdsShowError, message: String) {
                interstitialLoaded = false
                onClosed()
                loadInterstitialAd(activity)
            }
            override fun onUnityAdsShowStart(placementId: String) {}
            override fun onUnityAdsShowClick(placementId: String) {}
        })
        return true
    }
}

/**
 * Mediation: يحاول AdMob أولاً، فإن فشل يحاول Unity Ads
 */
object MediationManager {
    fun isAdsRemoved(context: android.content.Context): Boolean {
        return context.getSharedPreferences("transport_prefs", android.content.Context.MODE_PRIVATE)
            .getBoolean("ads_removed", false)
    }

    fun showRewardedAd(activity: Activity, onEarned: () -> Unit): Boolean {
        if (RealRewardedAdManager.showAd(activity, onEarned)) return true
        android.util.Log.d("Mediation", "AdMob rewarded not ready, trying Unity...")
        return UnityAdManager.showRewardedAd(activity, onEarned)
    }

    fun showInterstitialAd(activity: Activity, onClosed: () -> Unit): Boolean {
        if (RealInterstitialAdManager.showAd(activity, onClosed)) return true
        android.util.Log.d("Mediation", "AdMob interstitial not ready, trying Unity...")
        return UnityAdManager.showInterstitialAd(activity, onClosed)
    }
}

/**
 * دالة امتداد للوصول للـ Activity من السياق (Context) بأمان تام
 */
fun Context.findActivity(): Activity? {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    return null
}
