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
    testImplementation(Deps.Truth.core)
    testImplementation(Deps.Robolectric.core)
    testImplementation(Deps.Hamcrest.core)
    testImplementation(Deps.AndroidX.testCore)
    testImplementation(Deps.AndroidX.testRunner)
    testImplementation(Deps.AndroidX.testRules)
    testImplementation(Deps.AndroidSupport.supportAnnotations)
    testImplementation(Deps.AndroidSupport.testRunner)

    androidTestImplementation(Deps.JUnit.core)
    androidTestImplementation(Deps.Truth.core)
    androidTestImplementation(Deps.AndroidX.testCore)
    androidTestImplementation(Deps.AndroidX.testRunner)
    androidTestImplementation(Deps.AndroidX.testRules)
    androidTestImplementation(Deps.AndroidX.testEspresso)
    androidTestImplementation(Deps.AndroidX.testUiAutomator)

    kapt(Deps.AndroidX.roomCompiler)
    implementation(Deps.AndroidX.roomKtx)
    implementation(Deps.AndroidX.roomRuntime)

    implementation(Deps.Koin.core)
    implementation(Deps.Koin.coreExt)
    implementation(Deps.Koin.android)
    implementation(Deps.Koin.androidExt)
    implementation(Deps.Koin.androidScope)
    implementation(Deps.Koin.androidViewModel)
    implementation(Deps.Koin.androidXExt)
    implementation(Deps.Koin.androidXScope)
    implementation(Deps.Koin.androidXViewModel)
    implementation(Deps.Koin.androidXFragment)
    testImplementation(Deps.Koin.test)
}