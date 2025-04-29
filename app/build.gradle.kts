plugins {
    id("android.application")
    id("android.compose")
    id("android.hilt")
    id("kotlin-parcelize")
    id("dagger.hilt.android.plugin")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.thuanpx.mvvm_compose"

    defaultConfig {
        applicationId = "com.thuanpx.mvvm_compose"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildFeatures {
        buildConfig = true
    }

    buildTypes {
        debug {
            buildConfigField("String", "BASE_API_URL", "\"https://pokeapi.co/api/v2/\"")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                file("proguard-rules.pro")
            )
            buildConfigField("String", "BASE_API_URL", "\"https://pokeapi.co/api/v2/\"")
        }
      create("benchmark") {
        signingConfig = signingConfigs.getByName("debug")
        matchingFallbacks += listOf("release")
        isDebuggable = false
      }
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(platform(libs.compose.bom))
    implementation(libs.compose.ui.util)
    implementation(libs.compose.ui.tooling)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.runtime)
    implementation(libs.compose.foundation)
    implementation(libs.compose.foundation.layout)
    implementation(libs.compose.material)
    implementation(libs.compose.material3)

    implementation(libs.coil.kt)
    implementation(libs.coil.kt.compose)
    implementation(libs.coil.kt.svg)

    implementation(libs.kotlinx.coroutines.android)

    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    ksp(libs.hilt.compiler)

    implementation(libs.dataStore.preferences)

    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)

    implementation(libs.retrofit.core)
    implementation(libs.retrofit.converter.moshi)

    implementation(libs.okhttp.logging)

    implementation(libs.androidx.paging.compose)
    implementation(libs.androidx.paging.runtime)

    debugImplementation(libs.chucker.library)
    releaseImplementation(libs.chucker.library.no.op)

    implementation(libs.timber)

    implementation(libs.navigation.compose)

    implementation(libs.lifecycle.runtime.compose)
    implementation(libs.lifecycle.viewModel.compose)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity.compose)

}

if (file("google-services.json").exists()) {
    apply(plugin = libs.plugins.gms.googleServices.get().pluginId)
    apply(plugin = libs.plugins.firebase.crashlytics.get().pluginId)
}