import com.android.build.gradle.LibraryExtension
import org.gradle.kotlin.dsl.configure

plugins {
    id("android-core-module")

    id("org.jetbrains.kotlin.plugin.compose")
}

configure<LibraryExtension> {
    baseAndroidConfig(project)
    buildFeatures {
        compose = true
    }
}

dependencies {
    api(project(":core:domain"))
    implementation(libs.retrofit)
    implementation(libs.androidx.core.ktx)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
}
