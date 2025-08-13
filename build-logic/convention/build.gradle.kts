import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "uk.co.nasa.buildlogic"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

gradlePlugin {
    plugins {
        create("androidApplication") {
            id = "co.uk.nasa.convention.application"
            implementationClass = "uk.co.nasa.convention.AndroidApplicationConventionPlugin"
        }
    }
}