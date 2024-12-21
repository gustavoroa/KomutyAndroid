plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.roasystems.komuty"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.roasystems.komuty"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    implementation(libs.lifecycle.livedata.ktx)
    implementation(libs.lifecycle.viewmodel.ktx)
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    // Firebase Authentication (for Google Authentication)
    implementation("com.google.firebase:firebase-auth:21.1.0")

    // Retrofit for Web API integration (e.g., for backend services)
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // OkHttp (for handling HTTP requests, used by Retrofit)
    implementation("com.squareup.okhttp3:okhttp:4.9.3")

    // Gson for parsing JSON
    implementation("com.google.code.gson:gson:2.8.9")

    // MySQL Connector for accessing MySQL databases (for Android)
    implementation("mysql:mysql-connector-java:8.0.27")
    implementation(libs.legacy.support.v4)

    // JUnit testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}