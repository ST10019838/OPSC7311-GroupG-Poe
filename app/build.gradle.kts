
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.googleGmsGoogleServices)
//    id("kotlin-kapt")
//    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.example.chronometron"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.chronometron"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation("androidx.compose.material:material-icons-extended:1.5.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")


    implementation("com.chargemap.compose:numberpicker:1.0.3")
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.firebase.database)


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


    val voyagerVersion = "1.0.0"
    // Navigator
    implementation("cafe.adriel.voyager:voyager-navigator:$voyagerVersion")
    // Screen Model
    implementation("cafe.adriel.voyager:voyager-screenmodel:$voyagerVersion")
    // BottomSheetNavigator
    implementation("cafe.adriel.voyager:voyager-bottom-sheet-navigator:$voyagerVersion")
    // TabNavigator
    implementation("cafe.adriel.voyager:voyager-tab-navigator:$voyagerVersion")
    // Transitions
    implementation("cafe.adriel.voyager:voyager-transitions:$voyagerVersion")
    // Koin integration
    implementation("cafe.adriel.voyager:voyager-koin:$voyagerVersion")
    // Hilt integration
    implementation("cafe.adriel.voyager:voyager-hilt:$voyagerVersion")
    // LiveData integration
    implementation("cafe.adriel.voyager:voyager-livedata:$voyagerVersion")


    implementation("com.github.benjamin-luescher:compose-form:0.2.8")


    val cameraxVersion = "1.3.0-rc01"
    implementation(libs.androidx.camera.core)
    implementation(libs.androidx.camera.camera2)
    implementation(libs.androidx.camera.lifecycle)
    implementation(libs.androidx.camera.video)

    implementation(libs.androidx.camera.view)
    implementation(libs.androidx.camera.extensions)


    // Firebase and Play Services for authentication and analytics.
    implementation("com.google.android.gms:play-services-auth:21.1.0")
    implementation(platform("com.google.firebase:firebase-bom:32.8.1"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-auth")
    implementation("com.google.android.material:material:1.11.0")
    implementation("com.google.android.gms:play-services-identity:18.0.1")
    implementation("com.google.android.gms:play-services-safetynet:18.0.1")


    // Room
//    val roomVersion = "2.6.1"
//    implementation("androidx.room:room-ktx:$roomVersion")
//    kapt("androidx.room:room-compiler:$roomVersion")


    //Dagger - Hilt
//    implementation(libs.hilt.android)
//    kapt(libs.hilt.android.compiler)


    // YCharts
    implementation(libs.ycharts)

    // Reveal Swipe
    implementation(libs.revealswipe)

    // Firebase auth
//    implementation("com.firebaseui:firebase-ui-auth:7.2.0")


    // Import the BoM for the Firebase platform
    implementation(platform(libs.google.firebase.bom))

    // Add the dependency for the Firebase Authentication library
    // When using the BoM, you don't specify versions in Firebase library dependencies
    implementation(libs.google.firebase.auth)
}

//kapt {
//    correctErrorTypes = true
//}

//repositories {
//    mavenCentral()
//}