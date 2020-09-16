import dependencies.Deps

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

apply(from = rootProject.file("gradle/android.gradle"))

dependencies {
    implementation(Deps.Kotlin.stdlib)
    implementation(Deps.Kotlin.coroutines)

    testImplementation(Deps.JUnit.core)

    kapt(Deps.AndroidX.roomCompiler)
    implementation(Deps.AndroidX.roomKtx)
    implementation(Deps.AndroidX.roomRuntime)
}