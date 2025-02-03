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
    }
}

rootProject.name = "Nasa"
include(":app")
include(":core:network")
include(":core:ui")
include("feature:astronomy_picture_of_day")
include(":data:astronomy_pictures")
include(":core:designsystem")
include(":core:database")
include(":feature:historic_astronomy_pictures_of_day")
include(":feature:favorite_picture_of_day")
