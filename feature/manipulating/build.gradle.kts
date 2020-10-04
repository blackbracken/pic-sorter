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
    Dep.koinPackages.forEach { implementation(it) }

    implementation(project(":data"))
    implementation(project(":feature:feature_common"))

    implementation(Dep.AndroidX.lifecycleCommonJava8)
    implementation(Dep.AndroidX.lifecycleExtensions)
    implementation(Dep.AndroidX.lifecycleLiveDataKtx)
    implementation(Dep.AndroidX.lifecycleViewModelKtx)
    implementation(Dep.AndroidX.lifecycleViewModelSavedState)

    implementation(Dep.AndroidX.constraintLayout)

    implementation(Dep.Coil.core)

    implementation(Dep.MaterialDialogs.core)
    implementation(Dep.MaterialDialogs.files)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}