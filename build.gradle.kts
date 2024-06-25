// Top-level build.gradle

// Define Kotlin version as an external property
ext.kotlin_version = "1.9.0"  // You can update this to the latest stable version

repositories {
    google()  // Includes Google's Maven repository
    mavenCentral() // Includes Maven Central repository
    // Optionally add Jitpack for libraries not in Maven Central
    maven { url 'https://www.jitpack.io' }
}

// Configure Gradle plugins and dependencies
buildscript {
    dependencies {
        classpath 'com.android.tools.build:gradle:8.8.0' // Adjust version based on compatibility
        classpath "org.jetbrains.kotlin:kotlin-gradle-plugin:$ext.kotlin_version"
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
        // Optionally add Jitpack for libraries not in Maven Central
        maven { url 'https://www.jitpack.io' }
    }
}

// Clean task to remove build directory
task clean(type: Delete) {
    delete rootProject.buildDir
}
