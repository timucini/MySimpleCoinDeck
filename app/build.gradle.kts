import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id(Plugins.androidApplication)
    kotlin(Plugins.kotlinAndroid)
    kotlin(Plugins.kotlinKapt)
    id(Plugins.navigationSafeArgs)
    id(Plugins.daggerHilt)
}

val coinRankingApiKey: String = gradleLocalProperties(rootDir).getProperty("COIN_RANKING_API_KEY")

android {
    compileSdkVersion(30)

    defaultConfig {
        applicationId = "com.example.mysimplecoindeck"
        setMinSdkVersion(24)
        setTargetSdkVersion(30)
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
        forEach {
            it.buildConfigField(
                type = "String",
                name = "COIN_RANKING_API_KEY",
                value = "$coinRankingApiKey"
            )
        }
    }
    buildFeatures {
        viewBinding = true
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(Dependencies.Kotlin.stdlib)

    implementation(Dependencies.AndroidX.core)
    implementation(Dependencies.AndroidX.constraintLayout)
    implementation(Dependencies.AndroidX.recyclerView)
    implementation(Dependencies.Google.material)

    // Architectural Components
    implementation(Dependencies.AndroidX.lifecycleRuntime)
    implementation(Dependencies.AndroidX.lifecycleViewmodel)

    // Room
    implementation(Dependencies.AndroidX.roomKtx)
    implementation(Dependencies.AndroidX.roomRuntime)
    kapt(Dependencies.AndroidX.roomCompiler)

    // Coroutines
    implementation(Dependencies.Coroutines.core)
    implementation(Dependencies.Coroutines.android)

    // Retrofit
    implementation(Dependencies.Retrofit.core)
    implementation(Dependencies.Retrofit.moshi)
    implementation(Dependencies.Okhttp.logging)

    // Navigation Components
    implementation(Dependencies.AndroidX.navigationFragment)
    implementation(Dependencies.AndroidX.navigationUi)

    // Glide
    implementation(Dependencies.Glide.core)
    kapt(Dependencies.Glide.compiler)

    // Hilt
    implementation(Dependencies.Hilt.core)
    kapt(Dependencies.Hilt.compiler)

    // Unit Tests
    testImplementation(Dependencies.junit)

    // Instrumented Tests
    androidTestImplementation(Dependencies.AndroidX.Test.core)
    androidTestImplementation(Dependencies.AndroidX.Test.junit)
    androidTestImplementation(Dependencies.Espresso.core)
    androidTestImplementation(Dependencies.Espresso.contrib)

    //Coil - Image Loading
    implementation(Dependencies.Coil.coil)
    implementation(Dependencies.Coil.coilSvg)

    // Material Dialogs
    implementation(Dependencies.materialDialogs)

}
