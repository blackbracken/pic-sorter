buildscript {
    val versionGradle: String by extra("3.4.1")
    val versionKotlin: String by extra("1.3.50")
    val versionGlide: String by extra("4.9.0")
    val versionArrow: String by extra("0.9.0")

    repositories {
        google()
        jcenter()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:$versionGradle")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:$versionKotlin")
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