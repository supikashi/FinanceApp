includeBuild("build_logic")

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
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "FinanceApp"
include(":app")

listOf(
    "model",
    "domain",
    "data",
    "ui",
    "di",
    "graph"
).forEach {
    include(":core:$it")
}

listOf(
    "transactions",
    "account",
    "category",
    "settings"
).forEach {
    include(":features:$it")
}
