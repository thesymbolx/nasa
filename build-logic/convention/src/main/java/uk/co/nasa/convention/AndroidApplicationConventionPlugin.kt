package uk.co.nasa.convention

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("com.android.application")
            apply("org.jetbrains.kotlin.android")
            apply("com.google.dagger.hilt.android")
            apply("com.google.devtools.ksp")
            apply("org.jetbrains.kotlin.plugin.compose")
            apply("kotlinx-serialization")
        }

        extensions.configure<ApplicationExtension> {
            compileSdk = 36

            defaultConfig {
                applicationId = "uk.co.nasa"
                minSdk = 31
                targetSdk = 36
                versionCode = 1
                versionName = "1.0"

                testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            }

            compileOptions {
                sourceCompatibility = JavaVersion.VERSION_17
                targetCompatibility = JavaVersion.VERSION_17
            }
        }

        extensions.configure<KotlinAndroidProjectExtension> {
            compilerOptions {
                jvmTarget.set(JvmTarget.JVM_17)
            }
        }

        val libs = extensions.getByType<VersionCatalogsExtension>().named("libs")

        dependencies {
            add("implementation", libs.findLibrary("hilt").get())
            add("ksp", libs.findLibrary("hilt.complier").get())

            add("implementation", libs.findLibrary("kotlinx.serialization.json").get())

            add("implementation", platform(libs.findLibrary("androidx.compose.bom").get()))
            add("implementation", libs.findLibrary("androidx.material3").get())
            add("implementation", libs.findLibrary("androidx.navigation.compose").get())
        }

    }
}