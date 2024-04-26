pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        // The following line of code was taken from youtube
        // Author: Tech Harvest BD  -  THBD
        // Link: https://www.youtube.com/watch?v=ZtiaNrHKkJo
        maven {

            url = uri("https://jitpack.io")
        }
    }
}

rootProject.name = "ChronoMetron"
include(":app")
 