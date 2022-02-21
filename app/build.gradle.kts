plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("dagger.hilt.android.plugin")
    //TODO: Above migrate to aliases
    alias(libs.plugins.kotlin.serialization)

//    id 'com.google.devtools.ksp' version '1.6.10-1.0.2'
}

android {
    compileSdk = 31
    buildToolsVersion = "30.0.3"

    defaultConfig {
        applicationId = "de.leonfuessner.tada"
        minSdk = 21
        targetSdk = 31
        versionCode = 2
        versionName = "1.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//        vectorDrawables {
//            useSupportLibrary true
//        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"))
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.compose.get()
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        compose = true
    }

//    kotlin {
//        sourceSets {
//            debug {
//                kotlin.srcDir("build/generated/ksp/debug/kotlin")
//            }
//            release {
//                kotlin.srcDir("build/generated/ksp/release/kotlin")
//            }
//        }
//    }

    packagingOptions {
        // for JNA and JNA-platform (coroutines 1.5.2)
        resources.excludes.add("META-INF/AL2.0")
        resources.excludes.add("META-INF/LGPL2.1")
    }
}

dependencies {

    implementation(platform(libs.kotlin.bom))

    implementation(libs.compose.ui.core)
    implementation(libs.compose.ui.tooling)
    implementation(libs.compose.material.core)
    implementation(libs.compose.material.icons)
    implementation(libs.compose.animation)

    implementation(libs.androidx.activity.compose)

    implementation(libs.androidx.navigation.compose)

    implementation(libs.timber)


    implementation(platform(libs.okhttp.bom))
    implementation(libs.okhttp.client)
    implementation(libs.okhttp.logger)

    implementation(libs.retrofit)
    implementation(libs.retrofit.converter)

    //TODO: move into version catalogue
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")

    implementation(libs.hilt.android.runtime)
    kapt(libs.hilt.compiler)
    kapt(libs.androidx.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    kapt(libs.room.compiler)


    //TODO: move into version catalogue
    implementation("androidx.datastore:datastore-preferences:1.0.0")

    implementation(libs.coil.compose)

    implementation(libs.chuckerteam.chucker) //TODO: in release do noop

    val room_version = "2.4.0"

    implementation ("androidx.room:room-runtime:$room_version")
    annotationProcessor ("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
    kapt ("androidx.room:room-compiler:$room_version")

    val accompanist = "0.24.2-alpha"

    implementation("com.google.accompanist:accompanist-navigation-material:$accompanist")
    implementation("com.google.accompanist:accompanist-insets:$accompanist")
    // If using insets-ui
    implementation ("com.google.accompanist:accompanist-insets-ui:$accompanist")

    implementation ("com.google.accompanist:accompanist-pager:$accompanist")

}