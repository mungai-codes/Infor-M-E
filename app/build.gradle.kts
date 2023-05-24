import org.jetbrains.kotlin.kapt3.base.Kapt.kapt

@Suppress("DSL_SCOPE_VIOLATION") // TODO: Remove once KTIJ-19369 is fixed
plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.hiltAndroid)
    alias(libs.plugins.kotlinKapt)
    alias(libs.plugins.devtoolsKsp)
}

android {
    namespace = "com.mungai.infor_m_e"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.mungai.infor_m_e"
        minSdk = 26
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.3"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(project(":core:common"))
    implementation(project(":core:data"))
    implementation(project(":core:domain"))
    implementation(project(":core:notifications"))
    implementation(project(":core:ui"))
    implementation(project(":feature:details"))
    implementation(project(":feature:home"))
    implementation(project(":feature:search"))
    implementation(project(":feature:settings"))

    // dagger hilt
    implementation(libs.hilt.android)
    implementation(libs.hilt.work)
    kapt(libs.hilt.android.compiler)
    kapt(libs.hilt.compiler)

    // navigation
    implementation(libs.hilt.navigation)
    implementation(libs.compose.navigation)
    implementation(libs.animated.navigation)

    // network
    implementation(libs.retrofit)
    implementation(libs.bundles.okhttp)
    implementation(libs.bundles.moshi)
    ksp(libs.moshi.codegen)

    // datastore preferences
    implementation(libs.datastore.preferences)

    // work manager
    implementation(libs.work.runtime.ktx)

    implementation(libs.core.ktx)
    implementation(libs.lifecycle.runtime.ktx)
    implementation(libs.activity.compose)
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)

    // compose - animation
    implementation(libs.compose.animation)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(platform(libs.compose.bom))
    androidTestImplementation(libs.ui.test.junit4)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.ui.test.manifest)
}