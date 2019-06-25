plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

val versionKotlin: String by project
val versionGlide: String by project
val versionArrow: String by project

android {
    compileSdkVersion(28)
    defaultConfig {
        applicationId = "black.bracken.picsorter"
        minSdkVersion(28)
        targetSdkVersion(28)
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:$versionKotlin")
    implementation("androidx.appcompat:appcompat:1.1.0-beta01")
    implementation("androidx.constraintlayout:constraintlayout:2.0.0-beta2")
    testImplementation("junit:junit:4.12")
    androidTestImplementation("androidx.test:runner:1.2.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")

    implementation("net.rdrei.android.dirchooser:library:3.2@aar")
    implementation("com.github.guardian:Option:-SNAPSHOT")

    implementation("com.github.bumptech.glide:glide:$versionGlide")
    annotationProcessor("com.github.bumptech.glide:compiler:$versionGlide")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.0-M1")
}
