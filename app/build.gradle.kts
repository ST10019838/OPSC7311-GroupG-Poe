plugins {
    // Applies the Android application plugin for building Android apps.
    alias(libs.plugins.androidApplication)
    // Applies the Kotlin Android plugin to add support for Kotlin in the project.
    alias(libs.plugins.jetbrainsKotlinAndroid)
    // Applies the Google Services plugin, necessary for integrating Firebase and other Google services.
    id("com.google.gms.google-services")
}

android {
    // Defines the unique namespace used for resources and R class generation.
    namespace = "com.example.chronometron"
    // Sets the compile SDK version that your app will be compiled against.
    compileSdk = 34

    defaultConfig {
        // Unique application ID that identifies your app on devices and in the Google Play Store.
        applicationId = "com.example.chronometron"
        // Minimum SDK version; the app will not run on devices with a lower SDK.
        minSdk = 24
        // Target SDK version; the app is tested and optimized for running on this SDK version.
        targetSdk = 34
        // An integer value that represents the version of the application code, relative to other versions.
        versionCode = 1
        // A string value that represents the release version of the application as displayed to users.
        versionName = "1.0"

        // Specifies which testing framework to use for running unit tests.
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        // Enables support for vector drawables on API levels lower than 21.
        vectorDrawables.useSupportLibrary = true
    }

    buildTypes {
        release {
            // Enables code shrinking, obfuscation, and optimization to reduce the APK size.
            isMinifyEnabled = false
            // Specifies the location of the ProGuard rules file.
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        // Specifies Java version compatibility.
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        // Sets the version of Kotlin bytecode to be generated.
        jvmTarget = "1.8"
    }
    buildFeatures {
        // Enables Jetpack Compose, a modern toolkit for building native UI.
        compose = true
    }

    composeOptions {
        // Sets the Kotlin compiler version for Jetpack Compose.
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            // Excludes certain files that might be packaged into the APK under META-INF directory.
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    // Core library dependencies for Android development.
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation("androidx.navigation:navigation-compose:2.7.7")


    // UI component and testing libraries.
    implementation("com.chargemap.compose:numberpicker:1.0.3")
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Libraries for navigation and dependency injection.
    val voyagerVersion = "1.0.0"
    implementation("cafe.adriel.voyager:voyager-navigator:$voyagerVersion")
    implementation("cafe.adriel.voyager:voyager-screenmodel:$voyagerVersion")
    implementation("cafe.adriel.voyager:voyager-bottom-sheet-navigator:$voyagerVersion")
    implementation("cafe.adriel.voyager:voyager-tab-navigator:$voyagerVersion")
    implementation("cafe.adriel.voyager:voyager-transitions:$voyagerVersion")
    implementation("cafe.adriel.voyager:voyager-koin:$voyagerVersion")
    implementation("cafe.adriel.voyager:voyager-hilt:$voyagerVersion")
    implementation("cafe.adriel.voyager:voyager-livedata:$voyagerVersion")

    // Firebase and Play Services for authentication and analytics.
    implementation("com.google.android.gms:play-services-auth:21.1.0")
    implementation(platform("com.google.firebase:firebase-bom:32.8.1"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.android.material:material:1.11.0")
    implementation("com.google.android.gms:play-services-identity:18.0.1")
    implementation("com.google.android.gms:play-services-safetynet:18.0.1")


    // Compose Form library for enhanced forms in Compose UI.
    implementation("com.github.benjamin-luescher:compose-form:0.2.8")

    implementation("androidx.compose.material:material-icons-core:1.6.6")
    implementation("androidx.compose.material:material-icons-extended:1.6.6")
    implementation("io.coil-kt:coil-compose:2.1.0")

}

// Applies the Google Services plugin to read the google-services.json file and integrate Firebase services.
apply(plugin = "com.google.gms.google-services")
