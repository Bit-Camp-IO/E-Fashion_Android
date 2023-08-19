plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("kotlin-parcelize")


}


android {
    namespace = "com.bitio.ui"
    compileSdk = 33

    defaultConfig {
        minSdk = 26

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

    }

    buildTypes {
        release {
            isMinifyEnabled = true
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

    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
}

dependencies {

    //project
   // api(project(":productsComponent"))
    api(project(":infrastructure"))

    api("androidx.core:core-ktx:1.10.1")
    api("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    api("androidx.activity:activity-compose:1.7.2")
    api(platform("androidx.compose:compose-bom:2023.06.01"))
    api("androidx.compose.ui:ui")
    api("androidx.compose.ui:ui-graphics")
    api("androidx.compose.ui:ui-tooling-preview")
    api("androidx.compose.material3:material3")


    implementation("com.google.android.gms:play-services-location:21.0.1")


    // Navigation
   // api("androidx.hilt:hilt-navigation-compose:1.0.0")
    api("androidx.navigation:navigation-compose:2.6.0")


    // Google maps
    api("com.google.maps.android:maps-compose:1.0.0")
    api("com.google.android.gms:play-services-maps:18.1.0")
    api("com.google.android.gms:play-services-location:21.0.1")

    // Accompanist
    api("com.google.accompanist:accompanist-systemuicontroller:0.28.0")

    api("androidx.constraintlayout:constraintlayout-compose:1.0.1")

    // coil
    implementation("io.coil-kt:coil-compose:2.4.0")


    //google fonts
    implementation("androidx.compose.ui:ui-text-google-fonts:1.4.3")

    // Koin
    api ("io.insert-koin:koin-androidx-compose:3.4.6")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.06.01"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

}