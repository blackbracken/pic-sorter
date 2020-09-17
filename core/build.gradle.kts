import dependencies.Dep

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

apply(from = rootProject.file("gradle/android.gradle"))

dependencies {
    implementation("androidx.navigation:navigation-fragment-ktx:2.3.0")
    implementation("androidx.navigation:navigation-ui-ktx:2.3.0")
    Dep.corePackages.forEach { implementation(it) }
}