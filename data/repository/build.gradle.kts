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

    implementation(project(":data:model"))
    implementation(project(":data:db"))

    implementation(Dep.Koin.core)
    implementation(Dep.Koin.coreExt)
    implementation(Dep.Koin.android)
    implementation(Dep.Koin.androidExt)
    implementation(Dep.Koin.androidScope)
    implementation(Dep.Koin.androidViewModel)
    implementation(Dep.Koin.androidXExt)
    implementation(Dep.Koin.androidXScope)
    implementation(Dep.Koin.androidXViewModel)
    implementation(Dep.Koin.androidXFragment)
    testImplementation(Dep.Koin.test)
}