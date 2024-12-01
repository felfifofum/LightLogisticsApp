plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.example.lightlogisticsapp"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.lightlogisticsapp"
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }

    repositories {
        google()
        mavenCentral()
        maven {
            url = uri("https://maven.tealiumiq.com/android/releases/")
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.androidx.junit.ktx)
    testImplementation(libs.junit)
    // Added to help execute tasks synchrounously
    testImplementation("androidx.arch.core:core-testing:2.1.0")
    // Provides mocked Android environment
    testImplementation ("org.robolectric:robolectric:4.10")
    // To mock dependencies
    testImplementation ("org.mockito:mockito-core:4.5.1")
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Tealium for BrowserStack logging

    /*
    Tealium collects customer data across
    digital touch points to improve customer experience.
    */
    implementation("com.tealium:kotlin-core:1.6.0")
}