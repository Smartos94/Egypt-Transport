# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.

# Preserve Jetpack Compose nodes and reflections
-keepclassmembers class * extends androidx.compose.ui.node.Owner { *; }
-keep class androidx.compose.ui.platform.** { *; }
-keep class androidx.compose.runtime.** { *; }

# Google Play Services and AdMob keep rules
-keep class com.google.android.gms.ads.** { *; }
-keep interface com.google.android.gms.ads.** { *; }
-keep class com.google.android.gms.common.** { *; }

# Keep project package structure to prevent any reflections issues on start
-keep class com.example.** { *; }
-keep class com.smartos.egyptmonorail.** { *; }

# Unity Ads keep rules to prevent obfuscation and runtime exceptions
-keep class com.unity3d.ads.** { *; }
-keep interface com.unity3d.ads.** { *; }
-keep class com.unity3d.services.** { *; }
-keep interface com.unity3d.services.** { *; }
-dontwarn com.unity3d.ads.**
-dontwarn com.unity3d.services.**
