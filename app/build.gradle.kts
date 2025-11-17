@file:Suppress("UnstableApiUsage")

plugins {
    id("com.android.application")
    id("kotlin-android")
}

android {
    namespace = "io.github.tristan23612.spotifymango"
    compileSdk = 35

    defaultConfig {
        applicationId = "io.github.tristan23612.spotifymango"
        minSdk = 27
        targetSdk = 34
        versionCode = 1
        versionName = "1.0.2"
        buildConfigField("long", "BUILD_DATE", "${System.currentTimeMillis()}")
    }


    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        buildConfig = true
        resValues = false
    }
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
}

dependencies {
    compileOnly("de.robv.android.xposed:api:82")
    implementation("org.luckypray:dexkit:2.0.0-rc7")
}
