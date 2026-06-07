package com.example

import android.app.Application
import android.preference.PreferenceManager
import com.example.ui.components.RealInterstitialAdManager
import com.example.ui.components.RealRewardedAdManager
import com.example.ui.components.UnityAdManager
import com.google.android.gms.ads.MobileAds
import org.osmdroid.config.Configuration

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        try {
            Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this))
            Configuration.getInstance().userAgentValue = packageName
            MobileAds.initialize(this) {}
            RealInterstitialAdManager.loadAd(this)
            RealRewardedAdManager.loadAd(this)
            UnityAdManager.initialize(this)
        } catch (_: Throwable) { }
    }
}
