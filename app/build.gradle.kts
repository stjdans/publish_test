plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("maven-publish")
}

android {
    namespace = "com.example.publishlib"
    compileSdk = 34

    defaultConfig {
        minSdk = 24
        targetSdk = 33

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.10.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

publishing {
    repositories {
        maven {
            name = "publish-github"
            url = uri("https://maven.pkg.github.com/stjdans/publish_test")
            credentials {
                username = "stjdans"
                password = "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"
            }
        }

        maven {
            name = "publish-local"
            url = uri("$buildDir/repo")
        }
    }

    publications {
        register<MavenPublication>("github") {
            groupId = "${android.namespace}"
            artifactId = "lib"
            version = "0.0.1"
            version = "0.0.1-SNAPSHOT"

            artifact("$buildDir/outputs/aar/app-debug.aar")
        }
    }
}