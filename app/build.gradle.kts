plugins {
    // Apply the Android application plugin to support Android app development.
    alias(libs.plugins.androidApplication)
    // Apply the Kotlin Android plugin for Kotlin support.
    alias(libs.plugins.jetbrainsKotlinAndroid)
    id("com.google.gms.google-services")
}

android {
    // Set the namespace used for resources and R class generation.
    namespace = "com.example.chronometron"
    // Set the compile SDK version used by your app.
    compileSdk = 34

    defaultConfig {
        // Define the application ID that uniquely identifies your app on the device and in Google Play Store.
        applicationId = "com.example.chronometron"
        // Set the minimum SDK version. The app will not install on devices with an SDK lower than this.
        minSdk = 24
        // Set the target SDK version. Your app is optimized for running on devices with this SDK.
        targetSdk = 34
        // Define the version code of your app, which Google Play uses to identify the version.
        versionCode = 1
        // Define the version name of your app, which is displayed to users.
        versionName = "1.0"

        // Specify the AndroidJUnitRunner class as the default test instrumentation runner.
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        // Enable support for vector drawables on older Android versions.
        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {
        release {
            // Enable minification and resource shrinking for release builds to reduce APK size.
            isMinifyEnabled = false
            // Specify the ProGuard rules file for code shrinking and obfuscation.
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        // Set Java compatibility to Java 8.
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        // Use JVM target 1.8 for Kotlin to enable certain features such as lambda expressions.
        jvmTarget = "1.8"
    }
    buildFeatures {
        // Enable Jetpack Compose for this project.
        compose = true
    }

    composeOptions {
        // Specify the version of Kotlin compiler extensions for Jetpack Compose.
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packagingOptions {
        // Exclude certain files to avoid build errors related to duplicate files.
        resources.excludes += "/META-INF/{AL2.0,LGPL2.1}"
    }
}

dependencies {
    // Include essential libraries for Android development and Jetpack Compose.
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)

    // Include additional libraries for UI components and testing.
    implementation("com.chargemap.compose:numberpicker:1.0.3")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Include navigation and dependency injection libraries.
    val voyagerVersion = "1.0.0"
    implementation("cafe.adriel.voyager:voyager-navigator:$voyagerVersion")
    implementation("cafe.adriel.voyager:voyager-screenmodel:$voyagerVersion")
    implementation("cafe.adriel.voyager:voyager-bottom-sheet-navigator:$voyagerVersion")
    implementation("cafe.adriel.voyager:voyager-tab-navigator:$voyagerVersion")
    implementation("cafe.adriel.voyager:voyager-transitions:$voyagerVersion")
    implementation("cafe.adriel.voyager:voyager-koin:$voyagerVersion")
    implementation("cafe.adriel.voyager:voyager-hilt:$voyagerVersion")
    implementation("cafe.adriel.voyager:voyager-livedata:$voyagerVersion")

    // Google Firebase and Play Services for authentication and analytics.
    implementation("com.google.android.gms:play-services-auth:21.1.0")
    implementation(platform("com.google.firebase:firebase-bom:32.8.1"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.android.material:material:1.11.0")
}

// Apply Google Services plugin at the end to ensure it configures the project based on the google-services.json file.
apply(plugin = "com.google.gms.google-services")
