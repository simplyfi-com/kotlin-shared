import org.jetbrains.dokka.base.DokkaBase
import org.jetbrains.dokka.gradle.DokkaTask
import org.jetbrains.dokka.base.DokkaBaseConfiguration

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.plugins.serialization)
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.compose)
    `maven-publish`
    alias(libs.plugins.npm.publish)
    alias(libs.plugins.multiplatform.swiftpackage)
    alias(libs.plugins.dokka)
    alias(libs.plugins.git.publish)
}

group = "com.simplyfi"
version = rootProject.file("VERSION").readText()

repositories {
    mavenCentral()
    google()
}

kotlin {
    jvm {
        jvmToolchain(8)
    }

    js("browser") {
        moduleName = "browser"
        browser()
        binaries.library()
        generateTypeScriptDefinitions()
    }

    androidTarget {
        publishAllLibraryVariants()

        compilations.all {
            kotlinOptions {
                (findProperty("android.jvmTarget") as String?)?.let { jvmTarget = it }
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach {
        it.binaries.framework {
            baseName = "sdk"
            isStatic = true
        }
    }

    applyDefaultHierarchyTemplate()

    sourceSets {
        val uiMain by creating {
            dependencies {
                api(compose.runtime)
                api(compose.foundation)
            }
        }
        val mobileMain by creating {
            dependsOn(uiMain)
            dependencies {
                api(compose.material3)
            }
        }
        androidMain {
            dependsOn(mobileMain)
            dependencies {
                implementation(libs.ktor.client.okhttp)
            }
        }
        commonMain {
            dependencies {
                implementation(libs.kotlinx.datetime)
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.serialization.kotlinx.json)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.kotlinx.serialization.json)
            }
            uiMain.dependsOn(this)
        }
        jvmMain {
            dependencies {
                implementation(libs.ktor.client.okhttp)
                implementation(compose.runtime)
            }
        }
        val browserMain by getting {
            dependsOn(uiMain)
            dependencies {
                implementation(libs.ksoup)
            }
        }
        jsMain {
            dependsOn(commonMain.get())
            dependencies {
                implementation(libs.ktor.client.js)
                implementation(compose.html.core)
            }
            browserMain.dependsOn(this)
        }
        iosMain {
            dependsOn(mobileMain)
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(libs.ktor.client.darwin)
            }
        }
    }
}

android {
    namespace = "com.simplyfi.sdk"
    compileSdk = (findProperty("android.compileSdk") as String?)?.toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

    defaultConfig {
        minSdk = (findProperty("android.minSdk") as String?)?.toInt()
    }

    compileOptions {
        findProperty("android.sourceCompatibility")?.let { sourceCompatibility(it) }
        findProperty("android.targetCompatibility")?.let { sourceCompatibility(it) }
    }

    kotlin {
        (findProperty("android.jdkVersion") as String?)?.toInt()?.let { jvmToolchain(it) }
    }
}

npmPublish {
    organization.set((group as String).split(".").reversed().joinToString("-"))
    version.set(project.version as String)

    packages {
        getByName("browser") {
            packageName.set("sdk-browser")
        }
    }

    registries {
        github {
            authToken.set(providers.environmentVariable("GITHUB_TOKEN"))
        }
    }
}

publishing {
    repositories {
        maven {
            url = uri("https://maven.pkg.github.com/simplyfi-com/kotlin-shared")
            name = "github"
            credentials {
                username = providers.environmentVariable("GITHUB_ACTOR").get()
                password = providers.environmentVariable("GITHUB_TOKEN").get()
            }
        }
    }
}

multiplatformSwiftPackage {
    packageName("sdk-ios")
    swiftToolsVersion("5.8")
    targetPlatforms {
        iOS { v("16") }
    }
    outputDirectory(File(projectDir, "build/swiftpackage"))
}

buildscript {
    dependencies {
        classpath(libs.dokka.base)
    }
}

tasks.withType<DokkaTask>().configureEach {
    moduleName.set("SimplyFi SDK")

    dokkaSourceSets {
        configureEach {
            sourceLink {
                localDirectory.set(projectDir.resolve("src"))
                remoteUrl.set(uri("https://github.com/simplyfi-com/kotlin-shared/-/tree/master/sdk/src").toURL())
                remoteLineSuffix.set("#L")
            }
        }
    }

    pluginConfiguration<DokkaBase, DokkaBaseConfiguration> {
        footerMessage = "Copyright © 2023 SFI Technologies Limited"
        customStyleSheets = listOf(file("assets/logo-styles.css"))
    }
}

gitPublish {
    commitMessage.set("Update to $version")

    (publications) {
        "main" {
            repoUri.set("git@github.com:simplyfi-com/swift-shared.git")
            branch.set("main")

            contents {
                from(
                    rootProject.file("LICENSE"),
                    rootProject.file("VERSION"),
                    layout.projectDirectory.dir(".swift-sdk-base")
                )
                from(layout.buildDirectory.dir("swiftpackage")) {
                    exclude("sdk-ios-*.zip")
                }
            }
        }

        create("pages") {
            repoUri.set("git@github.com:simplyfi-com/kotlin-shared.git")
            branch.set("gh-pages")

            contents {
                from(layout.buildDirectory.dir("dokka/html"))
            }
        }
    }
}

tasks.getByName("gitPublishCopy").dependsOn("createSwiftPackage")
tasks.getByName("gitPublishPagesCopy").dependsOn("dokkaHtml")