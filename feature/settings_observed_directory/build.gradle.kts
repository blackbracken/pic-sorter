import dependencies.Dep

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

apply(from = rootProject.file("gradle/android.gradle"))

dependencies {
    implementation(project(":core"))
    implementation(project(":data:model"))
    implementation(project(":data:repository"))
    implementation(project(":feature:feature_common"))

    Dep.corePackages.forEach { implementation(it) }
    Dep.androidCorePackages.forEach { implementation(it) }
    Dep.testPackages.forEach { testImplementation(it) }
    Dep.androidTestPackages.forEach { androidTestImplementation(it) }

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