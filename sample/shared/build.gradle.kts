plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.android.library)
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

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach {
        it.binaries.framework {
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
        val iosX64Main by getting
        val iosArm64Main by getting
        val iosSimulatorArm64Main by getting
        val iosMain by creating {
            dependsOn(commonMain)
            iosX64Main.dependsOn(this)
            iosArm64Main.dependsOn(this)
            iosSimulatorArm64Main.dependsOn(this)
        }
    }
}

android {
    namespace = "com.simplyfi.sample"
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

multiplatformResources {
    multiplatformResourcesPackage = "com.simplyfi.sample"
    multiplatformResourcesSourceSet = "commonMain"
}