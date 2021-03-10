include(":app")
rootProject.name = "MySimpleCoinDeck"


pluginManagement {
    repositories {
        gradlePluginPortal()
        google()
        jcenter()
    }
}

plugins {
    id("com.gradle.enterprise") version "3.0"
}

gradleEnterprise {
    buildScan {
        termsOfServiceUrl = "https://gradle.com/terms-of-service"
        termsOfServiceAgree = "yes"
        publishAlways()
    }
}
