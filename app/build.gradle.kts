//plugins {
//    alias(libs.plugins.android.application)
//}
//
//android {
//    namespace = "org.me.gcu.medconnect"
//    compileSdk = 34
//
//    defaultConfig {
//        applicationId = "org.me.gcu.medconnect"
//        minSdk = 26
//        targetSdk = 34
//        versionCode = 1
//        versionName = "1.0"
//
//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//    }
//
//    buildTypes {
//        release {
//            isMinifyEnabled = false
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
//        }
//    }
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_1_8
//        targetCompatibility = JavaVersion.VERSION_1_8
//    }
//
//    packagingOptions {
//        exclude("META-INF/DEPENDENCIES")
//        exclude("META-INF/LICENSE")
//        exclude("META-INF/LICENSE.txt")
//        exclude("META-INF/NOTICE")
//        exclude("META-INF/NOTICE.txt")
//    }
//}
//
//dependencies {
//    val navVersion = "2.7.7"
//
//    implementation("androidx.navigation:navigation-fragment:$navVersion")
//    implementation("androidx.navigation:navigation-ui:$navVersion")
//
//    implementation("com.google.android.material:material:1.4.0")
//    implementation("androidx.appcompat:appcompat:1.3.1")
//    implementation("com.squareup.retrofit2:retrofit:2.9.0")
//    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
//
//    // AWS Android SDK dependencies
//    implementation("com.amazonaws:aws-android-sdk-core:2.75.2")
//    implementation("com.amazonaws:aws-android-sdk-s3:2.75.2")
//    implementation("com.amazonaws:aws-android-sdk-ddb:2.75.2")
//    implementation("com.amazonaws:aws-android-sdk-ddb-mapper:2.75.2")
//    implementation("com.amazonaws:aws-android-sdk-cognitoidentityprovider:2.75.2")
//
//    implementation("com.google.android.gms:play-services-vision:20.1.3")
//    implementation("org.apache.pdfbox:pdfbox:3.0.2")
//    implementation("org.apache.commons:commons-io:1.3.2")
//
//    implementation(libs.appcompat)
//    implementation(libs.activity)
//    implementation(libs.constraintlayout)
//    testImplementation(libs.junit)
//    androidTestImplementation(libs.ext.junit)
//    androidTestImplementation(libs.espresso.core)
//}

//plugins {
//    alias(libs.plugins.android.application)
//}
//
//android {
//    namespace = "org.me.gcu.medconnect"
//    compileSdk = 34
//
//    defaultConfig {
//        applicationId = "org.me.gcu.medconnect"
//        minSdk = 26
//        targetSdk = 34
//        versionCode = 1
//        versionName = "1.0"
//
//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
//    }
//
//    buildTypes {
//        release {
//            isMinifyEnabled = false
//            proguardFiles(
//                getDefaultProguardFile("proguard-android-optimize.txt"),
//                "proguard-rules.pro"
//            )
//        }
//    }
//    compileOptions {
//        sourceCompatibility = JavaVersion.VERSION_1_8
//        targetCompatibility = JavaVersion.VERSION_1_8
//    }
//
//    packagingOptions {
//        exclude("META-INF/DEPENDENCIES")
//        exclude("META-INF/LICENSE")
//        exclude("META-INF/LICENSE.txt")
//        exclude("META-INF/NOTICE")
//        exclude("META-INF/NOTICE.txt")
//    }
//}
//
//dependencies {
//    val navVersion = "2.7.7"
//
//    implementation("androidx.navigation:navigation-fragment:$navVersion")
//    implementation("androidx.navigation:navigation-ui:$navVersion")
//
//    implementation("com.google.android.material:material:1.4.0")
//    implementation("androidx.appcompat:appcompat:1.3.1")
//    implementation("com.squareup.retrofit2:retrofit:2.9.0")
//    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
//
//    // AWS Android SDK dependencies
//    implementation("com.amazonaws:aws-android-sdk-core:2.75.2")
//    implementation("com.amazonaws:aws-android-sdk-s3:2.75.2")
//    implementation("com.amazonaws:aws-android-sdk-ddb:2.75.2")
//    implementation("com.amazonaws:aws-android-sdk-ddb-mapper:2.75.2")
//    implementation("com.amazonaws:aws-android-sdk-cognitoidentityprovider:2.75.2")
//
//    implementation("com.google.android.gms:play-services-vision:20.1.3")
//    implementation("org.apache.pdfbox:pdfbox:3.0.2")
//    implementation("org.apache.commons:commons-io:1.3.2")
//
//    implementation(libs.appcompat)
//    implementation(libs.activity)
//    implementation(libs.constraintlayout)
//    testImplementation(libs.junit)
//    androidTestImplementation(libs.ext.junit)
//    androidTestImplementation(libs.espresso.core)
//}

plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "org.me.gcu.medconnect"
    compileSdk = 34

    defaultConfig {
        applicationId = "org.me.gcu.medconnect"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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

    packagingOptions {
        exclude("META-INF/DEPENDENCIES")
        exclude("META-INF/LICENSE")
        exclude("META-INF/LICENSE.txt")
        exclude("META-INF/NOTICE")
        exclude("META-INF/NOTICE.txt")
    }
}

dependencies {
    val navVersion = "2.7.7"

    implementation("androidx.navigation:navigation-fragment:$navVersion")
    implementation("androidx.navigation:navigation-ui:$navVersion")

    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // AWS Android SDK dependencies
    implementation("com.amazonaws:aws-android-sdk-core:2.75.2")
    implementation("com.amazonaws:aws-android-sdk-s3:2.75.2")
    implementation("com.amazonaws:aws-android-sdk-ddb:2.75.2")
    implementation("com.amazonaws:aws-android-sdk-ddb-mapper:2.75.2")
    implementation("com.amazonaws:aws-android-sdk-cognitoidentityprovider:2.75.2")

    implementation("com.google.android.gms:play-services-vision:20.1.3")
    implementation("org.apache.pdfbox:pdfbox:3.0.2")
    implementation("org.apache.commons:commons-io:1.3.2")

    implementation("androidx.cardview:cardview:1.0.0")

    implementation(libs.appcompat)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)

    // Exclude old Kotlin versions
    configurations.all {
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-stdlib-jdk7")
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-stdlib-jdk8")
    }
}

