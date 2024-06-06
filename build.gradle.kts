// Top-level build file where you can add configuration options common to all sub-projects/modules.

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        // Add Google Services plugin classpath
        classpath("com.google.gms:google-services:4.4.2")
    }
}

plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    // Apply the Google services plugin
    id("com.google.gms.google-services") version "4.4.2" apply false
}

allprojects {
    // No need to re-declare repositories here if they are already declared in settings.gradle.kts
}
