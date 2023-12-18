plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.plugins.serialization)
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.compose)
    `maven-publish`
    alias(libs.plugins.npm.publish)
    alias(libs.plugins.multiplatform.swiftpackage)
}

group = "com.simplyfi"
version = "0.0.1"

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
    organization.set((group as String).split(".")[1])
    version.set(project.version as String)

    packages {
        getByName("browser") {
            packageName.set("sdk-browser")
        }
    }

    registries {
        register("gitlab") {
            uri.set(providers.environmentVariable("GITLAB_NPM_URI"))
            authToken.set(providers.environmentVariable("GITLAB_NPM_TOKEN"))
        }
    }
}

publishing {
    repositories {
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

multiplatformSwiftPackage {
    packageName("sdk-ios")
    swiftToolsVersion("5.8")
    targetPlatforms {
        iOS { v("16") }
    }
    outputDirectory(File(projectDir, "build/swiftpackage"))
}