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

    implementation(Dep.AndroidX.navigationFragmentKtx)
    implementation(Dep.AndroidX.navigationDynamicFeaturesFragment)
}