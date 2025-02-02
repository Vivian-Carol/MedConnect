//plugins {
//    id("com.android.application")
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
//    packaging {
//        resources {
//            excludes += "mockito-extensions/org.mockito.plugins.MockMaker"
//        }
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
//
//    implementation("androidx.core:core-ktx:1.7.0")
//    implementation("androidx.appcompat:appcompat:1.3.1")
//    implementation("com.google.android.material:material:1.4.0")
//    implementation("androidx.constraintlayout:constraintlayout:2.1.0")
//    testImplementation("junit:junit:4.13.2")
//    androidTestImplementation("androidx.test.ext:junit:1.1.3")
//    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")
//
//    // Navigation
//    val navVersion = "2.7.7"
//    implementation("androidx.navigation:navigation-fragment:$navVersion")
//    implementation("androidx.navigation:navigation-ui:$navVersion")
//
//    implementation("androidx.coordinatorlayout:coordinatorlayout:1.1.0")
//
//    // Retrofit
//    implementation("com.squareup.retrofit2:retrofit:2.9.0")
//    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
//
//    // AWS Android SDK
//    implementation("com.amazonaws:aws-android-sdk-core:2.75.2")
//    implementation("com.amazonaws:aws-android-sdk-s3:2.75.2")
//    implementation("com.amazonaws:aws-android-sdk-ddb:2.75.2")
//    implementation("com.amazonaws:aws-android-sdk-ddb-mapper:2.75.2")
//    implementation("com.amazonaws:aws-android-sdk-cognitoidentityprovider:2.75.2")
//    implementation("com.amazonaws:aws-android-sdk-textract:2.75.2")
//    implementation("com.amazonaws:aws-android-sdk-s3:2.75.2")
//
//    // Google Play Services
//    implementation("com.google.android.gms:play-services-vision:20.1.3")
//
//    // Apache PDFBox and Commons IO
//    implementation("org.apache.pdfbox:pdfbox:3.0.2")
//    implementation("org.apache.commons:commons-io:1.3.2")
//
//    // AndroidX CardView
//    implementation("androidx.cardview:cardview:1.0.0")
//
//    // Unit testing dependencies
//    testImplementation("org.mockito:mockito-core:4.6.1")
//    testImplementation("androidx.test:core:1.4.0")
//    testImplementation("org.robolectric:robolectric:4.7.3")
//    testImplementation("androidx.test.ext:junit:1.1.3")
//    testImplementation("androidx.test:runner:1.4.0")
//    testImplementation("androidx.test:rules:1.4.0")
//
//    // Espresso integration testing dependencies
//    androidTestImplementation("org.mockito:mockito-android:4.6.1")
//    androidTestImplementation("com.linkedin.dexmaker:dexmaker:2.28.1")
//    androidTestImplementation("com.linkedin.dexmaker:dexmaker-mockito:2.28.1")
//
//    // Exclude old Kotlin versions
//    configurations.all {
//        exclude(group = "org.jetbrains.kotlin", module = "kotlin-stdlib-jdk7")
//        exclude(group = "org.jetbrains.kotlin", module = "kotlin-stdlib-jdk8")
//    }
//}

plugins {
    id("com.android.application")
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

    packaging {
        resources {
            excludes += "mockito-extensions/org.mockito.plugins.MockMaker"
        }
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
    implementation("androidx.core:core-ktx:1.7.0")
    implementation("androidx.appcompat:appcompat:1.3.1")
    implementation("com.google.android.material:material:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.3")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.4.0")

    implementation("com.google.android.gms:play-services-vision:20.1.3")
    implementation("com.google.mlkit:text-recognition:16.0.0")

    // Navigation
    val navVersion = "2.7.7"
    implementation("androidx.navigation:navigation-fragment:$navVersion")
    implementation("androidx.navigation:navigation-ui:$navVersion")

    implementation("androidx.coordinatorlayout:coordinatorlayout:1.1.0")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // AWS Android SDK
    implementation("com.amazonaws:aws-android-sdk-core:2.75.2")
    implementation("com.amazonaws:aws-android-sdk-s3:2.75.2")
    implementation("com.amazonaws:aws-android-sdk-ddb:2.75.2")
    implementation("com.amazonaws:aws-android-sdk-ddb-mapper:2.75.2")
    implementation("com.amazonaws:aws-android-sdk-cognitoidentityprovider:2.75.2")
    implementation("com.amazonaws:aws-android-sdk-textract:2.75.2")

    // Google Play Services
    implementation("com.google.android.gms:play-services-vision:20.1.3")

    // Apache PDFBox and Commons IO
    implementation("org.apache.pdfbox:pdfbox:3.0.2")
    implementation("org.apache.commons:commons-io:1.3.2")

    // AndroidX CardView
    implementation("androidx.cardview:cardview:1.0.0")

    // Unit testing dependencies
    testImplementation("org.mockito:mockito-core:4.6.1")
    testImplementation("androidx.test:core:1.4.0")
    testImplementation("org.robolectric:robolectric:4.7.3")
    testImplementation("androidx.test.ext:junit:1.1.3")
    testImplementation("androidx.test:runner:1.4.0")
    testImplementation("androidx.test:rules:1.4.0")

    // Espresso integration testing dependencies
    androidTestImplementation("org.mockito:mockito-android:4.6.1")
    androidTestImplementation("com.linkedin.dexmaker:dexmaker:2.28.1")
    androidTestImplementation("com.linkedin.dexmaker:dexmaker-mockito:2.28.1")

    // Exclude old Kotlin versions
    configurations.all {
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-stdlib-jdk7")
        exclude(group = "org.jetbrains.kotlin", module = "kotlin-stdlib-jdk8")
    }
}
