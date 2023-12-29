plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.jetbrains.compose)
}

kotlin {
    js(IR) {
        binaries.executable()
        browser()
    }
    sourceSets {
        val jsMain by getting {
            dependencies {
                implementation(project(":sample:shared"))
                implementation(compose.runtime)
                implementation(compose.html.core)
            }
        }
    }
}
