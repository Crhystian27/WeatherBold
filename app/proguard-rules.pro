# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# ===== SECURITY: Ofuscar API Key =====
# Keep BuildConfig but obfuscate values
-keep class co.cristian.weatherbold.BuildConfig { *; }

# ===== Retrofit =====
-keepattributes Signature, InnerClasses, EnclosingMethod
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations
-keepattributes AnnotationDefault

# Keep API service interfaces
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}

-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn javax.annotation.**
-dontwarn kotlin.Unit
-dontwarn retrofit2.KotlinExtensions
-dontwarn retrofit2.KotlinExtensions$*

# ===== OkHttp =====
-dontwarn okhttp3.**
-dontwarn okio.**
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

# ===== Gson - Optimized Rules =====
-keepattributes Signature
-keepattributes *Annotation*
-dontwarn sun.misc.**

# Keep Gson TypeToken and its generic type
-keep class com.google.gson.reflect.TypeToken { *; }
-keep class * extends com.google.gson.reflect.TypeToken

# Keep generic signatures for Gson
-keepattributes Signature

# Specific Gson classes needed for reflection
-keep class com.google.gson.stream.** { *; }

# Keep custom TypeAdapters if you have any
-keep class * implements com.google.gson.TypeAdapter
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

# ===== Data Classes for Serialization =====
# Keep all fields and constructors for DTOs (used by Gson)
-keep class co.cristian.weatherbold.data.remote.dto.** { *; }

# Keep all fields for domain models (may be serialized)
-keep class co.cristian.weatherbold.domain.model.Location { *; }
-keep class co.cristian.weatherbold.domain.model.Weather { *; }
-keep class co.cristian.weatherbold.domain.model.WeatherDetail { *; }
-keep class co.cristian.weatherbold.domain.model.WeatherSummary { *; }

# ===== Coroutines =====
-keepnames class kotlinx.coroutines.internal.MainDispatcherFactory {}
-keepnames class kotlinx.coroutines.CoroutineExceptionHandler {}
-keepclassmembers class kotlinx.coroutines.** {
    volatile <fields>;
}

# ===== Hilt =====
-dontwarn com.google.errorprone.annotations.**

# ===== Kotlin =====
-keep class kotlin.Metadata { *; }
-keepclassmembers class **$WhenMappings {
    <fields>;
}
-keepclassmembers class kotlin.Metadata {
    public <methods>;
}

# ===== Coil (Image Loading) =====
-dontwarn coil.**
-keep class coil.ImageLoader { *; }
-keep class coil.request.ImageRequest { *; }

# ===== Navigation Component =====
-keepnames class androidx.navigation.fragment.NavHostFragment
-keep class * extends androidx.fragment.app.Fragment{}

# ===== ViewBinding =====
-keep class * implements androidx.viewbinding.ViewBinding {
    public static *** bind(android.view.View);
    public static *** inflate(android.view.LayoutInflater);
}

# ===== Material Components =====
-dontwarn com.google.android.material.**
-keep class com.google.android.material.R$* { *; }