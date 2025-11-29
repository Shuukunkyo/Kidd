plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "udemy.android.kidd"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "udemy.android.kidd"
        minSdk = 30
        targetSdk = 36
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
        compose = true
    }
}

dependencies {

    // ... 之前的依赖 (例如: 基础, Navigation, Compose)


    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")

// 【新增】MVVM - Networking (Retrofit/OkHttp)
// 用于发起网络请求
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
// Retrofit 的 Moshi 转换器 (日本项目常用 Moshi/Gson 来处理 JSON 序列化)
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")
// Moshi 核心库
    implementation("com.squareup.moshi:moshi-kotlin:1.15.1")
// OkHttp 拦截器，用于日志打印/调试
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")


// 【新增】MVVM - Coroutines (协程)
// 用于在 ViewModel 中安全地启动协程
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.0")
// 用于将 LiveData 转换为 Flow 或在 Lifecycle-aware 的 Scope 中启动协程
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.0") // 确认版本，您代码中已有这个，确保是最新版本或您想用的版本

    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")


        // 基础
    implementation ("androidx.core:core-ktx:1.12.0")
    implementation ("androidx.appcompat:appcompat:1.6.1")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")

    // Jetpack Navigation Component
    implementation ("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation ("androidx.navigation:navigation-ui-ktx:2.7.7")

    // Activity & Fragment KTX
    implementation ("androidx.activity:activity-ktx:1.8.2")
    implementation ("androidx.fragment:fragment-ktx:1.6.2")

        // ... 其他依赖（如 LiveData, ViewModel, Compose 等后续再加）

    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")




    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)
}