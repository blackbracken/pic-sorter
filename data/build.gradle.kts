import dependencies.Dep

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

apply(from = rootProject.file("gradle/android.gradle"))

dependencies {
    implementation(Dep.Kotlin.stdlib)
    implementation(Dep.Kotlin.coroutines)

    testImplementation(Dep.JUnit.core)
    testImplementation(Dep.Truth.core)
    testImplementation(Dep.Robolectric.core)
    testImplementation(Dep.Hamcrest.core)
    testImplementation(Dep.AndroidX.testCore)
    testImplementation(Dep.AndroidX.testRunner)
    testImplementation(Dep.AndroidX.testRules)
    testImplementation(Dep.AndroidSupport.supportAnnotations)
    testImplementation(Dep.AndroidSupport.testRunner)

    androidTestImplementation(Dep.JUnit.core)
    androidTestImplementation(Dep.Truth.core)
    androidTestImplementation(Dep.AndroidX.testCore)
    androidTestImplementation(Dep.AndroidX.testRunner)
    androidTestImplementation(Dep.AndroidX.testRules)
    androidTestImplementation(Dep.AndroidX.testEspresso)
    androidTestImplementation(Dep.AndroidX.testUiAutomator)

    kapt(Dep.AndroidX.roomCompiler)
    implementation(Dep.AndroidX.roomKtx)
    implementation(Dep.AndroidX.roomRuntime)

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