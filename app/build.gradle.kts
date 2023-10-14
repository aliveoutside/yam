@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
}

android {
    namespace = "ru.toxyxd.yam"
    compileSdk = 34

    defaultConfig {
        applicationId = "ru.toxyxd.yam"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")
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
        kotlinCompilerExtensionVersion = "1.5.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(libs.bundles.androidKtx)
    implementation(libs.bundles.compose)
    implementation(libs.decompose)
    implementation(libs.decomposeExtensionCompose)

    implementation(libs.koin)
    implementation(libs.koinAndroid)
    implementation(libs.koinCompose)

    implementation(libs.coil)

    implementation(libs.multiplatformSettings)
    implementation(libs.multiplatformSettingsSerialization)

    implementation(libs.bundles.ktorClient)

    implementation(project(":common"))
    implementation(project(":core:player"))
    implementation(project(":core:yaapi"))
    implementation(project(":feature:home"))
    implementation(project(":feature:item"))
    implementation(project(":feature:signin"))
    implementation(project(":feature-ya:root"))
}