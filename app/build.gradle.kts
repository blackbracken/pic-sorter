plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
}

val versionKotlin: String by project

android {
    compileSdkVersion(28)
    defaultConfig {
        applicationId = "black.bracken.picsorter"
        minSdkVersion(26)
        targetSdkVersion(28)
        versionCode = 2
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
    implementation("androidx.appcompat:appcompat:1.1.0")
    implementation("androidx.constraintlayout:constraintlayout:2.0.0-beta2")
    testImplementation("junit:junit:4.12")
    androidTestImplementation("androidx.test:runner:1.2.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.2.0")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.0")

    compile("android.arch.persistence.room:runtime:1.1.1")
    annotationProcessor("android.arch.persistence.room:compiler:1.1.1")
    kapt("android.arch.persistence.room:compiler:1.1.1")

    implementation("net.rdrei.android.dirchooser:library:3.2@aar")
    implementation("com.github.guardian:Option:-SNAPSHOT")

    implementation("androidx.recyclerview:recyclerview:1.1.0-beta04")

    implementation("com.xwray:groupie:2.5.1")
    implementation("com.xwray:groupie-kotlin-android-extensions:2.5.1")
    implementation("com.xwray:groupie-databinding:2.5.1")

    implementation("org.koin:koin-core:2.0.1")
    implementation("org.koin:koin-core-ext:2.0.1")
    implementation("org.koin:koin-android:2.0.1")
    implementation("org.koin:koin-androidx-scope:2.0.1")
    implementation("org.koin:koin-androidx-viewmodel:2.0.1")

    implementation("io.coil-kt:coil:0.7.0")

    // TODO: replaced by Coil
    implementation("com.github.bumptech.glide:glide:4.9.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.9.0")
}
