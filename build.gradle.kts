buildscript {
    val versionGradle: String by extra("3.4.1")
    val versionKotlin: String by extra("1.3.31")

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
        maven(url = "https://jitpack.io")
    }
}

task<Delete>("clean") {
    delete(rootProject.buildDir)
}