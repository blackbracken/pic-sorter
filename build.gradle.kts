buildscript {
    repositories {
        google()
        jcenter()
        mavenCentral()
    }

    dependencies {
        classpath("com.android.tools.build:gradle:4.1.0")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.0")
        classpath("org.koin:koin-gradle-plugin:2.1.6")
        classpath("com.google.protobuf:protobuf-gradle-plugin:0.8.13")
    }
}

apply(from = "gradle/dependencyGraph.gradle")

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