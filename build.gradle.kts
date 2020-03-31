buildscript {
    val versionGradle: String by extra("3.4.1")
    val versionKotlin: String by extra("1.3.41")
    val versionKoin: String by extra("2.1.5")

    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:$versionGradle")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$versionKotlin")
        classpath("org.koin:koin-gradle-plugin:$versionKoin")
    }
}

allprojects {
    repositories {
        google()
        jcenter()
        mavenCentral()
        maven(url = "https://jitpack.io")
    }
}

task<Delete>("clean") {
    delete(rootProject.buildDir)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
    kotlinOptions {
        jvmTarget = "1.8"
    }
}