
// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        google()
        mavenCentral()
            maven{ url =uri("https://maven.google.com") }

                    jcenter()
    }
    dependencies {
        classpath ("com.android.tools.build:gradle:8.0.2")
        classpath ("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.0")
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        maven{ url =uri("htts://maven.google.com") }
    }
}


