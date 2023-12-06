plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.native.cocoapods)
    alias(libs.plugins.multiplatform.resources)
}

kotlin {
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
        summary = "Sample shared"
        homepage = "https://gitlab.tenderhub.net/tenderhub/kotlin-shared"
        version = "1.0"
        ios.deploymentTarget = "17.0"
        podfile = project.file("../iosApp/Podfile")
        framework {
            baseName = "shared"
            isStatic = true
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.ui)
                implementation(libs.kotlinx.datetime)
                api(project(":sdk"))
                api(libs.resources)
                api(libs.resources.compose)
            }
        }
        val androidMain by getting {
            dependsOn(commonMain)
            dependencies {
                implementation(libs.appcompat)
                implementation(libs.activity.compose)
                implementation(libs.core.ktx)
                implementation(libs.kotlinx.datetime.jvm)
            }
        }
        val iosMain by getting {
            dependsOn(commonMain)
        }
        val iosSimulatorArm64Main by getting {
            dependsOn(iosMain)
        }
    }
}

android {
    namespace = "com.simplyfi.sample"
    compileSdk = (findProperty("android.compileSdk") as String?)?.toInt()

    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

    defaultConfig {
        minSdk = (findProperty("android.minSdk") as String?)?.toInt()
        applicationId = "com.simplyfi.sample"
    }

    compileOptions {
        findProperty("android.sourceCompatibility")?.let { sourceCompatibility(it) }
        findProperty("android.targetCompatibility")?.let { sourceCompatibility(it) }
    }

    kotlin {
        (findProperty("android.jdkVersion") as String?)?.toInt()?.let { jvmToolchain(it) }
    }
}

multiplatformResources {
    multiplatformResourcesPackage = "com.simplyfi.sample"
    multiplatformResourcesSourceSet = "commonMain"
}