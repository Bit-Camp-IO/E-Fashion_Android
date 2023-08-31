pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven {
            url = uri("https://storage.googleapis.com/r8-releases/raw")
        }
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()

    }
}

rootProject.name = "E Fashion"
include(":app")
include(":productsComponent")
include(":userComponent")
include(":supportComponent")
include(":sharedComponent")
include(":orderComponent")
include(":ui")
include(":authComponent")
include(":infrastructure")
