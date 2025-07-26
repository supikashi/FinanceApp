plugins {
    id("android-feature-module")
    id("com.google.devtools.ksp")
    kotlin("plugin.serialization") version "2.1.20"
}

dependencies {
    api(project(":core:data"))
    api(project(":core:ui"))
    api(project(":core:di"))
    api(project(":core:graph"))
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.dagger)
    ksp(libs.dagger.compiler)
}
