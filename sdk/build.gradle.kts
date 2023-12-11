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
        androidMain {
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
        commonMain {
            dependencies {
                implementation(libs.kotlinx.datetime)
                implementation(libs.ktor.client.core)
                implementation(libs.ktor.serialization.kotlinx.json)
                implementation(libs.ktor.client.content.negotiation)
                implementation(libs.kotlinx.coroutines.core)
                implementation(libs.kotlinx.serialization.json)
            }
        }
        commonTest {
            dependencies {
                implementation(kotlin("test"))
                implementation(libs.kotlinx.coroutines.test)
            }
        }
        jvmMain {
            dependencies {
                implementation(libs.ktor.client.okhttp)
            }
        }
        jvmTest {}
        jsMain {
            dependencies {
                implementation(libs.ktor.client.js)
            }
        }
        jsTest {}
        iosMain {
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