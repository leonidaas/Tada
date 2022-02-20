buildscript {

    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }

    dependencies {
        classpath(libs.android.tools.build.gradle)
        classpath(libs.kotlin.gradle)
        classpath(libs.hilt.gradle)
    }

}

subprojects {
    repositories {
        google()
        mavenCentral()
    }
}