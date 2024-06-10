# ChronoMetron Time Management App

## Project Overview

ChronoMetron is an advanced time management application designed for Android, meticulously developed to enhance user productivity and time tracking. Developed as part of a group assignment for OPSC7311, this application integrates a variety of features to cater to comprehensive time management needs.


### Key Features

- **User Authentication:** Secure login with options to authenticate via Google or via the defined user/password
- **Timesheet Management:** Users can create, modify, and categorize their timesheet entries.
- **Photograph Attachments:** Optional photo/camera attachments for detailed documentation.
- **Work Hour Goals:** Set minimum and maximum daily work goals with notifications.
- **Detailed Reporting:** View and filter timesheet entries over selectable periods and access detailed analytics on time spent across categories.
- **User-Friendly Interface:** Intuitive UI that handles invalid inputs gracefully.


### Unique Features
- **Timesheet Entry Archiving:** You can archive your timesheet entries to hide them, reminisce about them later, or instead of deleting them (for hoarding purposes or fear that they might be needed later).
- **Swipable Timesheet Entries:** Swipe a timesheet entry to the right or the left in order to reveal an action, allowing you to quickly and easily delete or archive that entry,
- **Theme Toggler:** Can change the apps theme to light and dark mode.

<br>

### Note:
The completed code for the app is on the main branch.

The link for the main repository: https://github.com/ST10019838/OPSC7311-GroupG-Poe

<br>

## Installation

To run the ChronoMetron app, clone this repository and import the project into Android Studio. Ensure you have the latest version of Android Studio and the Kotlin plugin installed.

```bash
git clone https://github.com/ST10019838/OPSC7311-GroupG-Poe
```

<br>

## Usage
- **Login/Register:** Securely log in or register a new account.
- **Create Categories:** Customize categories for different activities.
- **Add Timesheet Entries:** Record your time with details like start/end times, date, description, and associated category.
- **Attach Photographs/camera image:** Optionally add photos to your entries.
- **Set Goals:** Define your daily minimum and maximum work hours.
- **View Reports:** Access detailed reports to monitor your productivity.

<br>

## Demostration
Refer to the demonstration videos in the repository to see the app in action, showcasing all functionalities through a simulated environment.

For **basic features (Part 2)**, refer to: 
> - GroupG_OPSC7311_POE_Part-2_DemonstrationVideo.mov

<br>

For **advanced features/improvements (Part 3)**, refer to:
> - GroupG_OPSC7311_POE_Part-3_DemonstrationVideo.mov



<br>

## Contributers - Group-G
- Guillaume Swanevelder (ST10019972)
- Damian Dare (ST10019838)


<br>

## Dependencies

ChronoMetron relies on several libraries and tools for its functionality. Ensure that these dependencies are included in your environment to run the application successfully.

### Core Dependencies
- **AndroidX Core & Lifecycle Libraries**
- implementation(libs.androidx.core.ktx)
- implementation(libs.androidx.lifecycle.runtime.ktx)
- implementation(libs.androidx.activity.compose)

### UI and Compose Dependencies
- implementation(platform(libs.androidx.compose.bom))
- implementation(libs.androidx.ui)
- implementation(libs.androidx.ui.graphics)
- implementation(libs.androidx.ui.tooling.preview)
- implementation(libs.androidx.material3)
- implementation("androidx.compose.material:material-icons-extended:1.5.1")
- implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.2")
- implementation(libs.ycharts)
- implementation(libs.revealswipe)
- implementation("com.chargemap.compose:numberpicker:1.0.3")

### CameraX for Image Capturing
- val cameraxVersion = "1.3.0-rc01"
- implementation(libs.androidx.camera.core)
- implementation(libs.androidx.camera.camera2)
- implementation(libs.androidx.camera.lifecycle)
- implementation(libs.androidx.camera.video)
- implementation(libs.androidx.camera.view)
- implementation(libs.androidx.camera.extensions)

### Navigation and Screen Management
- val voyagerVersion = "1.0.0"
- implementation("cafe.adriel.voyager:voyager-navigator:$voyagerVersion")
- implementation("cafe.adriel.voyager:voyager-screenmodel:$voyagerVersion")
- implementation("cafe.adriel.voyager:voyager-bottom-sheet-navigator:$voyagerVersion")
- implementation("cafe.adriel.voyager:voyager-tab-navigator:$voyagerVersion")
- implementation("cafe.adriel.voyager:voyager-transitions:$voyagerVersion")
- implementation("cafe.adriel.voyager:voyager-koin:$voyagerVersion")
- implementation("cafe.adriel.voyager:voyager-hilt:$voyagerVersion")
- implementation("cafe.adriel.voyager:voyager-livedata:$voyagerVersion")

### Firebase for Authentication and Analytics
- implementation("com.google.android.gms:play-services-auth:21.1.0")
- implementation(platform("com.google.firebase:firebase-bom:32.8.1"))
- implementation("com.google.firebase:firebase-analytics")
- implementation("com.google.firebase:firebase-auth")

### Material Design Components and Google Play Services
- implementation("com.google.android.material:material:1.11.0")
- implementation("com.google.android.gms:play-services-identity:18.0.1")
- implementation("com.google.android.gms:play-services-safetynet:18.0.1")
- implementation("com.github.benjamin-luescher:compose-form:0.2.8")
- implementation("com.chargemap.compose:numberpicker:1.0.3")

### Testing Libraries
- testImplementation(libs.junit)
- androidTestImplementation(libs.androidx.junit)
- androidTestImplementation(libs.androidx.espresso.core)
- androidTestImplementation(platform(libs.androidx.compose.bom))
- androidTestImplementation(libs.androidx.ui.test.junit4)
- debugImplementation(libs.androidx.ui.tooling)
- debugImplementation(libs.androidx.ui.test.manifest)



