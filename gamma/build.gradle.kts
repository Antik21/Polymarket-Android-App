plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.polymarket.gamma"
    compileSdk = 34

    defaultConfig {
        minSdk = 31

        testApplicationId = "com.polymarket.gamma.test"
        testInstrumentationRunner = "com.polymarket.gamma.AppTestRunner"

        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    kotlin {
        jvmToolchain(17)
    }
}

dependencies {
    implementation(libs.gson)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter)
    implementation(libs.sandwich)
    implementation(libs.okhttp)
    implementation(libs.androidx.runner)
    implementation(libs.kotlinx.coroutines)
    releaseImplementation(libs.chucker)
    debugImplementation(libs.chucker.debug)

    implementation(libs.web3j.core)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
