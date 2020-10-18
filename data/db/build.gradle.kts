import dependencies.Dep

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

apply(from = rootProject.file("gradle/android.gradle"))

dependencies {
    Dep.corePackages.forEach { implementation(it) }
    Dep.androidCorePackages.forEach { implementation(it) }

    kapt(Dep.AndroidX.roomCompiler)
    implementation(Dep.AndroidX.roomKtx)
    implementation(Dep.AndroidX.roomRuntime)

    implementation(Dep.Koin.core)
    implementation(Dep.Koin.coreExt)
    testImplementation(Dep.Koin.test)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}