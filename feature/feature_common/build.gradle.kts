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

    Dep.testPackages.forEach { testImplementation(it) }
    Dep.androidTestPackages.forEach { androidTestImplementation(it) }

    implementation(Dep.MaterialDialogs.core)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}