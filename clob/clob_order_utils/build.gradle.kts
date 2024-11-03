plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "com.polymarket.clob_order_utils"
    compileSdk = 34

    defaultConfig {
        minSdk = 31

        testApplicationId = "com.polymarket.clob_order_utils.test"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    implementation(project(":eip712"))
    implementation(libs.web3j.core)
    implementation(libs.gson)

    testImplementation(libs.junit)
}