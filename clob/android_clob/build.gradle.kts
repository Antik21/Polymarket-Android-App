plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.polymarket.clob"
    compileSdk = 34

    defaultConfig {
        minSdk = 31

        testApplicationId = "com.polymarket.android_clob.test"
        testInstrumentationRunner = "com.polymarket.clob.AppTestRunner"
        consumerProguardFiles("consumer-rules.pro")

        buildConfigField("String", "API_KEY", "${project.findProperty("API_KEY")}")
        buildConfigField("String", "API_SECRET", "${project.findProperty("API_SECRET")}")
        buildConfigField("String", "API_PASSPHRASE", "${project.findProperty("API_PASSPHRASE")}")
        buildConfigField("String", "PRIVATE_KEY", "${project.findProperty("PRIVATE_KEY")}")
    }

    android.buildFeatures.buildConfig = true

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
    implementation(project(":eip712"))
    implementation(project(":clob:clob_order_utils"))
    implementation(libs.gson)
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter)
    implementation(libs.sandwich)
    implementation(libs.okhttp)
    implementation(libs.androidx.runner)
    implementation(libs.kotlinx.coroutines)
    releaseImplementation(libs.chucker)
    debugImplementation(libs.chucker.debug)

    implementation(libs.androidx.core.ktx)
    implementation(libs.web3j.core)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
}