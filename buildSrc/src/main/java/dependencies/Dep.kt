package dependencies

object Dep {
    val corePackages = listOf(
        Kotlin.stdlib,
        Kotlin.coroutines
    )

    val androidCorePackages = listOf(
        AndroidX.appCompat,
        AndroidX.legacySupport,
        Koin.core,
        Koin.coreExt,
        Koin.android,
        Koin.androidExt,
        Koin.androidScope,
        Koin.androidViewModel,
        Koin.androidXExt,
        Koin.androidXScope,
        Koin.androidXViewModel,
        Koin.androidXFragment
    )

    val testPackages = listOf(
        JUnit.core,
        Truth.core,
        Robolectric.core,
        Hamcrest.core,
        AndroidX.testCore,
        AndroidX.testRunner,
        AndroidX.testRules,
        AndroidSupport.supportAnnotations,
        AndroidSupport.testRunner
    )

    val androidTestPackages = listOf(
        JUnit.core,
        Truth.core,
        AndroidX.testCore,
        AndroidX.testRunner,
        AndroidX.testRules,
        AndroidX.testEspresso,
        AndroidX.testUiAutomator
    )

    val koinPackages = listOf(
        Koin.core,
        Koin.coreExt,
        Koin.android,
        Koin.androidExt,
        Koin.androidScope,
        Koin.androidViewModel,
        Koin.androidXExt,
        Koin.androidXScope,
        Koin.androidXViewModel,
        Koin.androidXFragment
    )

    object Kotlin {
        const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.4.0"
        const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.7"
    }

    object AndroidX {
        const val testCore = "androidx.test:core:1.3.0"
        const val testRunner = "androidx.test:runner:1.3.0"
        const val testRules = "androidx.test:rules:1.3.0"
        const val testEspresso = "androidx.test.espresso:espresso-core:3.3.0"
        const val testUiAutomator = "androidx.test.uiautomator:uiautomator:2.2.0"

        const val appCompat = "androidx.appcompat:appcompat:1.3.0-alpha02"

        const val legacySupport = "androidx.legacy:legacy-support-v4:1.0.0"

        const val lifecycleCommonJava8 = "androidx.lifecycle:lifecycle-common-java8:2.2.0"
        const val lifecycleExtensions = "androidx.lifecycle:lifecycle-extensions:2.2.0"
        const val lifecycleViewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0"
        const val lifecycleLiveDataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:2.2.0"
        const val lifecycleViewModelSavedState =
            "androidx.lifecycle:lifecycle-viewmodel-savedstate:2.2.0"

        const val constraintLayout = "androidx.constraintlayout:constraintlayout:2.0.1"

        const val recyclerView = "androidx.recyclerview:recyclerview:1.2.0-alpha05"

        const val navigationFragmentKtx = "androidx.navigation:navigation-fragment-ktx:2.3.0"
        const val navigationUi = "androidx.navigation:navigation-ui:2.3.0"
        const val navigationDynamicFeaturesFragment =
            "androidx.navigation:navigation-dynamic-features-fragment:2.3.0"

        const val roomRuntime = "androidx.room:room-runtime:2.2.5"
        const val roomKtx = "androidx.room:room-ktx:2.2.5"
        const val roomCompiler = "androidx.room:room-compiler:2.2.5"

        const val dataStoreCore = "androidx.datastore:datastore-core:1.0.0-alpha01"
    }

    object AndroidSupport {
        const val supportAnnotations = "com.android.support:support-annotations:28.0.0"
        const val testRunner = "com.android.support.test:runner:1.0.2"
    }

    object JUnit {
        const val core = "junit:junit:4.12"
    }

    object Truth {
        const val core = "com.google.truth:truth:1.0.1"
    }

    object Robolectric {
        const val core = "org.robolectric:robolectric:4.3.1"
    }

    object Hamcrest {
        const val core = "org.hamcrest:hamcrest-library:1.3"
    }

    object Koin {
        const val core = "org.koin:koin-core:2.1.6"
        const val coreExt = "org.koin:koin-core-ext:2.1.5"
        const val test = "org.koin:koin-test:2.1.6"
        const val android = "org.koin:koin-android:2.1.5"
        const val androidScope = "org.koin:koin-android-scope:2.1.5"
        const val androidViewModel = "org.koin:koin-android-viewmodel:2.1.5"
        const val androidExt = "org.koin:koin-android-ext:2.1.5"
        const val androidXScope = "org.koin:koin-androidx-scope:2.1.5"
        const val androidXViewModel = "org.koin:koin-androidx-viewmodel:2.1.5"
        const val androidXFragment = "org.koin:koin-androidx-fragment:2.1.5"
        const val androidXExt = "org.koin:koin-androidx-ext:2.1.5"
    }

    object Coil {
        const val core = "io.coil-kt:coil:0.9.5"
    }

    object Groupie {
        const val core = "com.xwray:groupie:2.8.0"
        const val kotlinAndroidExtensions = "com.xwray:groupie-kotlin-android-extensions:2.8.0"
    }

    object MaterialDialogs {
        const val core = "com.afollestad.material-dialogs:core:3.2.1"
        const val files = "com.afollestad.material-dialogs:files:3.2.1"
    }
}