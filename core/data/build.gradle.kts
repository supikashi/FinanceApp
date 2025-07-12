import java.util.Properties
import kotlin.apply

plugins {
    id("android-core-module")
    id("com.google.devtools.ksp")
    kotlin("plugin.serialization") version "2.1.20"
}

android {

    defaultConfig {

        val props = Properties().apply {
            rootProject.file("local.properties")
                .inputStream()
                .use { load(it) }
        }
        val apiKey: String = props.getProperty("API_KEY")
        buildConfigField("String", "API_KEY", "\"$apiKey\"")
    }

    buildFeatures {
        buildConfig = true
    }
}

dependencies {
    api(project(":core:domain"))
    implementation(libs.androidx.core.ktx)
    implementation(libs.retrofit)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation(libs.okhttp)

    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    implementation(libs.dagger)
    ksp(libs.dagger.compiler)
}
