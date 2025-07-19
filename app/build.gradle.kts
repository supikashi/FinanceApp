import java.util.Properties

plugins {
    id("android-app-module")
    id("com.google.devtools.ksp")
    kotlin("plugin.serialization") version "2.1.20"
    id("com.jraska.module.graph.assertion") version "2.9.0"
}

moduleGraphAssert {
    maxHeight = 6
}

android {
    namespace = "com.spksh.financeapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.spksh.financeapp"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val props = Properties().apply {
            rootProject.file("local.properties")
                .inputStream()
                .use { load(it) }
        }
        val apiKey: String = props.getProperty("API_KEY")
        buildConfigField("String", "API_KEY", "\"$apiKey\"")
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
}

dependencies {
    implementation(project(":features:transactions"))
    implementation(project(":features:account"))
    implementation(project(":features:category"))
    implementation(project(":features:settings"))
    implementation(libs.dagger)
    implementation(libs.androidx.work.runtime.ktx)
    ksp(libs.dagger.compiler)
}