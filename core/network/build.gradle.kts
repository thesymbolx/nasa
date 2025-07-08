plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.kotlin.android)
    id("kotlinx-serialization")
    alias(libs.plugins.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "uk.co.nasa.network"
    compileSdk = 34

    defaultConfig {
        minSdk = 31

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)

    implementation(libs.retrofit)
    implementation(libs.retrofit.ktx.serialization)
    implementation(libs.kotlinx.serialization.json)

    implementation(platform(libs.okhttp.bom))

    ksp(libs.hilt.complier)
    implementation(libs.hilt)

    testImplementation(libs.junit)
}