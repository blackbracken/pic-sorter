import dependencies.Dep

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
}

apply(from = rootProject.file("gradle/android.gradle"))

android {
    defaultConfig {
        applicationId = "black.bracken.picsorter"
        versionCode = 5
        versionName = "1.2.1"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    testOptions {
        testOptions.unitTests.isIncludeAndroidResources = true
    }
}

val versionKotlin: String by project

dependencies {
    implementation(Dep.Kotlin.stdlib)
    implementation(Dep.Kotlin.coroutines)

    implementation(project(":core"))
    implementation(project(":data:db"))
    implementation(project(":data:model"))
    implementation(project(":data:repository"))
    implementation(project(":feature:feature_common"))
    implementation(project(":feature:settings_top"))
    implementation(project(":feature:settings_observed_directory"))
    implementation(project(":feature:settings_simple_manipulating_top"))
    implementation(project(":feature:settings_simple_manipulating_registerer"))
    implementation(project(":feature:manipulating"))

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

    implementation(Dep.AndroidX.appCompat)

    implementation(Dep.AndroidX.legacySupport)

    implementation(Dep.AndroidX.lifecycleCommonJava8)
    implementation(Dep.AndroidX.lifecycleExtensions)
    implementation(Dep.AndroidX.lifecycleLiveDataKtx)
    implementation(Dep.AndroidX.lifecycleViewModelKtx)
    implementation(Dep.AndroidX.lifecycleViewModelSavedState)

    implementation(Dep.AndroidX.constraintLayout)
    implementation(Dep.AndroidX.recyclerView)

    implementation(Dep.AndroidX.navigationDynamicFeaturesFragment)
    implementation(Dep.AndroidX.navigationFragmentKtx)
    implementation(Dep.AndroidX.navigationUi)

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

    implementation(Dep.Coil.core)

    implementation(Dep.Groupie.core)
    implementation(Dep.Groupie.kotlinAndroidExtensions)

    implementation(Dep.MaterialDialogs.core)
    implementation(Dep.MaterialDialogs.files)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}