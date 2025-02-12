plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.serialization)
    kotlin("kapt")
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "dhandev.android.oknoted"
    compileSdk = 35

    defaultConfig {
        applicationId = "dhandev.android.oknoted"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    //TODO: Enable if want the release build to be signed
//    signingConfigs {
//        create("release"){
//            val properties = Properties().apply {
//                load(File("key.properties").reader())
//            }
//            storePassword = properties.getProperty("storePassword")
//            keyPassword = properties.getProperty("keyPassword")
//            keyAlias = properties.getProperty("keyAlias")
//            storeFile = File(properties.getProperty("storeFile"))
//        }
//    }

    buildTypes {
        release {
            isMinifyEnabled = true
            //TODO: Enable if want the release build to be signed
//            signingConfig = signingConfigs.getByName("release")
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //manually added libs
    implementation(libs.datastore)
    implementation(libs.dagger.hilt)
    debugImplementation(libs.androidx.ui.tooling)
    kapt(libs.dagger.hilt.compiler)
    implementation(libs.serialization)
    implementation(libs.androidx.lifecycle.runtime.ktx)

    //compose lib
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.ui.tooling)
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.hilt.nav)
}