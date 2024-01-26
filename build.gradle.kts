plugins {
    alias(libs.plugins.kotlin.multiplatform).apply(false)
    alias(libs.plugins.kotlin.plugins.serialization).apply(false)
    alias(libs.plugins.android.library).apply(false)
    alias(libs.plugins.android.application).apply(false)
    alias(libs.plugins.jetbrains.compose).apply(false)
    alias(libs.plugins.multiplatform.resources).apply(false)
    alias(libs.plugins.multiplatform.swiftpackage).apply(false)
    alias(libs.plugins.npm.publish).apply(false)
    alias(libs.plugins.suspend.transform).apply(false)
}

buildscript {
    dependencies {
        classpath(libs.resources.generator)
    }
}