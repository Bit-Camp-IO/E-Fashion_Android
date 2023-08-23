plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
   // id("org.jetbrains.kotlin.plugin.serialization").version("1.8.10")
    kotlin("plugin.serialization")
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}
dependencies{
    api(project(":sharedComponent"))
}