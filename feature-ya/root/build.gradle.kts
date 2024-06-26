@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidLibrary)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlinSerialization)
}

android {
    namespace = "ru.toxyxd.root"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    api(project(":feature:root"))

    implementation(libs.koin)
    implementation(libs.bundles.androidKtx)
    implementation(libs.decompose)

    implementation(project(":common"))
    implementation(project(":core:yaapi"))
    implementation(project(":feature-ya:artist"))
    implementation(project(":feature-ya:item"))
    implementation(project(":feature-ya:player"))
    implementation(project(":feature-ya:signin"))
    implementation(project(":feature-ya:home"))
}