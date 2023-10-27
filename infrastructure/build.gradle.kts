plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    kotlin("plugin.serialization")
}

android {
    namespace = "com.bitio.infrastructure"
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
}

dependencies {
    //project
    api(project(":authComponent"))
    api(project(":orderComponent"))
    api(project(":productsComponent"))
    api(project(":notificationsComponent"))
    api(project(":userComponent"))

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")

    //retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")
    implementation("com.squareup.okhttp3:okhttp:4.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")


    //room
    val roomVersion = "2.5.2"
    api("androidx.room:room-runtime:$roomVersion")
    ksp("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-ktx:$roomVersion")


    val koinVersion ="3.4.3"
    api("io.insert-koin:koin-core:$koinVersion")
    api("io.insert-koin:koin-android:$koinVersion")
    val koinKspVersion ="1.2.2"
    api("io.insert-koin:koin-annotations:$koinKspVersion")
    ksp ("io.insert-koin:koin-ksp-compiler:$koinKspVersion")
    // api("io.insert-koin:koin-androidx-workmanager:3.4.3")



    //work manager
    implementation("androidx.work:work-runtime-ktx:2.8.1")
    //data store
    implementation("androidx.datastore:datastore-preferences:1.0.0")

}