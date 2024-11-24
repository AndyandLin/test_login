plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("androidx.navigation.safeargs.kotlin")
    id("kotlin-kapt")
}

android {
    namespace = "com.example.myapplication"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
}

dependencies {
    // 版本常量
    val versions = object {
        val room = "2.6.1"
        val work = "2.9.0"
        val lifecycle = "2.7.0"
        val nav = "2.7.6"
        val retrofit = "2.9.0"
        val okhttp = "4.10.0"
        val coroutines = "1.7.1"
    }

    // AndroidX 核心組件
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")

    // UI 組件
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.viewpager2:viewpager2:1.0.0")

    // Lifecycle 組件
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:${versions.lifecycle}")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:${versions.lifecycle}")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:${versions.lifecycle}")

    // Activity & Fragment
    implementation("androidx.activity:activity-ktx:1.8.2")
    implementation("androidx.fragment:fragment-ktx:1.6.2")

    // Navigation 組件
    implementation("androidx.navigation:navigation-fragment-ktx:${versions.nav}")
    implementation("androidx.navigation:navigation-ui-ktx:${versions.nav}")

    // Room 數據庫
    implementation("androidx.room:room-runtime:${versions.room}")
    implementation("androidx.room:room-ktx:${versions.room}")
    kapt("androidx.room:room-compiler:${versions.room}")

    // WorkManager
    implementation("androidx.work:work-runtime-ktx:${versions.work}")

    // 網絡相關
    implementation("com.squareup.retrofit2:retrofit:${versions.retrofit}")
    implementation("com.squareup.retrofit2:converter-gson:${versions.retrofit}")
    implementation("com.squareup.okhttp3:okhttp:${versions.okhttp}")
    implementation("com.squareup.okhttp3:logging-interceptor:${versions.okhttp}")
    implementation("com.google.code.gson:gson:2.10")

    // Google Play Services
    implementation("com.google.android.gms:play-services-safetynet:18.0.1")

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:${versions.coroutines}")

    // 測試相關
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.44") // 使用最新版本
    kapt("com.google.dagger:hilt-compiler:2.44") // 使用最新版本
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.44")
    kaptAndroidTest("com.google.dagger:hilt-compiler:2.44")
}