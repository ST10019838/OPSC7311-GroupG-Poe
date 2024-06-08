plugins {
    // Apply Android application plugin
    id("com.android.application")
    // Apply Kotlin Android plugin using alias from version catalog
    alias(libs.plugins.jetbrainsKotlinAndroid)
    // Apply Google services plugin for Firebase integration
    id("com.google.gms.google-services")
    // Uncomment if you need Dagger Hilt for dependency injection
    // id("kotlin-kapt")
    // id("com.google.dagger.hilt.android")
}

android {
    // Define the namespace of the application
    namespace = "com.example.chronometron"
    // Set the compile SDK version
    compileSdk = 34

    defaultConfig {
        // Define the application ID
        applicationId = "com.example.chronometron"
        // Minimum supported SDK version
        minSdk = 24
        // Target SDK version
        targetSdk = 34
        // Define version code and version name of the application
        versionCode = 1
        versionName = "1.0"

        // Define the test instrumentation runner
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            // Enable support library for vector drawables
            useSupportLibrary = true
        }
    }

    buildTypes {
        // Configuration for the release build type
        release {
            // Disable code shrinking, obfuscation, and optimization for release build
            isMinifyEnabled = false
            // Define ProGuard configuration files
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        // Set Java source compatibility version
        sourceCompatibility = JavaVersion.VERSION_1_8
        // Set Java target compatibility version
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        // Set Kotlin JVM target version
        jvmTarget = "1.8"
    }
    buildFeatures {
        // Enable Jetpack Compose
        compose = true
    }
    composeOptions {
        // Define Kotlin compiler extension version for Jetpack Compose
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            // Exclude unnecessary files from the APK
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Core AndroidX and Compose dependencies
    implementation(libs.androidx.core.ktx) // Android KTX core extensions
    implementation(libs.androidx.lifecycle.runtime.ktx) // Lifecycle runtime KTX
    implementation(libs.androidx.activity.compose) // Jetpack Compose integration for activities
    implementation(platform(libs.androidx.compose.bom)) // BOM for Jetpack Compose
    implementation(libs.androidx.ui) // UI library for Jetpack Compose
    implementation(libs.androidx.ui.graphics) // Graphics library for Jetpack Compose
    implementation(libs.androidx.ui.tooling.preview) // Tooling preview support for Jetpack Compose
    implementation(libs.androidx.material3) // Material Design 3 library for Jetpack Compose
    implementation("androidx.compose.material:material-icons-extended:1.5.1") // Material icons extended for Jetpack Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2") // ViewModel integration for Jetpack Compose

    // Additional libraries
    implementation("com.chargemap.compose:numberpicker:1.0.3") // Number picker for Jetpack Compose
    implementation(libs.androidx.lifecycle.runtime.compose)

    // Test dependencies
    testImplementation(libs.junit) // JUnit for unit testing
    androidTestImplementation(libs.androidx.junit) // AndroidX JUnit for Android testing
    androidTestImplementation(libs.androidx.espresso.core) // Espresso for UI testing
    androidTestImplementation(platform(libs.androidx.compose.bom)) // BOM for Jetpack Compose testing
    androidTestImplementation(libs.androidx.ui.test.junit4) // JUnit 4 integration for Jetpack Compose UI testing
    debugImplementation(libs.androidx.ui.tooling) // Tooling support for Jetpack Compose in debug builds
    debugImplementation(libs.androidx.ui.test.manifest) // Manifest for Jetpack Compose UI testing in debug builds

    // Voyager navigation library dependencies
    val voyagerVersion = "1.0.0"
    implementation("cafe.adriel.voyager:voyager-navigator:$voyagerVersion") // Navigator
    implementation("cafe.adriel.voyager:voyager-screenmodel:$voyagerVersion") // Screen Model
    implementation("cafe.adriel.voyager:voyager-bottom-sheet-navigator:$voyagerVersion") // BottomSheetNavigator
    implementation("cafe.adriel.voyager:voyager-tab-navigator:$voyagerVersion") // TabNavigator
    implementation("cafe.adriel.voyager:voyager-transitions:$voyagerVersion") // Transitions
    implementation("cafe.adriel.voyager:voyager-koin:$voyagerVersion") // Koin integration
    implementation("cafe.adriel.voyager:voyager-hilt:$voyagerVersion") // Hilt integration
    implementation("cafe.adriel.voyager:voyager-livedata:$voyagerVersion") // LiveData integration

    // Compose form library
    implementation("com.github.benjamin-luescher:compose-form:0.2.8")

    // CameraX library dependencies
    val cameraxVersion = "1.3.0-rc01"
    implementation(libs.androidx.camera.core) // CameraX core library
    implementation(libs.androidx.camera.camera2) // Camera2 support for CameraX
    implementation(libs.androidx.camera.lifecycle) // Lifecycle support for CameraX
    implementation(libs.androidx.camera.video) // Video support for CameraX
    implementation(libs.androidx.camera.view) // View support for CameraX
    implementation(libs.androidx.camera.extensions) // Extensions support for CameraX

    // Firebase and Play Services dependencies
    implementation(platform("com.google.firebase:firebase-bom:33.1.0")) // Firebase BOM
    implementation("com.google.firebase:firebase-analytics-ktx") // Firebase Analytics
    implementation("com.google.firebase:firebase-auth-ktx") // Firebase Authentication
    implementation("com.google.firebase:firebase-database-ktx:20.1.0") // Firebase Realtime Database
    implementation("com.google.android.gms:play-services-auth:21.1.0") // Google Play Services Authentication
    implementation("com.google.android.gms:play-services-identity:18.0.1") // Google Play Services Identity
    implementation("com.google.android.gms:play-services-safetynet:18.0.1") // Google Play Services SafetyNet
    implementation("com.google.android.material:material:1.11.0") // Material Design components

    // Room dependencies (if needed)
    // Uncomment if using Room for local database
    // val roomVersion = "2.6.1"
    // implementation("androidx.room:room-ktx:$roomVersion")
    // kapt("androidx.room:room-compiler:$roomVersion")

    // Dagger - Hilt dependencies (if needed)
    // Uncomment if using Dagger Hilt for dependency injection
    // implementation(libs.hilt.android)
    // kapt(libs.hilt.android.compiler)
}

// Uncomment if using kapt for annotation processing
// kapt {
//     correctErrorTypes = true
// }
