plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.plugins.serialization)
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.kotlin.native.cocoapods)
}

group = "com.simplyfi"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    google()
}

kotlin {
    jvm {
        jvmToolchain(8)
    }

    js("browser") {
        browser()
    }

    androidTarget {
        compilations.all {
            kotlinOptions {
                (findProperty("android.jvmTarget") as String?)?.let { jvmTarget = it }
            }
        }
    }

    iosX64()
    iosArm64()
    iosSimulatorArm64()

    cocoapods {
        homepage = "https://gitlab.tenderhub.net/tenderhub/kotlin-shared"
        summary = "SimplyFi SDK"
        version = "1.0"
        ios.deploymentTarget = "16.4"
        framework {
            baseName = "sdk"
            isStatic = true
        }
    }

    applyDefaultHierarchyTemplate()

    sourceSets {
        val uiMain by creating {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
            }
        }
        val mobileMain by creating {
            dependencies {
                implementation(compose.material3)
                implementation(libs.compose.webview)
            }
            dependsOn(uiMain)
        }
        androidMain {
            dependsOn(mobileMain)
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
                implementation(libs.compose.webview)
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
