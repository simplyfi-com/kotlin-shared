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
        testRuns.named("test") {
            executionTask.configure {
                useJUnitPlatform()
            }
        }
    }

    js {
        nodejs {
            testTask(Action {
                useMocha {
                    timeout = 180000.toString()
                }
            })
        }
    }

    androidTarget {
        compilations.all {
            kotlinOptions {
                (findProperty("android.jvmTarget") as String?)?.let { jvmTarget = it }
            }
        }
    }

    ios()
    iosSimulatorArm64()

    cocoapods {
        homepage = "https://gitlab.tenderhub.net/tenderhub/kotlin-shared"
        summary = "SimplyFi SDK"
        version = "1.0"
        ios.deploymentTarget = "17.0"
        framework {
            baseName = "sdk"
            isStatic = true
        }
    }

    sourceSets {
        val androidMain by getting {
            dependencies {
                api(libs.activity.compose)
                api(libs.appcompat)
                api(libs.core.ktx)
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(libs.compose.webview)
                implementation(libs.ktor.client.okhttp)
            }
        }
        val androidUnitTest by getting
        val commonMain by getting {
            dependencies {
                implementation(libs.kotlinx.datetime)
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.serialization.kotlinx.json)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.kotlinx.serialization.json)
            }
        }
        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
                implementation(libs.kotlinx.coroutines.test)
            }
        }
        val jvmMain by getting {
            dependencies {
                implementation(libs.ktor.client.okhttp)
            }
        }
        val jvmTest by getting
        val jsMain by getting {
            dependencies {
                implementation(libs.ktor.client.js)
            }
        }
        val jsTest by getting
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosMain by getting {
            dependsOn(commonMain)
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(libs.compose.webview)
                implementation(libs.ktor.client.darwin)
            }
        }
        val iosSimulatorArm64Main by getting {
            dependsOn(iosMain)
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