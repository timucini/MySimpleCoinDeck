object Dependencies {

    object Kotlin {
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
    }

    object AndroidX {
        const val activity = "androidx.activity:activity-ktx:${Versions.activity}"
        const val appCompat = "androidx.appcompat:appcompat:${Versions.appCompat}"
        const val core = "androidx.core:core-ktx:${Versions.core}"
        const val constraintLayout =
            "androidx.constraintlayout:constraintlayout:${Versions.constraintLayout}"
        const val coreTesting = "androidx.arch.core:core-testing:${Versions.coreTesting}"
        const val fragment = "androidx.fragment:fragment-ktx:${Versions.fragment}"
        const val lifecycleRuntime =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
        const val lifecycleViewmodel =
            "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
        const val navigationFragment =
            "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
        const val navigationUi = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
        const val navigationTesting =
            "androidx.navigation:navigation-testing:${Versions.navigation}"
        const val paging = "androidx.paging:paging-runtime-ktx:${Versions.paging}"
        const val swiperefresh =
            "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swiperefresh}"
        const val recyclerView = "androidx.recyclerview:recyclerview:${Versions.recyclerview}"
        const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
        const val roomKtx = "androidx.room:room-ktx:${Versions.room}"
        const val roomCompiler = "androidx.room:room-compiler:${Versions.room}"

        object Test {
            const val runner = "androidx.test:runner:${Versions.test}"
            const val rules = "androidx.test:rules:${Versions.test}"
            const val junit = "androidx.test.ext:junit:${Versions.testJunit}"
            const val orchestrator = "androidx.test:orchestrator:${Versions.test}"
            const val core = "androidx.test:core:${Versions.test}"
        }
    }

    object Hilt {
        const val core = "com.google.dagger:hilt-android:${Versions.hilt}"
        const val compiler = "com.google.dagger:hilt-compiler:${Versions.hilt}"
        const val testing = "com.google.dagger:hilt-android-testing:${Versions.hilt}"
    }


    object Espresso {
        const val core = "androidx.test.espresso:espresso-core:${Versions.espresso}"
        const val contrib = "androidx.test.espresso:espresso-contrib:${Versions.espresso}"
        const val intents = "androidx.test.espresso:espresso-intents:${Versions.espresso}"
    }

    object Google {
        const val material = "com.google.android.material:material:${Versions.material}"
    }

    object Retrofit {
        const val core = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
        const val gson = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
        const val moshi = "com.squareup.retrofit2:converter-moshi:${Versions.retrofit}"
    }

    object Glide {
        const val core = "com.github.bumptech.glide:glide:${Versions.glide}"
        const val compiler = "com.github.bumptech.glide:compiler:${Versions.glide}"
    }

    object Okhttp {
        const val logging = "com.squareup.okhttp3:logging-interceptor:${Versions.okHttp}"
        const val espressoIdlingResource = "com.jakewharton.espresso:okhttp3-idling-resource:1.0.0"
    }

    object Coroutines {
        const val core = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
        const val test = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Versions.coroutines}"
        const val android =
            "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    }

    object Coil {
        const val coil = "io.coil-kt:coil:${Versions.coil}"
        const val coilSvg = "io.coil-kt:coil-svg:${Versions.coilSVG}"
    }

    const val timber = "com.jakewharton.timber:timber:${Versions.timber}"
    const val junit = "junit:junit:${Versions.junit}"
    const val truth = "com.google.truth:truth:${Versions.truth}"
    const val barista = "com.schibsted.spain:barista:${Versions.barista}"
    const val materialDialogs = "com.afollestad.material-dialogs:input:${Versions.materialDialogs}"

}
