import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")

    kotlin("kapt")
    id("com.google.dagger.hilt.android")

    id("com.google.gms.google-services")
    id("com.google.devtools.ksp") version "1.8.10-1.0.9"
}

android {
    namespace = "com.example.projectandroid2"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.projectandroid2"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        //testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testInstrumentationRunner = "com.example.projectandroid2.HiltTestRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf(
                    "room.schemaLocation" to "$projectDir/schemas",
                    "room.incremental" to "true"
                )
            }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17

    }
    kotlinOptions {
        jvmTarget = "17"
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }

}

dependencies {
    val nav_version = "2.5.3"

    implementation("androidx.compose.runtime:runtime-livedata:1.5.4")

    implementation ("androidx.compose.foundation:foundation:1.0.0")
    implementation ("androidx.compose.material:material:1.0.0")

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.2")
    implementation("androidx.activity:activity-compose:1.8.0")
    implementation(platform("androidx.compose:compose-bom:2023.03.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.03.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    testImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    //androidTestImplementation ("androidx.test:core:1.5.0")
    testImplementation("androidx.test:core:1.5.0")

    androidTestImplementation ("androidx.compose.ui:ui-test")
 //   androidTestImplementation ("androidx.test.espresso:espresso-intents:3.5.1")

 //   androidTestImplementation ("androidx.test:rules:1.5.0")
//
//    testImplementation ("org.mockito.kotlin:mockito-kotlin:5.2.1")
//    testImplementation ("org.mockito:mockito-core:5.8.0")
//    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")
//    // Optional -- mockito-kotlin
//    testImplementation ("io.mockk:mockk:1.13.8")


    //androidTestImplementation ("androidx.test:runner:1.5.2")
    testImplementation("androidx.test:runner:1.5.2")
    testImplementation ("org.robolectric:robolectric:4.7.3")

    // Hilt testing
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.44")
    kaptAndroidTest("com.google.dagger:hilt-android-compiler:2.44")

    //  Timber
    implementation("com.jakewharton.timber:timber:4.7.1")

    //coil
    implementation("io.coil-kt:coil-compose:2.5.0")
    //navigation
    implementation ("androidx.navigation:navigation-compose:$nav_version")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")

    // Moshi for json converter
    implementation("com.squareup.moshi:moshi:1.14.0")
    implementation("com.squareup.moshi:moshi-kotlin:1.14.0")

    // DataStore
    implementation("androidx.datastore:datastore-core:1.0.0")
    implementation("androidx.datastore:datastore-preferences:1.0.0")


    // Hilt
    implementation("com.google.dagger:hilt-android:2.44")
    kapt("com.google.dagger:hilt-android-compiler:2.44")
    implementation("androidx.hilt:hilt-navigation-compose:1.0.0")

    // Room
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.5.1")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.1")
    implementation("androidx.room:room-ktx:2.4.3")
    implementation("com.google.android.gms:play-services-maps:18.1.0")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    kapt("androidx.room:room-compiler:2.4.3")
    implementation("androidx.room:room-runtime:2.4.3")
    testImplementation("androidx.room:room-testing:2.4.3")

    androidTestImplementation ("app.cash.turbine:turbine:0.9.0")

    // Map
    implementation ("com.google.maps.android:maps-compose:4.1.1")
    implementation("com.google.android.gms:play-services-maps:18.1.0")
    implementation("androidx.compose.foundation:foundation:1.6.0-alpha07")
    implementation("com.google.maps.android:android-maps-utils:3.5.3")
    implementation("com.google.maps.android:maps-compose-widgets:3.1.1")
    implementation("com.google.maps.android:maps-compose-utils:3.1.1")

    //FIREBASE
    // Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:32.5.0"))

    // Firestore
    implementation("com.google.firebase:firebase-firestore")


    implementation("androidx.lifecycle:lifecycle-runtime-compose:2.6.2")

    implementation ("androidx.lifecycle:lifecycle-viewmodel-compose:1.0.0-alpha07")

    // Gson
    implementation("com.google.code.gson:gson:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Splash screen
   // implementation ("androidx.core:core-splashscreen:1.0.0")

    // ML Kit
// Layouts
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("androidx.annotation:annotation:1.2.0")
// CameraX
    implementation("androidx.camera:camera-camera2:1.0.0-SNAPSHOT")
    implementation("androidx.camera:camera-lifecycle:1.0.0-SNAPSHOT")
    implementation("androidx.camera:camera-view:1.0.0-SNAPSHOT")
// For graphic overlay
    implementation("com.google.guava:guava:27.1-android")
// text Recognition
    implementation("com.google.mlkit:text-recognition:16.0.0")


}
kapt {
    correctErrorTypes = true
}
