import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.androidx.navigation.safeargs)
}

android {
    namespace = "co.cristian.weatherbold"
    compileSdk = 36

    defaultConfig {
        applicationId = "co.cristian.weatherbold"
        minSdk = 21
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        // Leer API Key desde local.properties de forma segura
        val apiKey = rootProject.file("local.properties")
            .takeIf { it.exists() }
            ?.inputStream()
            ?.use { Properties().apply { load(it) } }
            ?.getProperty("WEATHER_API_KEY")
            ?: error("⚠️ WEATHER_API_KEY no encontrada en local.properties. Por favor, agrega tu API Key.")

        buildConfigField("String", "WEATHER_API_KEY", "\"$apiKey\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        debug {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        viewBinding = true
        buildConfig = true
    }
    
    testOptions {
        unitTests {
            isIncludeAndroidResources = true
            isReturnDefaultValues = true
        }
    }
}

// JaCoCo configuration for code coverage
apply(plugin = "jacoco")

tasks.register<JacocoReport>("jacocoTestReport") {
    dependsOn("testDebugUnitTest")
    
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
    
    val fileFilter = listOf(
        // Android generated
        "**/R.class",
        "**/R$*.class",
        "**/BuildConfig.*",
        "**/Manifest*.*",
        "**/*Test*.*",
        "android/**/*.*",
        
        // Data Binding
        "**/*\$ViewInjector*.*",
        "**/*\$ViewBinder*.*",
        "**/databinding/*",
        "**/android/databinding/*",
        "**/androidx/databinding/*",
        "**/BR.*",
        
        // Hilt/Dagger generated
        "**/di/**",
        "**/*_Factory.*",
        "**/*_MembersInjector.*",
        "**/*Module.*",
        "**/*Module\$*.*",
        "**/Hilt_*.*",
        "**/*_HiltModules*.*",
        "**/*_ComponentTreeDeps*.*",
        "**/*_Provide*Factory*.*",
        
        // UI Layer (Fragments, Activities, Adapters) - NO BUSINESS LOGIC
        "**/presentation/ui/**/*Fragment.*",
        "**/presentation/ui/**/*Adapter.*",
        "**/presentation/ui/**/*Extensions.*",
        "**/MainActivity.*",
        "**/MainActivity\$*.*",
        "**/WeatherBoldApplication.*",
        
        // DTOs (Data Transfer Objects - no business logic)
        "**/data/remote/dto/**",
        
        // API Interfaces (no logic to test)
        "**/data/remote/api/**",
        "**/data/remote/datasource/**",
        
        // Security/Config (tested via integration)
        "**/core/security/**",
        "**/core/network/ApiKeyInterceptor.*",
        "**/core/network/NetworkConnectivityManager.*",
        "**/core/network/NetworkErrorHandler.*",
        
        // Constants (no logic)
        "**/core/network/ApiConstants.*",
        "**/core/util/UiConstants.*",
        
        // Repository interfaces (no implementation)
        "**/domain/repository/*Repository.*"
    )
    
    val debugTree = fileTree("${layout.buildDirectory.get().asFile}/tmp/kotlin-classes/debug") {
        exclude(fileFilter)
    }
    
    val mainSrc = "${project.projectDir}/src/main/java"
    
    sourceDirectories.setFrom(files(mainSrc))
    classDirectories.setFrom(files(debugTree))
    executionData.setFrom(fileTree(layout.buildDirectory.get().asFile) {
        include("jacoco/testDebugUnitTest.exec")
    })
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.recyclerview)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    // Networking
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.gson)

    // Dependency Injection - Hilt
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    // Coroutines
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.kotlinx.coroutines.core)

    // Image Loading
    implementation(libs.coil)

    // Testing
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.coroutines.test)
    testImplementation(libs.turbine)
    testImplementation(libs.truth)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}