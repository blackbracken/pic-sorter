import com.google.protobuf.gradle.*
import dependencies.Dep

plugins {
    id("com.android.library")
    kotlin("android")
    kotlin("android.extensions")
    kotlin("kapt")
    id("com.google.protobuf")
}

apply(from = rootProject.file("gradle/android.gradle"))

dependencies {
    Dep.corePackages.forEach { implementation(it) }

    implementation(project(":model"))
    implementation(project(":data:db"))

    implementation(Dep.AndroidX.dataStoreCore)

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

    implementation("com.google.protobuf:protobuf-lite:3.0.1")
}

protobuf {
    protoc {
        artifact = "com.google.protobuf:protoc:3.10.0"
    }

    plugins {
        id("javalite") {
            artifact = "com.google.protobuf:protoc-gen-javalite:3.0.0"
        }
    }

    generateProtoTasks {
        all().forEach { task ->
            task.plugins {
                id("javalite")
            }
        }
    }
}