import dependencies.Deps

plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("kotlin-android")
    id("kotlin-android-extensions")
    id("kotlin-kapt")
}

val versionKotlin: String by project

android {
    compileSdkVersion(28)
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    defaultConfig {
        applicationId = "black.bracken.picsorter"
        minSdkVersion(28)
        targetSdkVersion(28)
        versionCode = 5
        versionName = "1.2.1"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
    buildFeatures {
        dataBinding = true
        viewBinding = true
    }
    androidExtensions {
        isExperimental = true
    }
    testOptions {
        testOptions.unitTests.isIncludeAndroidResources = true
    }
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(Deps.Kotlin.stdlib)
    implementation(Deps.Kotlin.coroutines)

    implementation(project(":data"))

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

    implementation(Deps.AndroidX.appCompat)

    implementation(Deps.AndroidX.legacySupport)

    implementation(Deps.AndroidX.lifecycleCommonJava8)
    implementation(Deps.AndroidX.lifecycleExtensions)
    implementation(Deps.AndroidX.lifecycleLiveDataKtx)
    implementation(Deps.AndroidX.lifecycleViewModelKtx)
    implementation(Deps.AndroidX.lifecycleViewModelSavedState)

    implementation(Deps.AndroidX.constraintLayout)
    implementation(Deps.AndroidX.recyclerView)

    implementation(Deps.AndroidX.navigationDynamicFeaturesFragment)
    implementation(Deps.AndroidX.navigationFragmentKtx)
    implementation(Deps.AndroidX.navigationUi)

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

    implementation(Deps.Coil.core)

    implementation(Deps.Groupie.core)
    implementation(Deps.Groupie.kotlinAndroidExtensions)

    implementation(Deps.MaterialDialogs.core)
    implementation(Deps.MaterialDialogs.files)
}