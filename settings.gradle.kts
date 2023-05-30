pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Infor-M-E"
include(":app")
include(":core:common")
include(":core:domain")
include(":core:data")
include(":core:ui")
include(":feature:search")
include(":feature:home")
include(":feature:details")
include(":feature:settings")
