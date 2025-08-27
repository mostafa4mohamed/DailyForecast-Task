plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.hilt.android)
    id("org.jetbrains.kotlinx.kover") version "0.9.1"
}

android {
    namespace = "com.my_app.daily_forecast"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.my_app.daily_forecast"
        minSdk = 24
        targetSdk = 35
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

        applicationVariants.all { variant ->
            variant.outputs.all { output ->
                val appName = "Weather Now & Later App"
                val versionName = variant.versionName
                val versionCode = variant.versionCode
                val buildType = variant.buildType.name
                val fileName = "${appName}_v${versionName}_${versionCode}_${buildType}.apk"

                output.outputFile.renameTo(File(output.outputFile.parentFile, fileName))
                false
            }
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
        compose = true
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
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    testImplementation("io.kotest:kotest-runner-junit5:5.0.0") // Kotest for testing
    testImplementation("io.kotest:kotest-assertions-core:5.0.0") // Kotest assertions

    implementation(libs.weatherutils)

    //dagger hilt
    implementation(libs.hilt.android)
    implementation(libs.androidx.hilt.navigation.compose)
    ksp(libs.hilt.compiler)

    // Retrofit
    implementation(libs.retrofit)
    implementation(libs.logging.interceptor)
    implementation(libs.converter.gson)

    // For ViewModel
    implementation(libs.androidx.lifecycle.extensions)
    //    implementation(libs.androidx.lifecycle.viewmodel.ktx)

    //multi size ui
    implementation(libs.sdp.android)
    implementation(libs.ssp.android)

    implementation(libs.androidx.fragment.ktx)
    implementation(libs.androidx.activity.ktx)

    //room
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)

    //noinspection UseTomlInstead
    implementation("com.android.support:multidex:1.0.3")

    //
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.coil.compose)

    implementation(project(":domain"))
    implementation(project(":data"))
}