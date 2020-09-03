plugins {
  id("com.squareup.sqldelight")
  id("com.android.library")
  kotlin("android")
  kotlin("kapt")
  id(KtLint.name)
}

android {
  compileSdkVersion(BuildConfig.compileSdk)

  defaultConfig {
    minSdkVersion(BuildConfig.minSdk)
    targetSdkVersion(BuildConfig.targetSdk)
    versionCode = BuildConfig.versionCode
    versionName = BuildConfig.versionName
    resConfigs("en", "mm")

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    consumerProguardFiles("consumer-rules.pro")
  }

  buildTypes {
    getByName("debug") {
      isMinifyEnabled = false
    }

    getByName("release") {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
    }
  }

  compileOptions {
    coreLibraryDesugaringEnabled = true
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
  }

  kotlinOptions {
    jvmTarget = "1.8"
  }
}

sqldelight {
  database("MVoterRemoteKeyDb") {
    packageName = "com.popstack.mvoter2015.data.android"
    dialect = "sqlite:3.24"
  }
}

dependencies {
  implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
  implementation(project(":domain"))
  api(project(":data:common"))
  api(project(":data:cache"))
  api(project(":data:network"))

  coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:1.0.10")

  implementation(CommonLibs.timber)
  implementation(AndroidXPaging.common)
  implementation(Kotlin.stdblib_jdk)
  implementation(AndroidXCore.core_ktx)

  implementation("com.google.android.gms:play-services-location:17.0.0")

  //Database
  implementation(AndroidXSqlite.sqlite_ktx)
  implementation(SqlDelight.android_driver)

  //Dagger
  daggerJvm()
  daggerAndroid()

  //Testing
  testImplementation(CommonLibs.junit)
  testImplementation("org.robolectric:shadows-playservices:4.3.1")
  testImplementation(project(":coroutinetestrule"))
  mockito()
  mockitoAndroid()
  androidXTest()
}

ktlint {
  android.set(true)
}