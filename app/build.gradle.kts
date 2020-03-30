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
    defaultConfig {
        applicationId = "black.bracken.picsorter"
        minSdkVersion(26)
        targetSdkVersion(28)
        versionCode = 3
        versionName = "1.1"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    dataBinding {
        isEnabled = true
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))

    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:$versionKotlin")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.0")
    implementation("androidx.appcompat:appcompat:1.2.0-alpha03")
    implementation("androidx.constraintlayout:constraintlayout:2.0.0-beta4")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.2.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-savedstate:2.2.0")
    kapt("androidx.lifecycle:lifecycle-compiler:2.2.0")

    testImplementation("junit:junit:4.12")
    androidTestImplementation("androidx.test:runner:1.2.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")
    implementation("androidx.recyclerview:recyclerview:1.2.0-alpha01")

    implementation("net.rdrei.android.dirchooser:library:3.2@aar")
    implementation("com.github.guardian:Option:-SNAPSHOT")

    implementation("org.koin:koin-core:2.1.5")
    implementation("org.koin:koin-core-ext:2.1.5")
    testImplementation("org.koin:koin-test:2.1.5")
    implementation("org.koin:koin-android:2.1.5")
    implementation("org.koin:koin-android-scope:2.1.5")
    implementation("org.koin:koin-android-viewmodel:2.1.5")
    implementation("org.koin:koin-android-ext:2.1.5")
    implementation("org.koin:koin-androidx-scope:2.1.5")
    implementation("org.koin:koin-androidx-viewmodel:2.1.5")
    implementation("org.koin:koin-androidx-fragment:2.1.5")
    implementation("org.koin:koin-androidx-ext:2.1.5")

    implementation("com.github.bumptech.glide:glide:4.9.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.9.0")

    implementation("com.xwray:groupie:2.7.0")
    implementation("com.xwray:groupie-kotlin-android-extensions:2.7.0")
    implementation("com.xwray:groupie-databinding:2.7.0")
}
