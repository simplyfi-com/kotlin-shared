include(":sdk")
include(":sample:shared")
include(":sample:androidApp")
include(":sample:webApp")

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}

dependencyResolutionManagement {
    repositories {
        mavenCentral()
        google()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven {
            url = uri(providers.environmentVariable("GITLAB_MAVEN_URI").get())
            name = "gitlab"
            credentials(HttpHeaderCredentials::class) {
                name = providers.environmentVariable("GITLAB_MAVEN_HEADER").get()
                value = providers.environmentVariable("GITLAB_MAVEN_TOKEN").get()
            }
            authentication {
                create("header", HttpHeaderAuthentication::class)
            }
        }
    }
}

rootProject.name = "kotlin-shared"