// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    id("com.google.gms.google-services") version "4.4.1" apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
        maven(url = "https://jitpack.io") // Corrected syntax for Kotlin DSL
    }
    dependencies {
        // Ensure you have the necessary classpath for Google Services and Kotlin
        classpath("com.android.tools.build:gradle:7.0.4") // Use the version that matches your project requirements
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.31") // Use the version that matches your project requirements
        classpath("com.google.gms:google-services:4.4.1") // Google Services plugin
    }
}

tasks.register("clean", Delete::class) {
    delete(rootProject.buildDir)
}
