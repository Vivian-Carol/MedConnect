//// Top-level build file where you can add configuration options common to all sub-projects/modules.
//plugins {
//    alias(libs.plugins.android.application) apply false
//}
//
//buildscript {
//    repositories {
//        google()
//        mavenCentral()
//    }
//    dependencies {
//        classpath ("com.android.tools.build:gradle:8.4.0")
//    }
//}
//
//plugins {
//        id("com.android.application") version "8.4.0" apply false
//        id("com.android.library") version "8.4.0" apply false
//}
//
//allprojects {
//    repositories {
//        google()
//        mavenCentral()
//    }
//}
////plugins {
////    id("com.android.application") version "8.4.0" apply false
////    id("com.android.library") version "8.4.0" apply false
////}
////
////allprojects {
////    repositories {
////        google()
////        mavenCentral()
////    }
////}

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version "8.4.0" apply false
    id("com.android.library") version "8.4.0" apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath("com.android.tools.build:gradle:8.4.0")
    }
}

allprojects {
}
