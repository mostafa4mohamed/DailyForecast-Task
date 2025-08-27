plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.devtools.ksp)
    alias(libs.plugins.hilt.android)
    id("org.jetbrains.kotlinx.kover") version "0.9.1"
}

android {
    namespace = "com.my_app.data"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    testImplementation(libs.mockk)

    implementation( project (":domain"))

    //dagger hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)

     //coroutines
     implementation (libs.kotlinx.coroutines.android)

     // Retrofit
     implementation (libs.retrofit)
     implementation (libs.logging.interceptor)
     implementation (libs.converter.gson)

     //room
     implementation(libs.room.runtime)
     implementation(libs.room.ktx)
     ksp(libs.room.compiler)

    //
    implementation (libs.androidx.datastore.preferences)

}