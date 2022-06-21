plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
}

android {
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        applicationId = AppConfig.applicationId
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = AppConfig.versionCode
        versionName = AppConfig.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Compose.composeVersion
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(project(Modules.core))
    implementation(project(Modules.noteData))
    implementation(project(Modules.noteDomain))
    implementation(project(Modules.noteUI))

    implementation(AndroidX.coreKtx)
    implementation(AndroidX.lifecycleRuntime)
    implementation(AndroidX.splashScreen)

    implementation(Compose.activity)
    implementation(Compose.ui)
    implementation(Compose.material3)
    implementation(Compose.uiToolingPreview)
    implementation(Compose.navigation)
    implementation(Compose.hiltNavigation)

    implementation(Hilt.android)
    kapt(Hilt.androidCompiler)
    implementation(Hilt.lifecycleVM)
    kapt(Hilt.compiler)
}

// Compiler flag to use experimental Compose APIs
tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions.freeCompilerArgs += "-opt-in=org.mylibrary.OptInAnnotation"
}