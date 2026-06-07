package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ui.MonorailApp
import com.example.ui.theme.MyApplicationTheme
import com.example.viewmodel.MonorailViewModel
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.appopen.AppOpenAd
import com.google.android.gms.ads.LoadAdError

class MainActivity : ComponentActivity() {
    private var appOpenAd: AppOpenAd? = null
    private var isAdShowing = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadAppOpenAd()
        enableEdgeToEdge()
        setContent {
            val monorailViewModel: MonorailViewModel = viewModel()
            val isDarkMode by monorailViewModel.isDarkMode.collectAsState()
            MyApplicationTheme(darkTheme = isDarkMode) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = androidx.compose.material3.MaterialTheme.colorScheme.background
                ) {
                    MonorailApp(monorailViewModel)
                }
            }
        }
    }

    private fun loadAppOpenAd() {
        try {
            val request = AdRequest.Builder().build()
            AppOpenAd.load(
                this,
                "ca-app-pub-6240992863518966/4206584219",
                request,
                object : AppOpenAd.AppOpenAdLoadCallback() {
                    override fun onAdLoaded(ad: AppOpenAd) {
                        appOpenAd = ad
                        if (!isAdShowing) showAppOpenAd()
                    }

                    override fun onAdFailedToLoad(loadAdError: LoadAdError) {
                        appOpenAd = null
                    }
                }
            )
        } catch (_: Throwable) { }
    }

    private fun showAppOpenAd() {
        appOpenAd?.let { ad ->
            isAdShowing = true
            ad.fullScreenContentCallback = object : com.google.android.gms.ads.FullScreenContentCallback() {
                override fun onAdDismissedFullScreenContent() {
                    appOpenAd = null
                    isAdShowing = false
                }
                override fun onAdFailedToShowFullScreenContent(adError: com.google.android.gms.ads.AdError) {
                    appOpenAd = null
                    isAdShowing = false
                }
            }
            ad.show(this)
        }
    }
}
