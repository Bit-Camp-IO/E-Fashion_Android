buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath ("com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:2.0.1")
        classpath("com.android.tools:r8:8.2.24")
        classpath("com.google.gms:google-services:4.4.0")
    }
}


// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.1.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("org.jetbrains.kotlin.jvm") version "1.8.10" apply false
    id("com.android.library") version "8.1.0" apply false
    kotlin("plugin.serialization") version "1.8.10" apply false
    id("com.google.devtools.ksp") version "1.8.10-1.0.9" apply false
}

