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
    implementation(Dep.Kotlin.stdlib)
    implementation(Dep.Kotlin.coroutines)

    implementation(project(":data"))

    testImplementation(Dep.junit)
    testImplementation(Dep.truth)
    testImplementation(Dep.robolectric)
    testImplementation(Dep.hamcrest)
    testImplementation(Dep.AndroidX.testCore)
    testImplementation(Dep.AndroidX.testRunner)
    testImplementation(Dep.AndroidX.testRules)
    testImplementation(Dep.AndroidSupport.supportAnnotations)
    testImplementation(Dep.AndroidSupport.testRunner)

    androidTestImplementation(Dep.junit)
    androidTestImplementation(Dep.truth)
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

    implementation(Dep.Coil.core)

    implementation(Dep.Groupie.core)
    implementation(Dep.Groupie.kotlinAndroidExtensions)

    implementation(Dep.MaterialDialogs.core)
    implementation(Dep.MaterialDialogs.files)
}