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
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "yam"
include(":app")
include(":core:yaapi")
include(":feature:signin")
include(":feature-ya:signin")

include(":feature:root")
include(":feature-ya:root")
include(":feature:home")
include(":feature:search")
include(":feature:library")
include(":common")
include(":feature-ya:home")
include(":feature:item")
include(":feature-ya:item")
