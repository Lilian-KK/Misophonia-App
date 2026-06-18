import org.gradle.kotlin.dsl.invoke
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinSerialization) // **ADDED**
    alias(libs.plugins.ksp) // **ADDED** for Room
    alias(libs.plugins.androidx.room) // **ADDED** for Room
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.androidx.room.sqlite.wrapper) // **ADDED** for Room
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.11.0") //added for Kable
            //implementation(libs.kotlinx.coroutines.android)
            implementation(libs.ktor.client.okhttp)
        }
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)

            implementation(libs.kotlinx.serialization.json) // **ADDED** for Navigation
            implementation(libs.androidx.navigation.compose) // **ADDED** for Navigation
            // implementation(libs.androidx.navigationevent.compose) // **ADDED** for Navigation

            implementation(libs.androidx.room.runtime) // **ADDED** for Room
            implementation(libs.androidx.sqlite.bundled) // **ADDED** for Room

            implementation(libs.kotlinx.datetime) // **ADDED** for date/time handling

            api("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.11.0") //added for Kable
            implementation("com.juul.kable:kable-core:0.35.0") //added for Kable
//
//            implementation(libs.kotlin.serialization)
//            implementation(libs.kotlinx.coroutines.core)
//            implementation(libs.ktor.core)
//            implementation(libs.ktor.logging)
//            implementation(libs.ktor.client.negotiation)
//            implementation(libs.lifecycle.viewmodel.compose)
//            implementation(libs.coil.compose)
//            implementation(libs.coil.network.ktor)
//            implementation(libs.coil.compose.core)
//            implementation(libs.coil.mp)

            implementation(libs.ktor.client.core) // added for ktor
            implementation(libs.ktor.client.cio) // added for ktor
            implementation(libs.ktor.client.content.negotiation) // added for ktor
            implementation(libs.ktor.serialization.kotlinx.json) // added for ktor

            implementation("io.ktor:ktor-client-auth:3.5.0") // added for ktor auths

            implementation("ca.solo-studios:kt-fuzzy:0.1.0") // added for fuzzy kotlin string comparison
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}

//note: change namespace?
android {
    namespace = "edu.moravian.csci215.misophoniaapp"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "edu.moravian.csci215.misophoniaapp"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    add("kspAndroid", libs.androidx.room.compiler) // **ADDED** for Room
    add("kspIosSimulatorArm64", libs.androidx.room.compiler) // **ADDED** for Room
    // add("kspIosX64", libs.androidx.room.compiler) // **ADDED** for Room
    add("kspIosArm64", libs.androidx.room.compiler) // **ADDED** for Room
    debugImplementation(libs.compose.uiTooling)
}

room {
    schemaDirectory("$projectDir/schemas")
}