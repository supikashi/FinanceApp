import java.util.Properties
import kotlin.apply

plugins {
    id("android-core-module")
    id("com.google.devtools.ksp")
}

dependencies {
    api(project(":core:domain"))
    implementation(libs.dagger)
    ksp(libs.dagger.compiler)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
}