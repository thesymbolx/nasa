plugins {
    id("co.uk.nasa.convention.application")
}

android {
    namespace = "uk.co.nasa"

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(project(":core:network"))
    implementation(project(":core:database"))
    implementation(project(":feature:astronomy_picture_of_day"))
    implementation(project(":core:designsystem"))
    implementation(project(":feature:favorite_picture_of_day"))

    implementation(libs.bundles.coil)
}