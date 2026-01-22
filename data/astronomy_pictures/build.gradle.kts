
plugins {
    id("uk.co.nasa.convention.library")
    id("co.uk.nasa.convention.hilt")
    alias(libs.plugins.ksp)
}

android {
    namespace = "uk.co.nasa.astronomyPictures"


    defaultConfig {
   

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
    implementation(project(":core:network"))
    implementation(project(":core:database"))

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)

    ksp(libs.room.complier)
    ksp(libs.room.ktx)
    ksp(libs.room.runtime)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
}