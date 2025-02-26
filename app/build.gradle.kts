plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.serialization)
    kotlin("kapt")
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
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    //manually added libs
    implementation(libs.datastore)
    implementation(libs.dagger.hilt)
    kapt(libs.dagger.hilt.compiler)
    implementation(libs.serialization)
    implementation(libs.androidx.lifecycle.runtime.ktx)
}